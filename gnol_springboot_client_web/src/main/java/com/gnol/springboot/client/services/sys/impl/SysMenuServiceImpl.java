package com.gnol.springboot.client.services.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.plugins.core.context.AbstractBaseService;
import com.gnol.springboot.client.daos.sys.ExtSysMenuDao;
import com.gnol.springboot.client.dtos.tree.MenuTree;
import com.gnol.springboot.client.dtos.tree.MenuTreeUtil;
import com.gnol.springboot.client.dtos.tree.ZTree;
import com.gnol.springboot.client.dtos.tree.ZTreeUtil;
import com.gnol.springboot.client.services.sys.SysMenuService;
import com.gnol.springboot.common.daos.sys.BaseSysMenuDao;
import com.gnol.springboot.common.dos.sys.SysMenu;
import com.gnol.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: SysMenuServiceImpl
 * @Package: com.gnol.springboot.client.services.sys.impl
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:14
 * @Description: gnol 系统菜单表 Service 实现
 */
@Service(value = "sysMenuServiceImpl")
public class SysMenuServiceImpl extends AbstractBaseService<BaseSysMenuDao, SysMenu, Integer>
        implements SysMenuService {
    /**
     * gnol 系统菜单表扩展 Dao
     */
    @Resource(name = "extSysMenuDao")
    private ExtSysMenuDao sysMenuDao;

    @Override
    public Set<SimpleGrantedAuthority> listPermissionsByRoleId(Long roleId) {
        if (roleId == null) {
            return null;
        }
        return sysMenuDao.listPermissionsByRoleId(roleId);
    }

    @Override
    public Set<String> listPermissionsBySessionId(String sessionId) {
        if (StringUtil.isBlank(sessionId)) {
            return null;
        }
        return sysMenuDao.listPermissionsBySessionId(sessionId);
    }

    @Override
    public List<MenuTree> listMenuTreeByParentId(Integer parentId) {
        List<MenuTree> allMenuTree = sysMenuDao.listMenuTreeByParentId(null);
        if (allMenuTree != null && !allMenuTree.isEmpty()) {
            return new MenuTreeUtil().dealMenuTree(allMenuTree, parentId);
        }
        return new ArrayList<MenuTree>();
    }

    @Override
    public List<MenuTree> listMenuTreeByRoleId(Long roleId) {
        List<MenuTree> allMenuTree = sysMenuDao.listMenuTreeByRoleId(roleId);
        if (allMenuTree != null && !allMenuTree.isEmpty()) {
            return new MenuTreeUtil().dealMenuTree(allMenuTree, 0);
        }
        return new ArrayList<MenuTree>();
    }

    @Override
    public List<ZTree> listZTreeFormTreeFrameByParentId_SYNC(Integer parentId) {
        // 查询出所有菜单信息列表
        List<ZTree> allMenu = sysMenuDao.listZTreeFormTreeFrameByParentId(null);
        if (allMenu != null && !allMenu.isEmpty()) {
            return new ZTreeUtil().dealZtree2_6(allMenu, parentId == null ? 0L : parentId.longValue());
        }
        return new ArrayList<ZTree>();
    }

    @Override
    public List<PageData> listMenuByParentId(Integer parentId) {
        if (parentId == null) {
            parentId = 0;
        }
        return sysMenuDao.listMenuByParentId(parentId);
    }

    @Override
    public PageResult updateIcon(PageData pd) {
        if (pd == null) {
            return PageResult.error("保存对象不能为空！");
        }
        int count = sysMenuDao.updateIcon(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("请求失败！");
    }

    @Override
    public int hasSon(Integer parentId) {
        if (parentId == null) {
            parentId = 0;
        }
        return sysMenuDao.hasSon(parentId);
    }

    @Override
    public PageResult updateStatus(PageData pd) {
        if (pd == null) {
            return PageResult.error("保存对象不能为空！");
        }
        Integer menuId = pd.getInteger("menuId");
        if (menuId == null) {
            return PageResult.error("请传入要删除的菜单编号！");
        }
        Integer checkValue = pd.getInteger("checkValue");
        if (checkValue == null) {
            return PageResult.error("checkValue 不能为空！");
        }
        Long modifyUser = pd.getLong("modifyUser");
        if (modifyUser == null) {
            return PageResult.error("修改用户不能为空！");
        }
        int menu_count = this.hasSon(menuId);
        if (menu_count > 0) {
            return PageResult.error("请先删除子菜单！");
        }
        pd.put("status", StatusEnum.DELETE.getKey());
        int count = sysMenuDao.updateStatus(pd);
        return count == 1 ? PageResult.ok() : PageResult.error("菜单删除失败！");
    }

}