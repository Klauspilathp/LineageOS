package com.d7c.springboot.client.dtos.tree;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: MenuTree
 * @Package: com.d7c.springboot.client.dtos.tree
 * @author: 吴佳隆
 * @date: 2019年7月1日 下午4:19:48
 * @Description: 系统菜单树
 */
public class MenuTree implements Serializable {
    private static final long serialVersionUID = -4003009335083742393L;
    /**
     * 菜单主键采用int自增，使用BigInteger做2的权和只能传入int类型
     */
    private Integer menuId;
    /**
     * 父菜单编号
     */
    private Integer parentId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 访问菜单所需权限
     */
    private String permissions;
    /**
     * 菜单URL
     */
    private String menuUrl;
    /**
     * 图标
     */
    private String icon;
    /**
     * 菜单类型
     */
    private Integer menuType;
    /**
     * 菜单功能
     */
    private Integer menuFunc;
    /**
     * 是否显示
     */
    private Integer visible;
    /**
     * 子菜单列表
     */
    private List<MenuTree> childrens;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuFunc() {
        return menuFunc;
    }

    public void setMenuFunc(Integer menuFunc) {
        this.menuFunc = menuFunc;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public List<MenuTree> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<MenuTree> childrens) {
        this.childrens = childrens;
    }

}