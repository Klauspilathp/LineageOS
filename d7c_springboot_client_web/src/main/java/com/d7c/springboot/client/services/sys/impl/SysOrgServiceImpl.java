package com.d7c.springboot.client.services.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.context.AbstractBaseService;
import com.d7c.plugins.core.context.IdService;
import com.d7c.plugins.core.exception.D7cRuntimeException;
import com.d7c.springboot.client.config.D7cConstant;
import com.d7c.springboot.client.daos.sys.ExtSysOrgDao;
import com.d7c.springboot.client.daos.sys.ExtSysUserDao;
import com.d7c.springboot.client.dtos.tree.SelectTree;
import com.d7c.springboot.client.dtos.tree.SelectTreeUtil;
import com.d7c.springboot.client.dtos.tree.ZTree;
import com.d7c.springboot.client.dtos.tree.ZTreeUtil;
import com.d7c.springboot.client.services.sys.SysOrgService;
import com.d7c.springboot.common.daos.sys.BaseSysOrgDao;
import com.d7c.springboot.common.dos.sys.SysOrg;
import com.d7c.springboot.common.enums.sys.LevelEnum;
import com.d7c.springboot.common.enums.sys.OrgTypeEnum;
import com.d7c.springboot.common.enums.sys.StatusEnum;
import com.d7c.springboot.common.enums.sys.YesNoEnum;

/**
 * @Title: SysOrgServiceImpl
 * @Package: com.d7c.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:14
 * @Description: d7c 系统组织机构 Service 实现
 */
@Service(value = "sysOrgServiceImpl")
public class SysOrgServiceImpl extends AbstractBaseService<BaseSysOrgDao, SysOrg, Long> implements SysOrgService {
    /**
     * d7c 系统组织机构扩展 Dao
     */
    @Resource(name = "extSysOrgDao")
    private ExtSysOrgDao sysOrgDao;
    /**
     * d7c系统_用户表扩展 Dao
     */
    @Resource(name = "extSysUserDao")
    private ExtSysUserDao sysUserDao;
    /**
     * ID 生成服务
     */
    @Resource(name = "dbIdServiceImpl")
    private IdService idService;

    // ZTree 同步实现方式
    @Override
    public List<ZTree> listZTreeFormTreeFrameByParentId_SYNC(PageData pd) {
        // 查询出所有组织机构信息列表
        List<ZTree> allOrg = sysOrgDao.listZTreeFormTreeFrameByParentId(null);
        if (allOrg != null && !allOrg.isEmpty()) {
            return new ZTreeUtil().dealZtree2_6(allOrg, this.maxOrgId(pd));
        }
        return new ArrayList<ZTree>();
    }

    @Override
    public List<PageData> listPDBy(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            throw new D7cRuntimeException("parameters cannot be null！");
        }
        // 当前登录用户编号
        Long session_userId = pd.getLong(D7cConstant.SESSION_USER_ID);
        if (session_userId == null) {
            throw new D7cRuntimeException("SESSION_USER_ID cannot be null！");
        }
        if (session_userId.equals(D7cConstant.SUPER_ADMIN_ID)) {
            pd.remove("orgId");
        } else { // 不是超级管理员
            PageData org = sysOrgDao.getOrgInfoByUserId(session_userId);
            if (org == null || org.isEmpty()) {
                throw new D7cRuntimeException("当前登录用户组织机构信息不存在！");
            }
            // 当前登录用户组织机构编号
            Long session_orgId = org.getLong("orgId");
            if (session_orgId == null) {
                throw new D7cRuntimeException("当前用户组织机构编号为空！");
            }
            LevelEnum levelEnum = LevelEnum.forKey(org.get("level"));
            if (levelEnum == null) {
                throw new D7cRuntimeException("当前用户组织机构级别为空！");
            }
            // 查询类型
            Long parentId = pd.getLong("parentId");
            if (parentId == null || parentId.compareTo(0L) != 1) { // 首次进入查询页
                pd.put("orgId", org.getLong("orgId"));
                pd.remove("parentId");
            } else { // 根据父级组织机构编号查询子级组织机构列表
                pd.remove("orgId");
                // 限定它的上级组织机构查询条件
                switch (levelEnum) {
                    case BLOC_HQ:
                        pd.put("blocHQId", session_orgId);
                        break;
                    case AREA_HQ:
                        pd.put("areaHQId", session_orgId);
                        break;
                    case COMPANY:
                        pd.put("companyId", session_orgId);
                        break;
                    case DEPARTMENT:
                        break;
                    default:
                        throw new D7cRuntimeException("当前用户组织机构级别有误！");
                }
            }
        }
        pd.put("status", StatusEnum.NORMAL.getKey());
        return sysOrgDao.listPDBy(pd);
    }

    @Override
    public PageResult insertOrg(PageData pd) {
        PageResult result = this.saveVerify(pd);
        if (!result.isOk()) {
            return result;
        }
        pd = (PageData) result.getData();
        String orgNameCN = pd.getString("orgNameCN");
        if (StringUtil.isBlank(orgNameCN)) {
            return PageResult.error("orgNameCN 不能为空！");
        }
        OrgTypeEnum orgType = OrgTypeEnum.forKey(pd.getInteger("orgType"));
        if (orgType == null) {
            return PageResult.error("orgType 不存在！");
        }
        String orgCode = pd.getString("orgCode");
        if (StringUtil.isBlank(orgCode)) {
            return PageResult.error("orgCode 不能为空！");
        }
        Integer sort = pd.getInteger("sort");
        if (sort == null) {
            return PageResult.error("sort 不是数字！");
        }
        Long addUser = pd.getLong("addUser");
        if (addUser == null) {
            return PageResult.error("保存用户不能为空！");
        }

        // 判断组织机构代码是否唯一
        int hasExist = sysOrgDao.hasExist(pd);
        if (hasExist > 0) {
            return PageResult.error("组织机构代码已存在！");
        }

        SysOrg sysOrg = null;
        Integer level = null; // 地域级别
        Long parentId = pd.getLong("parentId");
        if (parentId.compareTo(0L) != 0) {
            SysOrg parentOrg = dao.getByKey(parentId);
            if (parentOrg == null) {
                return PageResult.error("父级组织机构信息为空！");
            }
            sysOrg = new SysOrg();
            sysOrg.setOrgId(idService.getLong(SysOrg.M.TABLE_NAME));
            Integer parentLevel = parentOrg.getLevel();
            if (parentLevel.compareTo(LevelEnum.BLOC_HQ.getKey()) != -1) {
                sysOrg.setBlocHQId(parentOrg.getBlocHQId());
                sysOrg.setBlocHQNameCN(parentOrg.getBlocHQNameCN());
            }
            if (parentLevel.compareTo(LevelEnum.AREA_HQ.getKey()) != -1) {
                sysOrg.setAreaHQId(parentOrg.getAreaHQId());
                sysOrg.setAreaHQNameCN(parentOrg.getAreaHQNameCN());
            }
            if (parentLevel.compareTo(LevelEnum.COMPANY.getKey()) != -1) {
                sysOrg.setCompanyId(parentOrg.getCompanyId());
                sysOrg.setCompanyNameCN(parentOrg.getCompanyNameCN());
            }
            level = parentLevel + 10;
            switch (LevelEnum.forKey(level)) {
                case AREA_HQ:
                    sysOrg.setAreaHQId(sysOrg.getOrgId());
                    sysOrg.setAreaHQNameCN(orgNameCN);
                    break;
                case COMPANY:
                    sysOrg.setCompanyId(sysOrg.getOrgId());
                    sysOrg.setCompanyNameCN(orgNameCN);
                    break;
                default:
                    break;
            }
        } else {
            sysOrg = new SysOrg();
            sysOrg.setOrgId(idService.getLong(SysOrg.M.TABLE_NAME));
            level = LevelEnum.BLOC_HQ.getKey();
            sysOrg.setBlocHQId(sysOrg.getOrgId());
            sysOrg.setBlocHQNameCN(orgNameCN);
        }
        sysOrg.setOrgNameCN(orgNameCN);
        sysOrg.setOrgNameEN(pd.getString("orgNameEN"));
        sysOrg.setOrgAlias(pd.getString("orgAlias"));
        sysOrg.setOrgType_Enum(orgType);
        sysOrg.setOrgAddress(pd.getString("orgAddress"));
        sysOrg.setOrgCode(orgCode);
        sysOrg.setLevel(level);
        sysOrg.setSort(sort);
        sysOrg.setPrincipal(pd.getString("principal"));
        sysOrg.setContactAddress(pd.getString("contactAddress"));
        sysOrg.setPostalcode(pd.getString("postalcode"));
        sysOrg.setPhone(pd.getString("phone"));
        sysOrg.setQq(pd.getString("qq"));
        sysOrg.setWeixin(pd.getString("weixin"));
        sysOrg.setEmail(pd.getString("email"));
        sysOrg.setParentId(parentId);
        sysOrg.setRemark(pd.getString("remark"));
        sysOrg.setAddUser(addUser);
        sysOrg.setStatus_Enum(StatusEnum.NORMAL);
        int insert = dao.insert(sysOrg);
        if (insert == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateOrg(PageData pd) {
        PageResult result = this.saveVerify(pd);
        if (!result.isOk()) {
            return result;
        }
        pd = (PageData) result.getData();
        Long orgId = pd.getLong("orgId");
        if (orgId == null || orgId.compareTo(0L) != 1) {
            return PageResult.error("组织机构编号不能为空！");
        }
        String orgNameCN = pd.getString("orgNameCN");
        if (StringUtil.isBlank(orgNameCN)) {
            return PageResult.error("orgNameCN 不能为空！");
        }
        OrgTypeEnum orgType = OrgTypeEnum.forKey(pd.getInteger("orgType"));
        if (orgType == null) {
            return PageResult.error("orgType 不存在！");
        }
        Integer sort = pd.getInteger("sort");
        if (sort == null) {
            return PageResult.error("sort 不是数字！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        SysOrg sysOrg = null;
        Integer level = null; // 地域级别
        Long parentId = pd.getLong("parentId");
        if (parentId.compareTo(0L) != 0) { // 公司降级
            SysOrg parentOrg = dao.getByKey(parentId);
            if (parentOrg == null) {
                return PageResult.error("父级组织机构信息为空！");
            }
            sysOrg = new SysOrg();
            Integer parentLevel = parentOrg.getLevel();
            if (parentLevel.compareTo(LevelEnum.BLOC_HQ.getKey()) != -1) {
                sysOrg.setBlocHQId(parentOrg.getBlocHQId());
                sysOrg.setBlocHQNameCN(parentOrg.getBlocHQNameCN());
            }
            if (parentLevel.compareTo(LevelEnum.AREA_HQ.getKey()) != -1) {
                sysOrg.setAreaHQId(parentOrg.getAreaHQId());
                sysOrg.setAreaHQNameCN(parentOrg.getAreaHQNameCN());
            }
            if (parentLevel.compareTo(LevelEnum.COMPANY.getKey()) != -1) {
                sysOrg.setCompanyId(parentOrg.getCompanyId());
                sysOrg.setCompanyNameCN(parentOrg.getCompanyNameCN());
            }
            level = parentLevel + 10;
            switch (LevelEnum.forKey(level)) {
                case AREA_HQ:
                    sysOrg.setAreaHQId(orgId);
                    sysOrg.setAreaHQNameCN(orgNameCN);
                    break;
                case COMPANY:
                    sysOrg.setCompanyId(orgId);
                    sysOrg.setCompanyNameCN(orgNameCN);
                    break;
                default:
                    break;
            }
        } else { // 子公司独立
            sysOrg = new SysOrg();
            level = LevelEnum.BLOC_HQ.getKey();
            sysOrg.setBlocHQId(sysOrg.getOrgId());
            sysOrg.setBlocHQNameCN(orgNameCN);
        }
        sysOrg.setOrgId(orgId);
        sysOrg.setOrgNameCN(orgNameCN);
        sysOrg.setOrgNameEN(pd.getString("orgNameEN"));
        sysOrg.setOrgAlias(pd.getString("orgAlias"));
        sysOrg.setOrgType_Enum(orgType);
        sysOrg.setOrgAddress(pd.getString("orgAddress"));
        sysOrg.setLevel(level);
        sysOrg.setSort(sort);
        sysOrg.setPrincipal(pd.getString("principal"));
        sysOrg.setContactAddress(pd.getString("contactAddress"));
        sysOrg.setPostalcode(pd.getString("postalcode"));
        sysOrg.setPhone(pd.getString("phone"));
        sysOrg.setQq(pd.getString("qq"));
        sysOrg.setWeixin(pd.getString("weixin"));
        sysOrg.setEmail(pd.getString("email"));
        sysOrg.setParentId(parentId);
        sysOrg.setRemark(pd.getString("remark"));
        sysOrg.setModifyUser(modifyUser);
        sysOrg.setCheckValue(checkValue);
        int update = dao.update(sysOrg);
        if (update == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult saveVerify(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        // 要新增或修改的组织机构的父组织机构编号
        Long parentId = pd.getLong("parentId");
        if (parentId == null || parentId.compareTo(0L) == -1) {
            return PageResult.error("父级组织机构编号不能为空！");
        }
        // 当前登录用户编号
        Long session_userId = pd.getLong(D7cConstant.SESSION_USER_ID);
        if (session_userId == null) {
            return PageResult.error("SESSION_USER_ID cannot be null");
        }
        if (session_userId.equals(D7cConstant.SUPER_ADMIN_ID)) { // 超级管理员
            return PageResult.ok(pd);
        }
        // 普通用户添加组织机构
        PageData user = sysUserDao.getUserInfoByUserId(session_userId);
        if (user == null || user.isEmpty()) {
            return PageResult.error("当前登录用户不存在！");
        }
        Long session_orgId = user.getLong("orgId");
        if (session_orgId == null) {
            return PageResult.error("当前用户组织机构编号为空！");
        }
        // 要修改的组织机构编号
        Long orgId = pd.getLong("orgId");
        if (orgId == null) { // 新增
            if (!parentId.equals(session_orgId)) {
                return PageResult.error("您只能给添加您的子级组织机构！");
            }
        } else { // 修改
            // 是否是管理员
            boolean administrator = YesNoEnum.equalValue(YesNoEnum.YES, user.getInteger("administrator"));
            if (orgId.equals(session_orgId) && !administrator) {
                return PageResult.error("您不是管理员，不能修改组织机构信息！");
            }
            if (!parentId.equals(session_orgId)) {
                return PageResult.error("您只能修改您的子级组织机构信息！");
            }
        }
        return PageResult.ok(pd);
    }

    @Override
    public PageResult updateStatus(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long orgId = pd.getLong("orgId");
        if (orgId == null || orgId.compareTo(0L) != 1) {
            return PageResult.error("组织机构编号不能为空！");
        }
        SysOrg org = dao.getByKey(orgId);
        if (org == null) {
            return PageResult.error("要删除的组织机构不存在！");
        }
        // 权限校验时要验证父级组织机构
        pd.put("parentId", org.getParentId());
        PageResult result = this.saveVerify(pd);
        if (!result.isOk()) {
            return result;
        }
        pd = (PageData) result.getData();
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }

        // 查询当前组织机构是否有子级组织机构
        pd.put("parentId", orgId);
        pd.put("status", StatusEnum.NORMAL.getKey());
        if (sysOrgDao.hasSon(pd) > 0) {
            return PageResult.error("请先删除子公司或部门！");
        }

        // 软删除组织机构
        pd.put("status", StatusEnum.DELETE.getKey());
        int count = sysOrgDao.updateStatus(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("组织机构删除失败！");
    }

    @Override
    public PageResult hasExist(PageData pd) {
        if (pd == null || pd.isEmpty() || pd.get("orgCode") == null) {
            return PageResult.ok();
        }
        int hasExist = sysOrgDao.hasExist(pd);
        if (hasExist > 0) {
            return PageResult.error("组织机构代码已存在！");
        }
        return PageResult.ok();
    }

    @Override
    public Long maxOrgId(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            throw new D7cRuntimeException("parameters cannot be null！");
        }
        // 当前登录用户编号
        Long session_userId = pd.getLong(D7cConstant.SESSION_USER_ID);
        if (session_userId == null) {
            throw new D7cRuntimeException("SESSION_USER_ID cannot be null！");
        }
        if (session_userId.equals(D7cConstant.SUPER_ADMIN_ID)) {
            return 0L;
        }
        PageData user = sysUserDao.getUserInfoByUserId(session_userId);
        if (user == null || user.isEmpty()) {
            throw new D7cRuntimeException("当前登录用户不存在！");
        }
        // 当前登录用户组织机构编号
        Long session_orgId = user.getLong("orgId");
        if (session_orgId == null) {
            throw new D7cRuntimeException("当前用户组织机构编号为空！");
        }
        return session_orgId;
    }

    @Override
    public List<SelectTree> listSelectTreeByParentId(PageData pd) {
        // 查询出所有组织机构列表
        List<SelectTree> allOrg = sysOrgDao.listSelectTreeBy(null);
        if (allOrg != null && !allOrg.isEmpty()) {
            return new SelectTreeUtil().dealSelectTree(allOrg, this.maxOrgId(pd));
        }
        return new ArrayList<SelectTree>();
    }

    @Override
    public PageData getOrgInfoByUserId(Long userId) {
        return sysOrgDao.getOrgInfoByUserId(userId);
    }

    @Override
    public PageData getOrgInfoByOrgId(Long orgId) {
        return sysOrgDao.getOrgInfoByOrgId(orgId);
    }

}