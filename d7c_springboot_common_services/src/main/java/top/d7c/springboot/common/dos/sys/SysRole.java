package top.d7c.springboot.common.dos.sys;

import top.d7c.springboot.common.dos.BasePojo;

/**
 * @Title: SysRole
 * @Package: top.d7c.springboot.common.dos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 09:13:40
 * @Description: d7c 系统角色表 pojo
 */
public class SysRole extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 主键
     */
    private Long roleId;
    /**
     * 父角色编号
     */
    private Long parentId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 菜单权限
     */
    private String menuQx;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMenuQx() {
        return menuQx;
    }

    public void setMenuQx(String menuQx) {
        this.menuQx = menuQx;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
        public final String TABLE_NAME = "sys_role";
        /**
         * 主键
         */
        public final String roleId = "roleId";
        /**
         * 父角色编号
         */
        public final String parentId = "parentId";
        /**
         * 角色名
         */
        public final String roleName = "roleName";
        /**
         * 菜单权限
         */
        public final String menuQx = "menuQx";
        /**
         * 排序
         */
        public final String sort = "sort";
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