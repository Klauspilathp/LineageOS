package com.gnol.springboot.common.pojos.sys;

import java.util.Date;

import com.gnol.plugins.tools.date.TimeConstant;
import com.gnol.springboot.common.enums.sys.SourceEnum;
import com.gnol.springboot.common.pojos.GnolSession;

/**
 * @Title: SysSession
 * @Package: com.gnol.springboot.common.pojos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 09:13:40
 * @Description: gnol 系统_用户详情权限临时表，用户在登录，下线等情况下触发插入、删除、更新操作，然后以此表关联业务表查询指定范围内的 pojo
 */
public class SysSession extends GnolSession {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 主键
     */
    private Long userId;
    /**
     * 工号
     */
    private String jobNumber;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 角色编号
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 菜单权限
     */
    private String menuQx;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 是否管理员
     */
    private Integer administrator;
    /**
     * 组织机构编号
     */
    private Long orgId;
    /**
     * 所在集团总部编号
     */
    private Long blocHQId;
    /**
     * 所在集团总部名称
     */
    private String blocHQName;
    /**
     * 所在区域总部编号
     */
    private Long areaHQId;
    /**
     * 所在区域总部名称
     */
    private String areaHQName;
    /**
     * 所在分公司编号
     */
    private Long companyId;
    /**
     * 所在分公司名称
     */
    private String companyName;
    /**
     * 所在部门编号
     */
    private Long departmentId;
    /**
     * 所在部门名称
     */
    private String departmentName;
    /**
     * 登录状态
     */
    private Integer loginStatus;
    /**
     * 最后登录时间
     */
    private Date loginTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

    public SysSession() {
        super();
    }

    public SysSession(String sessionId, long userId, String nickname, int userType, String source, long timeout) {
        super(sessionId, userId, nickname, userType, source, timeout);
        this.userId = userId;
        this.nickname = nickname;
    }

    public SysSession(String sessionId, SysUser user) {
        this(sessionId, SourceEnum.WEB.getKey(), TimeConstant.HOUR_MILLIS, user);
    }

    public SysSession(String sessionId, String source, long timeout, SysUser user) {
        this(sessionId, user.getUserId(), user.getNickname(), user.getUserType(), source, timeout);
        this.userId = user.getUserId();
        this.jobNumber = user.getJobNumber();
        this.userAccount = user.getUserAccount();
        this.roleId = user.getRoleId();
        // this.roleName
        this.nickname = user.getNickname();
        this.sex = user.getSex();
        this.birthday = user.getBirthday();
        this.phone = user.getPhone();
        this.administrator = user.getAdministrator();
        this.orgId = user.getOrgId();
        // 组织机构信息
        // this.loginStatus
        // this.loginTime
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Integer administrator) {
        this.administrator = administrator;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getBlocHQId() {
        return blocHQId;
    }

    public void setBlocHQId(Long blocHQId) {
        this.blocHQId = blocHQId;
    }

    public String getBlocHQName() {
        return blocHQName;
    }

    public void setBlocHQName(String blocHQName) {
        this.blocHQName = blocHQName;
    }

    public Long getAreaHQId() {
        return areaHQId;
    }

    public void setAreaHQId(Long areaHQId) {
        this.areaHQId = areaHQId;
    }

    public String getAreaHQName() {
        return areaHQName;
    }

    public void setAreaHQName(String areaHQName) {
        this.areaHQName = areaHQName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public static class __Names {
        /**
         * 当前实体对应的数据库表名
         */
        public final String TABLE_NAME = "sys_session";
        /**
         * 用户主键
         */
        public final String userId = "userId";
        /**
         * 会话编号
         */
        public final String sessionId = "sessionId";
        /**
         * 工号
         */
        public final String jobNumber = "jobNumber";
        /**
         * 用户账号
         */
        public final String userAccount = "userAccount";
        /**
         * 角色编号
         */
        public final String roleId = "roleId";
        /**
         * 角色名
         */
        public final String roleName = "roleName";
        /**
         * 菜单权限
         */
        public final String menuQx = "menuQx";
        /**
         * 昵称
         */
        public final String nickname = "nickname";
        /**
         * 头像
         */
        public final String avatar = "avatar";
        /**
         * 性别
         */
        public final String sex = "sex";
        /**
         * 生日
         */
        public final String birthday = "birthday";
        /**
         * 手机号
         */
        public final String phone = "phone";
        /**
         * 用户类别
         */
        public final String userType = "userType";
        /**
         * 是否管理员
         */
        public final String administrator = "administrator";
        /**
         * 组织机构编号
         */
        public final String orgId = "orgId";
        /**
         * 所在集团总部编号
         */
        public final String blocHQId = "blocHQId";
        /**
         * 所在集团总部名称
         */
        public final String blocHQName = "blocHQName";
        /**
         * 所在区域总部编号
         */
        public final String areaHQId = "areaHQId";
        /**
         * 所在区域总部名称
         */
        public final String areaHQName = "areaHQName";
        /**
         * 所在分公司编号
         */
        public final String companyId = "companyId";
        /**
         * 所在分公司名称
         */
        public final String companyName = "companyName";
        /**
         * 所在部门编号
         */
        public final String departmentId = "departmentId";
        /**
         * 所在部门名称
         */
        public final String departmentName = "departmentName";
        /**
         * 登录状态
         */
        public final String loginStatus = "loginStatus";
        /**
         * 最后登录时间
         */
        public final String loginTime = "loginTime";
        /**
         * 修改时间
         */
        public final String modifyTime = "modifyTime";
    }

}