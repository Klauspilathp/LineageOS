package com.d7c.springboot.client.services.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.context.AbstractBaseService;
import com.d7c.springboot.client.daos.sys.ExtSysSessionDao;
import com.d7c.springboot.client.services.sys.SysOrgService;
import com.d7c.springboot.client.services.sys.SysRoleService;
import com.d7c.springboot.client.services.sys.SysSessionService;
import com.d7c.springboot.common.daos.sys.BaseSysSessionDao;
import com.d7c.springboot.common.dos.sys.SysOrg;
import com.d7c.springboot.common.dos.sys.SysSession;
import com.d7c.springboot.common.enums.sys.LevelEnum;

/**
 * @Title: SysUserTemServiceImpl
 * @Package: com.d7c.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:45
 * @Description: d7c 系统_用户详情权限临时表，用户在登录，下线等情况下触发插入、删除、更新操作，然后以此表关联业务表查询指定范围内的 Service 实现
 */
@Service(value = "sysSessionServiceImpl")
public class SysSessionServiceImpl extends AbstractBaseService<BaseSysSessionDao, SysSession, Long>
        implements SysSessionService {
    /**
     * d7c 系统_用户详情权限临时表，用户在登录，下线等情况下触发插入、删除、更新操作，然后以此表关联业务表查询指定范围内的扩展 Dao
     */
    @Resource(name = "extSysSessionDao")
    private ExtSysSessionDao sysSessionDao;
    /**
     * d7c 系统角色表 Service 实现
     */
    @Resource(name = "sysRoleServiceImpl")
    private SysRoleService sysRoleService;
    /**
     * d7c 系统组织机构 Service
     */
    @Resource(name = "sysOrgServiceImpl")
    private SysOrgService sysOrgService;

    @Override
    public int insertReplace(SysSession sysSession) {
        return sysSessionDao.insertReplace(sysSession);
    }

    @Override
    public SysSession updateUserAddress(SysSession session) {
        if (session == null) {
            return session;
        }
        // 设置角色名称
        PageData role = sysRoleService.getAllParentRoleName(session.getRoleId());
        if (role != null) {
            session.setRoleName(role.getString("roleNameList"));
            session.setMenuQx(role.getString("menuQx"));
        }
        // 设置组织机构信息
        SysOrg org = sysOrgService.getByKey(session.getOrgId());
        if (org != null) {
            session.setBlocHQId(org.getBlocHQId());
            session.setBlocHQName(org.getBlocHQNameCN());
            session.setAreaHQId(org.getAreaHQId());
            session.setAreaHQName(org.getAreaHQNameCN());
            session.setCompanyId(org.getCompanyId());
            session.setCompanyName(org.getCompanyNameCN());
            if (LevelEnum.equalValue(LevelEnum.DEPARTMENT, org.getLevel())) {
                session.setDepartmentId(org.getOrgId());
                session.setDepartmentName(org.getOrgNameCN());
            }
        }
        // 将用户对象插入 d7c 系统_用户详情权限临时表，用户在登录，下线等情况下触发插入、删除、更新操作，然后以此表关联业务表查询指定范围内的 pojo
        sysSessionDao.insertReplace(session);
        return session;
    }

    @Override
    public String getMenuQXBySessionId(String sessionId) {
        return sysSessionDao.getMenuQXBySessionId(sessionId);
    }

    @Override
    public Long getUserIdBySessionId(String sessionId) {
        return sysSessionDao.getUserIdBySessionId(sessionId);
    }

}