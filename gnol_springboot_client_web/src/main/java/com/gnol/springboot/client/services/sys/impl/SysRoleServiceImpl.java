package com.gnol.springboot.client.services.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.plugins.core.context.AbstractBaseService;
import com.gnol.plugins.core.context.IdService;
import com.gnol.plugins.core.exception.GnolRuntimeException;
import com.gnol.springboot.client.config.GnolConstant;
import com.gnol.springboot.client.daos.sys.ExtSysRoleDao;
import com.gnol.springboot.client.daos.sys.ExtSysUserDao;
import com.gnol.springboot.client.dtos.tree.SelectTree;
import com.gnol.springboot.client.dtos.tree.ZTree;
import com.gnol.springboot.client.dtos.tree.ZTreeUtil;
import com.gnol.springboot.client.services.sys.SysRoleMenuService;
import com.gnol.springboot.client.services.sys.SysRoleService;
import com.gnol.springboot.common.daos.sys.BaseSysRoleDao;
import com.gnol.springboot.common.dos.sys.SysRole;
import com.gnol.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: SysRoleServiceImpl
 * @Package: com.gnol.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:57
 * @Description: gnol系统角色表 Service 实现
 */
@Service(value = "sysRoleServiceImpl")
public class SysRoleServiceImpl extends AbstractBaseService<BaseSysRoleDao, SysRole, Long> implements SysRoleService {
    /**
     * gnol系统角色表扩展 Dao
     */
    @Resource(name = "extSysRoleDao")
    private ExtSysRoleDao sysRoleDao;
    /**
     * gnol系统_用户表扩展 Dao
     */
    @Resource(name = "extSysUserDao")
    private ExtSysUserDao sysUserDao;
    /**
     * gnol 系统角色菜单表 Service
     */
    @Resource(name = "sysRoleMenuServiceImpl")
    private SysRoleMenuService sysRoleMenuService;
    /**
     * ID 生成服务
     */
    @Resource(name = "dbIdServiceImpl")
    private IdService idService;

    @Override
    public PageData getAllParentRoleName(Long roleId) {
        if (roleId == null) {
            return null;
        }
        return sysRoleDao.getAllParentRoleName(roleId);
    }

    @Override
    public List<ZTree> listZTreeByParentId_SYNC(PageData pd) {
        // 查询出所有角色列表
        List<ZTree> allRole = sysRoleDao.listZTreeByParentId(null);
        if (allRole != null && !allRole.isEmpty()) {
            return new ZTreeUtil().dealZtree3_5(allRole, this.maxRoleId(pd));
        }
        return new ArrayList<ZTree>();
    }

    @Override
    public Long maxRoleId(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            throw new GnolRuntimeException("parameters cannot be null！");
        }
        // 当前登录用户编号
        Long session_userId = pd.getLong(GnolConstant.SESSION_USER_ID);
        if (session_userId == null) {
            throw new GnolRuntimeException("SESSION_USER_ID cannot be null！");
        }
        if (session_userId.equals(GnolConstant.SUPER_ADMIN_ID)) {
            return 0L;
        }
        PageData role = sysRoleDao.getRoleInfoByUserId(session_userId);
        if (role == null || role.isEmpty()) {
            throw new GnolRuntimeException("当前登录用户角色信息不存在！");
        }
        // 当前登录用户父角色编号
        Long session_parentId = role.getLong("parentId");
        return session_parentId.compareTo(0L) == 0 ? role.getLong("roleId") : session_parentId;
    }

    @Override
    public List<SelectTree> listTopSelectTreeBy() {
        return sysRoleDao.listSelectTreeByParentId(0L);
    }

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        // 增加权限过滤
        PageData args = page.getArgs();
        if (args == null || args.isEmpty()) {
            throw new GnolRuntimeException("parameters cannot be null！");
        }
        // 当前登录用户编号
        Long session_userId = args.getLong(GnolConstant.SESSION_USER_ID);
        if (session_userId == null) {
            throw new GnolRuntimeException("SESSION_USER_ID cannot be null！");
        }
        if (session_userId.equals(GnolConstant.SUPER_ADMIN_ID)) {
            args.put("parentId", args.get("parentId") == null ? 0L : args.get("parentId"));
        } else {
            PageData user = sysUserDao.getUserInfoByUserId(session_userId);
            if (user == null || user.isEmpty()) {
                throw new GnolRuntimeException("当前登录用户不存在！");
            }
            // 当前登录用户角色编号
            Long session_roleId = user.getLong("roleId");
            args.put("parentId", session_roleId);
        }
        page.setArgs(args);
        return PageResult.ok(sysRoleDao.listPDPage(page)).setPage(page);
    }

    @Override
    public PageResult insertRole(PageData pd) {
        PageResult result = this.saveVerify(pd);
        if (!result.isOk()) {
            return result;
        }
        pd = (PageData) result.getData();
        String roleName = pd.getString("roleName");
        if (StringUtil.isBlank(roleName)) {
            return PageResult.error("roleName 不能为空！");
        }
        Integer sort = pd.getInteger("sort");
        if (sort == null) {
            return PageResult.error("sort 不是数字！");
        }
        Long addUser = pd.getLong("addUser");
        if (addUser == null) {
            return PageResult.error("保存用户不能为空！");
        }
        SysRole role = new SysRole();
        role.setRoleId(idService.getLong(SysRole.M.TABLE_NAME));
        role.setParentId(pd.getLong("parentId"));
        role.setRoleName(roleName);
        role.setMenuQx("0");
        role.setSort(sort);
        role.setRemark(pd.getString("remark"));
        role.setAddUser(addUser);
        role.setStatus_Enum(StatusEnum.NORMAL);
        int insert = dao.insert(role);
        if (insert == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateRole(PageData pd) {
        PageResult result = this.saveVerify(pd);
        if (!result.isOk()) {
            return result;
        }
        pd = (PageData) result.getData();
        String roleName = pd.getString("roleName");
        if (StringUtil.isBlank(roleName)) {
            return PageResult.error("roleName 不能为空！");
        }
        Integer sort = pd.getInteger("sort");
        if (sort == null) {
            return PageResult.error("sort 不是数字！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        SysRole role = new SysRole();
        role.setRoleId(pd.getLong("roleId"));
        role.setRoleName(roleName);
        role.setSort(sort);
        role.setRemark(pd.getString("remark"));
        role.setModifyUser(modifyUser);
        role.setCheckValue(checkValue);
        int update = dao.update(role);
        if (update == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateStatus(PageData pd) {
        PageResult result = this.saveVerify(pd);
        if (!result.isOk()) {
            return result;
        }
        pd = (PageData) result.getData();
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        // 清空该角色的权限
        sysRoleMenuService.deleteByKey(pd.getLong("roleId"));
        pd.put("status", StatusEnum.DELETE.getKey());
        int count = sysRoleDao.updateStatus(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("角色删除失败！");
    }

    @Override
    public PageResult saveVerify(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        // 当前登录用户编号
        Long session_userId = pd.getLong(GnolConstant.SESSION_USER_ID);
        if (session_userId == null) {
            return PageResult.error("SESSION_USER_ID cannot be null");
        }
        if (session_userId.equals(GnolConstant.SUPER_ADMIN_ID)) { // 超级管理员
            Long parentId = pd.getLong("parentId");
            pd.put("parentId", parentId == null ? 0L : parentId);
            return PageResult.ok(pd);
        }
        return PageResult.error("您不是超级管理员，不能修改角色信息！");
    }

}