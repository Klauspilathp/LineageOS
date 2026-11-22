package com.gnol.springboot.common.pojos.sys;

import java.io.Serializable;

/**
 * @Title: SysRoleMenu
 * @Package: com.gnol.springboot.common.pojos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 09:13:40
 * @Description: gnol 系统角色菜单表 pojo
 */
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 角色主键
     */
    private Long roleId;
    /**
     * 菜单主键
     */
    private Integer menuId;

    public SysRoleMenu(Long roleId, Integer menuId) {
        super();
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public static class __Names {
        /**
         * 当前实体对应的数据库表名
         */
        public final String TABLE_NAME = "sys_role_menu";
        /**
         * 角色主键
         */
        public final String roleId = "roleId";
        /**
         * 菜单主键
         */
        public final String menuId = "menuId";
    }

}