package com.d7c.springboot.common.dos.sys;

import com.d7c.springboot.common.dos.BasePojo;
import com.d7c.springboot.common.enums.sys.MenuFuncEnum;
import com.d7c.springboot.common.enums.sys.MenuTypeEnum;
import com.d7c.springboot.common.enums.sys.VisibleEnum;

/**
 * @Title: SysMenu
 * @Package: com.d7c.springboot.common.dos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 09:13:40
 * @Description: d7c 系统菜单表 pojo
 */
public class SysMenu extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
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
     * 排序
     */
    private Integer sort;
    /**
     * 是否显示
     */
    private Integer visible;
    /**
     * 备注
     */
    private String remark;

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

    public String getMenuType_Text() {
        return MenuTypeEnum.getValue(this.menuType);
    }

    public Integer getMenuFunc() {
        return menuFunc;
    }

    public void setMenuFunc(Integer menuFunc) {
        this.menuFunc = menuFunc;
    }

    public String getMenuFunc_Text() {
        return MenuFuncEnum.getValue(this.menuFunc);
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public String getVisible_Text() {
        return VisibleEnum.getValue(this.visible);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static class __Names {
        /**
         * 当前实体对应的数据库表名
         */
        public final String TABLE_NAME = "sys_menu";
        /**
         * 菜单主键采用int自增，使用BigInteger做2的权和只能传入int类型
         */
        public final String menuId = "menuId";
        /**
         * 父菜单编号
         */
        public final String parentId = "parentId";
        /**
         * 菜单名称
         */
        public final String menuName = "menuName";
        /**
         * 访问菜单所需权限
         */
        public final String permissions = "permissions";
        /**
         * 菜单URL
         */
        public final String menuUrl = "menuUrl";
        /**
         * 图标
         */
        public final String icon = "icon";
        /**
         * 菜单类型
         */
        public final String menuType = "menuType";
        /**
         * 菜单功能
         */
        public final String menuFunc = "menuFunc";
        /**
         * 排序
         */
        public final String sort = "sort";
        /**
         * 是否显示
         */
        public final String visible = "visible";
        /**
         * 备注
         */
        public final String remark = "remark";
        /**
         * 添加时间
         */
        public final String addTime = "addTime";
        /**
         * 添加人
         */
        public final String addUser = "addUser";
        /**
         * 修改时间
         */
        public final String modifyTime = "modifyTime";
        /**
         * 修改人
         */
        public final String modifyUser = "modifyUser";
        /**
         * 数据状态，0：删除，1：正常，2：冻结
         */
        public final String status = "status";
        /**
         * 数据当前版本
         */
        public final String checkValue = "checkValue";
    }

}