package com.d7c.springboot.client.services.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.context.AbstractBaseService;
import com.d7c.plugins.core.context.IdService;
import com.d7c.plugins.core.exception.D7cRuntimeException;
import com.d7c.plugins.tools.date.DateUtil;
import com.d7c.springboot.client.config.D7cConstant;
import com.d7c.springboot.client.daos.sys.ExtSysUserDao;
import com.d7c.springboot.client.services.sys.SysOrgService;
import com.d7c.springboot.client.services.sys.SysRoleService;
import com.d7c.springboot.client.services.sys.SysUserService;
import com.d7c.springboot.common.daos.sys.BaseSysUserDao;
import com.d7c.springboot.common.dos.sys.SysOrg;
import com.d7c.springboot.common.dos.sys.SysRole;
import com.d7c.springboot.common.dos.sys.SysUser;
import com.d7c.springboot.common.enums.sys.LevelEnum;
import com.d7c.springboot.common.enums.sys.LoginStatusEnum;
import com.d7c.springboot.common.enums.sys.SexEnum;
import com.d7c.springboot.common.enums.sys.StatusEnum;
import com.d7c.springboot.common.enums.sys.UserTypeEnum;
import com.d7c.springboot.common.enums.sys.YesNoEnum;

/**
 * @Title: SysUserServiceImpl
 * @Package: com.d7c.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:35
 * @Description: d7c 系统_用户表 Service 实现
 */
@Service(value = "sysUserServiceImpl")
public class SysUserServiceImpl extends AbstractBaseService<BaseSysUserDao, SysUser, Long> implements SysUserService {
    /**
     * d7c系统_用户表扩展 Dao
     */
    @Resource(name = "extSysUserDao")
    private ExtSysUserDao sysUserDao;
    /**
     * d7c 系统角色表 Service
     */
    @Resource(name = "sysRoleServiceImpl")
    private SysRoleService sysRoleService;
    /**
     * d7c 系统组织机构 Service
     */
    @Resource(name = "sysOrgServiceImpl")
    private SysOrgService sysOrgService;
    /**
     * ID 生成服务
     */
    @Resource(name = "dbIdServiceImpl")
    private IdService idService;

    @Override
    public SysUser getSysUserByUserAccount(String userAccount) {
        if (StringUtil.isBlank(userAccount)) {
            return null;
        }
        return sysUserDao.getSysUserByUserAccount(userAccount);
    }

    @Override
    public PageResult updateByLogin(SysUser user) {
        if (user == null) {
            return PageResult.error("保存对象不能为空！");
        }
        int count = sysUserDao.updateByLogin(user);
        return count == 1 ? PageResult.ok() : PageResult.error("请求失败！");
    }

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        // 增加权限过滤
        PageData args = page.getArgs();
        if (args == null || args.isEmpty()) {
            throw new D7cRuntimeException("parameters cannot be null！");
        }
        // 当前登录用户编号
        Long session_userId = args.getLong(D7cConstant.SESSION_USER_ID);
        if (session_userId == null) {
            throw new D7cRuntimeException("SESSION_USER_ID cannot be null！");
        }
        if (!session_userId.equals(D7cConstant.SUPER_ADMIN_ID)) { // 不是超级管理员
            PageData org = sysOrgService.getOrgInfoByUserId(session_userId);
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
            // 限定它的上级组织机构查询条件
            switch (levelEnum) {
                case BLOC_HQ:
                    args.put("blocHQId", session_orgId);
                    break;
                case AREA_HQ:
                    args.put("areaHQId", session_orgId);
                    break;
                case COMPANY:
                    args.put("companyId", session_orgId);
                    break;
                case DEPARTMENT:
                    args.put("orgId", session_orgId);
                    break;
                default:
                    break;
            }
        }
        page.setArgs(args);
        return PageResult.ok(sysUserDao.listPDPage(page)).setPage(page);
    }

    @Override
    public PageResult insertUser(PageData pd) {
        PageResult result = this.saveVerify(pd);
        if (!result.isOk()) {
            return result;
        }
        pd = (PageData) result.getData();
        String userAccount = pd.getString("userAccount");
        if (StringUtil.isBlank(userAccount)) {
            return PageResult.error("userAccount 不能为空！");
        }
        String password = pd.getString("password");
        if (StringUtil.isBlank(password)) {
            return PageResult.error("password 不能为空！");
        }
        String nickname = pd.getString("nickname");
        if (StringUtil.isBlank(nickname)) {
            return PageResult.error("nickname 不能为空！");
        }
        String birthday = pd.getString("birthday");
        if (StringUtil.isBlank(birthday)) {
            return PageResult.error("birthday 不能为空！");
        }
        SexEnum sex = SexEnum.forKey(pd.getInteger("sex"));
        if (sex == null) {
            return PageResult.error("sex 不能为空！");
        }
        String phone = pd.getString("phone");
        if (StringUtil.isBlank(phone)) {
            return PageResult.error("phone 不能为空！");
        }
        String email = pd.getString("email");
        if (StringUtil.isBlank(email)) {
            return PageResult.error("email 不能为空！");
        }
        Long addUser = pd.getLong("addUser");
        if (addUser == null) {
            return PageResult.error("保存用户不能为空！");
        }
        UserTypeEnum userType = UserTypeEnum.forKey(pd.getInteger("userType"));
        if (userType == null) {
            return PageResult.error("userType 不能为空！");
        }

        // 判断添加的用户 userAccount、phone、email 是否在数据库中已经存在
        StringBuilder sb = new StringBuilder("userAccount = '");
        sb.append(userAccount);
        sb.append("' OR phone = '");
        sb.append(phone);
        sb.append("' OR email = '");
        sb.append(email);
        sb.append("'");
        int hasExist = sysUserDao.hasExist(sb.toString());
        if (hasExist > 0) {
            return PageResult.error("userAccount、phone 或 email 不唯一！");
        }

        SysUser user = new SysUser();
        user.setUserId(idService.getLong(SysUser.M.TABLE_NAME));
        user.setRoleId(pd.getLong("roleId"));
        // 工号每次从数据库中获取一个值
        long jobNumber = idService.getLong(SysUser.M.TABLE_NAME + "." + SysUser.M.jobNumber, 1);
        user.setJobNumber(String.format("%010d", jobNumber));
        user.setUserAccount(userAccount);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setBirthday(birthday);
        user.setAge(DateUtil.getYearNum(birthday));
        user.setSex(sex.getKey());
        user.setPhone(phone);
        user.setWxOpenid(pd.getString("wxOpenid"));
        user.setEmail(email);
        user.setSignature(pd.getString("signature"));
        user.setUserType(userType.getKey());
        user.setOrgId(pd.getLong("orgId"));
        YesNoEnum administrator = YesNoEnum.forKey(pd.getInteger("administrator"));
        user.setAdministrator(administrator == null ? YesNoEnum.NO.getKey() : administrator.getKey());
        user.setLoginStatus(LoginStatusEnum.OFF_LINE.getKey());
        user.setRemark(pd.getString("remark"));
        user.setAddUser(addUser);
        user.setStatus_Enum(StatusEnum.NORMAL);
        int insert = dao.insert(user);
        if (insert == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateUser(PageData pd) {
        PageResult result = this.saveVerify(pd);
        if (!result.isOk()) {
            return result;
        }
        pd = (PageData) result.getData();
        Long userId = pd.getLong("userId");
        if (userId == null) {
            return PageResult.error("userId 不能为空！");
        }
        String nickname = pd.getString("nickname");
        if (StringUtil.isBlank(nickname)) {
            return PageResult.error("nickname 不能为空！");
        }
        String birthday = pd.getString("birthday");
        if (StringUtil.isBlank(birthday)) {
            return PageResult.error("birthday 不能为空！");
        }
        SexEnum sex = SexEnum.forKey(pd.getInteger("sex"));
        if (sex == null) {
            return PageResult.error("sex 不能为空！");
        }
        String phone = pd.getString("phone");
        if (StringUtil.isBlank(phone)) {
            return PageResult.error("phone 不能为空！");
        }
        String email = pd.getString("email");
        if (StringUtil.isBlank(email)) {
            return PageResult.error("email 不能为空！");
        }
        UserTypeEnum userType = UserTypeEnum.forKey(pd.getInteger("userType"));
        if (userType == null) {
            return PageResult.error("userType 不能为空！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }

        // 判断添加的用户 userAccount、phone、email 是否在数据库中已经存在
        StringBuilder sb = new StringBuilder("userId != ");
        sb.append(userId);
        sb.append(" AND (phone = '");
        sb.append(phone);
        sb.append("' OR email = '");
        sb.append(email);
        sb.append("')");
        int hasExist = sysUserDao.hasExist(sb.toString());
        if (hasExist > 0) {
            return PageResult.error("userAccount、phone 或 email 不唯一！");
        }

        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setRoleId(pd.getLong("roleId"));
        String password = pd.getString("password");
        if (StringUtil.isNotBlank(password)) {
            user.setPassword(password);
        }
        user.setNickname(nickname);
        user.setBirthday(birthday);
        user.setAge(DateUtil.getYearNum(birthday));
        user.setSex(sex.getKey());
        user.setPhone(phone);
        user.setWxOpenid(pd.getString("wxOpenid"));
        user.setEmail(email);
        user.setSignature(pd.getString("signature"));
        user.setUserType(userType.getKey());
        user.setOrgId(pd.getLong("orgId"));
        YesNoEnum administrator = YesNoEnum.forKey(pd.getInteger("administrator"));
        user.setAdministrator(administrator == null ? YesNoEnum.NO.getKey() : administrator.getKey());
        user.setRemark(pd.getString("remark"));
        user.setModifyUser(modifyUser);
        user.setCheckValue(checkValue);
        int insert = dao.update(user);
        if (insert == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult saveVerify(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long roleId = pd.getLong("roleId");
        if (roleId == null) {
            return PageResult.error("角色编号不能为空！");
        }
        Long orgId = pd.getLong("orgId");
        if (orgId == null) {
            return PageResult.error("orgId 不能为空！");
        }
        // 当前登录用户编号
        Long session_userId = pd.getLong(D7cConstant.SESSION_USER_ID);
        if (session_userId == null) {
            return PageResult.error("SESSION_USER_ID cannot be null");
        }
        if (session_userId.equals(D7cConstant.SUPER_ADMIN_ID)) { // 超级管理员
            return PageResult.ok(pd);
        }
        Long userId = pd.getLong("userId");
        if (userId != null && userId.equals(D7cConstant.SUPER_ADMIN_ID)) {
            return PageResult.error("您不能修改超级管理员信息！");
        }
        // 普通用户添加用户
        PageData user = sysUserDao.getUserInfoByUserId(session_userId);
        if (user == null || user.isEmpty()) {
            return PageResult.error("当前登录用户不存在！");
        }
        // 当前登录用户角色编号
        Long session_roleId = user.getLong("roleId");
        if (session_roleId == null) {
            return PageResult.error("当前用户角色编号为空！");
        }
        Long session_orgId = user.getLong("orgId");
        if (session_orgId == null) {
            return PageResult.error("当前用户组织机构编号为空！");
        }

        // 用户类型验证
        if (StringUtil.toInt(user.get("userType")) > StringUtil.toInt(pd.get("userType"))) {
            return PageResult.error("您不能添加高于自己的用户类型！");
        }

        // 是否是管理员
        boolean administrator = YesNoEnum.equalValue(YesNoEnum.YES, user.getInteger("administrator"));

        // 角色权限验证
        SysRole role = sysRoleService.getByKey(roleId);
        if (role == null) {
            return PageResult.error("要保存的角色不存在！");
        }
        if (userId == null) { // 新增用户
            if (administrator) {
                /**
                 * 当前用户是角色管理员，可以添加当前角色管理员及其子角色
                 */
                if (!session_roleId.equals(roleId)) { // 添加的不是当前角色，只能是子角色
                    if (!role.getParentId().equals(session_roleId)) {
                        return PageResult.error("您没有添加此角色的权限！");
                    }
                }
            } else {
                /**
                 * 当前用户不是角色管理员，只能添加子角色
                 */
                if (!role.getParentId().equals(session_roleId)) {
                    return PageResult.error("您没有添加此角色的权限！");
                }
            }
        } else { // 修改用户
            if (userId.compareTo(session_userId) == 0) { // 修改自己信息
                if (!session_roleId.equals(roleId)) {
                    return PageResult.error("您不能修改自己的角色信息！");
                }
                if (administrator != YesNoEnum.equalValue(YesNoEnum.YES, pd.getInteger("administrator"))) {
                    return PageResult.error("您不能修改自己的管理员信息！");
                }
            } else { // 修改别人信息
                if (administrator) {
                    /**
                     * 当前用户是角色管理员，可以添加当前角色管理员及其子角色
                     */
                    if (!session_roleId.equals(roleId)) { // 添加的不是当前角色，只能是子角色
                        if (!role.getParentId().equals(session_roleId)) {
                            return PageResult.error("您没有修改此角色的权限！");
                        }
                    }
                } else {
                    /**
                     * 当前用户不是角色管理员，只能添加子角色
                     */
                    if (!role.getParentId().equals(session_roleId)) {
                        return PageResult.error("您没有修改此角色的权限！");
                    }
                }
            }
        }

        // 组织机构验证
        SysOrg org = sysOrgService.getByKey(orgId);
        if (org == null) {
            return PageResult.error("组织机构不存在！");
        }
        if (userId == null) { // 新增用户
            switch (LevelEnum.forKey(org.getLevel())) {
                case BLOC_HQ: // 集团总部用户
                    if (!orgId.equals(session_orgId) || !administrator) {
                        return PageResult.error("您不是该集团总部管理员，不能新增员工！");
                    }
                    break;
                case AREA_HQ: // 区域总部用户
                    if (!(session_orgId.equals(org.getBlocHQId()) // 是集团用户可以新增区域总部用户
                            || (session_orgId.equals(org.getAreaHQId()) && administrator) // 是区域总部用户并且是管理员可以新增区域总部用户
                    )) {
                        return PageResult.error("您不是该集团总部用户，也不是区域总部管理员，不能新增员工！");
                    }
                    break;
                case COMPANY: // 分公司用户
                    if (!(session_orgId.equals(org.getAreaHQId()) // 是区域总部用户可以新增分公司用户
                            || (session_orgId.equals(org.getCompanyId()) && administrator) // 是分公司用户并且是管理员可以新增分公司用户
                    )) {
                        return PageResult.error("您不是区域总部用户，也不是分公司管理员，不能新增员工！");
                    }
                    break;
                case DEPARTMENT: // 部门
                    if (!(session_orgId.equals(org.getCompanyId()) // 是分公司用户可以新增部门用户
                            || (session_orgId.equals(org.getCompanyId()) && administrator) // 是部门用户并且是管理员可以新增部门用户
                    )) {
                        return PageResult.error("您不是分公司用户，也不是部门管理员，不能新增员工！");
                    }
                    break;
                default:
                    return PageResult.error("级别有误，不能新增员工！");
            }
        } else { // 修改用户
            if (userId.compareTo(session_userId) == 0) { // 修改自己信息
                if (!session_orgId.equals(orgId)) {
                    return PageResult.error("您不能修改自己的组织机构信息！");
                }
            } else { // 修改别人信息
                switch (LevelEnum.forKey(org.getLevel())) {
                    case BLOC_HQ: // 集团总部用户
                        if (!orgId.equals(session_orgId) || !administrator) {
                            return PageResult.error("您不是该集团总部管理员，不能修改员工信息！");
                        }
                        break;
                    case AREA_HQ: // 区域总部用户
                        if (!(session_orgId.equals(org.getBlocHQId()) // 是集团用户可以修改区域总部用户
                                || (session_orgId.equals(org.getAreaHQId()) && administrator) // 是区域总部用户并且是管理员可以修改区域总部用户
                        )) {
                            return PageResult.error("您不是该集团总部用户，也不是区域总部管理员，不能修改员工信息！");
                        }
                        break;
                    case COMPANY:
                        if (!(session_orgId.equals(org.getAreaHQId()) // 是区域总部用户可以修改分公司用户
                                || (session_orgId.equals(org.getCompanyId()) && administrator) // 是分公司用户并且是管理员可以修改分公司用户
                        )) {
                            return PageResult.error("您不是区域总部用户，也不是分公司管理员，不能修改员工信息！");
                        }
                        break;
                    case DEPARTMENT:
                        if (!(session_orgId.equals(org.getCompanyId()) // 是分公司用户可以修改部门用户
                                || (session_orgId.equals(org.getCompanyId()) && administrator) // 是部门用户并且是管理员可以修改部门用户
                        )) {
                            return PageResult.error("您不是分公司用户，也不是部门管理员，不能修改员工信息！");
                        }
                        break;
                    default:
                        return PageResult.error("级别有误，不能修改员工！");
                }
            }
        }
        return PageResult.ok(pd);
    }

    @Override
    public PageResult updateStatus(PageData pd) {
        PageResult result = this.delVerify(pd);
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
        pd.put("status", StatusEnum.DELETE.getKey());
        int count = sysUserDao.updateStatus(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("用户删除失败！");
    }

    @Override
    public PageResult delVerify(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        // 要删除用户编号
        Long del_userId = pd.getLong("userId");
        if (del_userId == null) {
            return PageResult.error("请传入要删除用户的编号！");
        }
        if (del_userId.equals(D7cConstant.SUPER_ADMIN_ID)) {
            return PageResult.error("不能软删除超级管理员信息！");
        }
        // 当前登录用户编号
        Long session_userId = pd.getLong(D7cConstant.SESSION_USER_ID);
        if (session_userId == null) {
            return PageResult.error("SESSION_USER_ID cannot be null");
        }
        if (session_userId.equals(D7cConstant.SUPER_ADMIN_ID)) { // 超级管理员
            return PageResult.ok(pd);
        }
        // 普通用户删除用户
        PageData session_user = sysUserDao.getUserInfoByUserId(session_userId);
        if (session_user == null || session_user.isEmpty()) {
            return PageResult.error("当前登录用户不存在！");
        }
        Long session_orgId = session_user.getLong("orgId");
        if (session_orgId == null) {
            return PageResult.error("当前用户组织机构编号为空！");
        }
        // 要删除的用户
        PageData del_user = sysUserDao.getUserInfoByUserId(del_userId);
        if (del_user == null || del_user.isEmpty()) {
            return PageResult.error("要删除的用户不存在！");
        }
        Long del_orgId = del_user.getLong("orgId");
        if (del_orgId == null) {
            return PageResult.ok(pd);
        }
        SysOrg del_org = sysOrgService.getByKey(del_orgId);
        if (del_org == null) {
            return PageResult.ok(pd);
        }
        boolean session_administrator = YesNoEnum.equalValue(YesNoEnum.YES, session_user.getInteger("administrator"));
        switch (LevelEnum.forKey(del_org.getLevel())) {
            case BLOC_HQ: // 集团总部用户
                if (!del_orgId.equals(session_orgId) || !session_administrator) {
                    return PageResult.error("您不是该集团总部管理员，不能删除该员工！");
                }
                break;
            case AREA_HQ: // 区域总部用户
                if (!(session_orgId.equals(del_org.getBlocHQId()) // 是集团用户可以删除区域总部用户
                        || (session_orgId.equals(del_org.getAreaHQId()) && session_administrator) // 是区域总部用户并且是管理员可以删除区域总部用户
                )) {
                    return PageResult.error("您不是该集团总部用户，也不是区域总部管理员，不能删除该员工！");
                }
                break;
            case COMPANY:
                if (!(session_orgId.equals(del_org.getAreaHQId()) // 是区域总部用户可以删除分公司用户
                        || (session_orgId.equals(del_org.getCompanyId()) && session_administrator) // 是分公司用户并且是管理员可以删除分公司用户
                )) {
                    return PageResult.error("您不是区域总部用户，也不是分公司管理员，不能删除该员工！");
                }
                break;
            case DEPARTMENT:
                if (!(session_orgId.equals(del_org.getCompanyId()) // 是分公司用户可以删除部门用户
                        || (session_orgId.equals(del_org.getOrgId()) && session_administrator) // 是部门用户并且是管理员可以删除部门用户
                )) {
                    return PageResult.error("您不是分公司用户，也不是部门管理员，不能删除该员工！");
                }
                break;
            default:
                return PageResult.error("级别有误，不能删除员工！");
        }
        return PageResult.ok(pd);
    }

    @Override
    public PageResult hasExist(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.ok();
        }
        List<String> ps = new ArrayList<String>();
        String userAccount = pd.getString("userAccount");
        if (StringUtil.isNotBlank(userAccount)) {
            ps.add(" userAccount = '" + userAccount + "'");
        }
        String phone = pd.getString("phone");
        if (StringUtil.isNotBlank(phone)) {
            ps.add(" phone = '" + phone + "'");
        }
        String email = pd.getString("email");
        if (StringUtil.isNotBlank(email)) {
            ps.add(" email = '" + email + "'");
        }
        if (ps.isEmpty()) {
            return PageResult.ok();
        }

        StringBuilder sb = new StringBuilder();
        Long userId = pd.getLong("userId");
        if (userId == null) {
            if (ps.size() == 1) {
                sb.append(ps.get(0));
            } else {
                for (int i = 0; i < ps.size(); i++) {
                    sb.append(ps.get(i));
                    if (i < ps.size() - 1) {
                        sb.append(" OR ");
                    }
                }
            }
        } else {
            sb.append("userId != ");
            sb.append(userId);
            if (ps.size() == 1) {
                sb.append(" AND ");
                sb.append(ps.get(0));
            } else {
                sb.append(" AND (");
                for (int i = 0; i < ps.size(); i++) {
                    sb.append(ps.get(i));
                    if (i < ps.size() - 1) {
                        sb.append(" OR ");
                    }
                }
                sb.append(")");
            }
        }
        int hasExist = sysUserDao.hasExist(sb.toString());
        if (hasExist > 0) {
            return PageResult.error("账号、手机号或邮箱已存在！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updateBatchStatus(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        // 当前登录用户编号
        Long session_userId = pd.getLong(D7cConstant.SESSION_USER_ID);
        if (session_userId == null) {
            return PageResult.error("SESSION_USER_ID cannot be null");
        }
        if (!session_userId.equals(D7cConstant.SUPER_ADMIN_ID)) {
            return PageResult.error("不是超级管理员，不能使用批量删除功能！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        String userIds = pd.getString("userIds");
        if (StringUtil.isBlank(userIds)) {
            PageResult.ok();
        }
        String[] split = userIds.split(",");
        pd.put("userIds", split);
        pd.put("status", StatusEnum.DELETE.getKey());
        int count = sysUserDao.updateBatchStatus(pd);
        return count > 0 ? PageResult.ok() : PageResult.error("用户删除失败！");
    }

    @Override
    public PageResult updateAvatar(PageData pd) {
        if (pd == null || pd.isEmpty() || pd.get("userId") == null) {
            return PageResult.error("保存对象不能为空");
        }
        int count = sysUserDao.updateAvatar(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("用户头像更新失败！");
    }

    @Override
    public PageResult updateMyInfo(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long userId = pd.getLong("userId");
        if (userId == null) {
            return PageResult.error("userId 不能为空！");
        }
        String nickname = pd.getString("nickname");
        if (StringUtil.isBlank(nickname)) {
            return PageResult.error("nickname 不能为空！");
        }
        SexEnum sex = SexEnum.forKey(pd.getInteger("sex"));
        if (sex == null) {
            return PageResult.error("sex 不能为空！");
        }
        String birthday = pd.getString("birthday");
        if (StringUtil.isBlank(birthday)) {
            return PageResult.error("birthday 不能为空！");
        }
        String phone = pd.getString("phone");
        if (StringUtil.isBlank(phone)) {
            return PageResult.error("phone 不能为空！");
        }
        String email = pd.getString("email");
        if (StringUtil.isBlank(email)) {
            return PageResult.error("email 不能为空！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }

        // 判断添加的用户 phone、email 是否在数据库中已经存在
        StringBuilder sb = new StringBuilder("userId != ");
        sb.append(userId);
        sb.append(" AND (phone = '");
        sb.append(phone);
        sb.append("' OR email = '");
        sb.append(email);
        sb.append("')");
        int hasExist = sysUserDao.hasExist(sb.toString());
        if (hasExist > 0) {
            return PageResult.error("phone 或 email 不唯一！");
        }

        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setNickname(nickname);
        user.setSex(sex.getKey());
        user.setBirthday(birthday);
        user.setAge(DateUtil.getYearNum(birthday));
        user.setPhone(phone);
        user.setEmail(email);
        user.setWxOpenid(pd.getString("wxOpenid"));
        user.setSignature(pd.getString("signature"));
        user.setRemark(pd.getString("remark"));
        user.setCheckValue(checkValue);
        int insert = dao.update(user);
        if (insert == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

    @Override
    public PageResult updatePassword(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        Long userId = pd.getLong("userId");
        if (userId == null) {
            return PageResult.error("userId 不能为空！");
        }
        int insert = sysUserDao.updatePassword(pd);
        if (insert == 0) {
            return PageResult.error("保存失败！");
        }
        return PageResult.ok();
    }

}