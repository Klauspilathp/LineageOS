package top.d7c.springboot.client.services.sys.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.StringUtil;
import top.d7c.plugins.core.context.AbstractBaseService;
import top.d7c.springboot.client.config.D7cConstant;
import top.d7c.springboot.client.daos.sys.ExtSysRoleMenuDao;
import top.d7c.springboot.client.dtos.tree.ZTree;
import top.d7c.springboot.client.dtos.tree.ZTreeUtil;
import top.d7c.springboot.client.services.sys.SysRoleMenuService;
import top.d7c.springboot.common.daos.sys.BaseSysRoleMenuDao;
import top.d7c.springboot.common.dos.sys.SysRoleMenu;

/**
 * @Title: SysRoleMenuServiceImpl
 * @Package: top.d7c.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:49
 * @Description: d7c 系统角色菜单表 Service 实现
 */
@Service(value = "sysRoleMenuServiceImpl")
public class SysRoleMenuServiceImpl extends AbstractBaseService<BaseSysRoleMenuDao, SysRoleMenu, Long>
        implements SysRoleMenuService {
    /**
     * d7c系统角色菜单表扩展 Dao
     */
    @Resource(name = "extSysRoleMenuDao")
    private ExtSysRoleMenuDao sysRoleMenuDao;

    @Override
    public List<ZTree> listAuthByRoleId(PageData pd) {
        if (pd == null || pd.isEmpty() || pd.getLong("roleId") == null
                || pd.getLong(D7cConstant.SESSION_USER_ID) == null) {
            return new ArrayList<ZTree>();
        }
        // 当前登录用户编号
        Long session_userId = pd.getLong(D7cConstant.SESSION_USER_ID);
        // 普通用户不能授权业务菜单
        if (session_userId == null || !session_userId.equals(D7cConstant.SUPER_ADMIN_ID)) {
            return new ArrayList<ZTree>();
        }
        List<ZTree> allMenu = sysRoleMenuDao.listAuthByRoleId(pd);
        if (allMenu != null && !allMenu.isEmpty()) {
            return new ZTreeUtil().dealZtree2_6(allMenu, 0L);
        }
        return new ArrayList<ZTree>();
    }

    @Override
    public PageResult saveAuth(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return PageResult.error("保存对象不能为空");
        }
        // 当前登录用户编号
        Long session_userId = pd.getLong(D7cConstant.SESSION_USER_ID);
        if (session_userId == null) {
            return PageResult.error("SESSION_USER_ID cannot be null");
        }
        // 不是超级管理员
        if (!session_userId.equals(D7cConstant.SUPER_ADMIN_ID)) {
            return PageResult.error("不是超级管理员，不能授权角色");
        }
        Long roleId = pd.getLong("roleId");
        if (roleId == null || roleId.compareTo(0L) != 1) {
            return PageResult.error("角色编号不能为空！");
        }
        String[] menuIds = StringUtil.splitStrToArray(pd.getString("menuIds"));
        List<SysRoleMenu> roleMenus = null;
        if (menuIds != null) {
            roleMenus = new ArrayList<SysRoleMenu>();
            for (String menuId : menuIds) {
                Integer mid = StringUtil.toInteger(menuId);
                if (mid != null) {
                    roleMenus.add(new SysRoleMenu(roleId, mid));
                }
            }
        }
        // 先解除权限关系
        dao.deleteByKey(roleId);
        // 设置新的权限关系
        if (roleMenus != null && !roleMenus.isEmpty()) {
            dao.insertBatch(roleMenus);
        }
        return PageResult.error("保存成功！");
    }

}