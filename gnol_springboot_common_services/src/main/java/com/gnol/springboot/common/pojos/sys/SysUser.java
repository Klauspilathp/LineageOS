package com.gnol.springboot.common.pojos.sys;

import java.math.BigDecimal;
import java.util.Date;

import com.gnol.springboot.common.pojos.BasePojo;

/**
 * @Title: SysUser
 * @Package: com.gnol.springboot.common.pojos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 09:13:40
 * @Description: gnol 系统_用户表 pojo
 */
public class SysUser extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 主键
     */
    private Long userId;
    /**
     * 角色编号
     */
    private Long roleId;
    /**
     * 工号
     */
    private String jobNumber;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码最后修改时间
     */
    private Date passwordModifyTime;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 微信号
     */
    private String wxOpenid;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 经度
     */
    private BigDecimal lng;
    /**
     * 纬度
     */
    private BigDecimal lat;
    /**
     * 用户类别
     */
    private Integer userType;
    /**
     * 组织机构编号
     */
    private Long orgId;
    /**
     * 是否管理员
     */
    private Integer administrator;
    /**
     * 登录状态
     */
    private Integer loginStatus;
    /**
     * 最后登录时间
     */
    private Date loginTime;
    /**
     * 最后登录地址
     */
    private String loginIp;
    /**
     * 账号冻结时间
     */
    private Date freezeTime;
    /**
     * 账号冻结原因
     */
    private String freezeCause;
    /**
     * 密保问题1
     */
    private String securityQuestion1;
    /**
     * 密保问题答案1
     */
    private String securityQuestionAnswer1;
    /**
     * 密保问题2
     */
    private String securityQuestion2;
    /**
     * 密保问题答案2
     */
    private String securityQuestionAnswer2;
    /**
     * 密保问题3
     */
    private String securityQuestion3;
    /**
     * 密保问题答案3
     */
    private String securityQuestionAnswer3;
    /**
     * 密保修改时间
     */
    private Date securityModifyTime;
    /**
     * 备注
     */
    private String remark;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPasswordModifyTime() {
        return passwordModifyTime;
    }

    public void setPasswordModifyTime(Date passwordModifyTime) {
        this.passwordModifyTime = passwordModifyTime;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Integer administrator) {
        this.administrator = administrator;
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

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(Date freezeTime) {
        this.freezeTime = freezeTime;
    }

    public String getFreezeCause() {
        return freezeCause;
    }

    public void setFreezeCause(String freezeCause) {
        this.freezeCause = freezeCause;
    }

    public String getSecurityQuestion1() {
        return securityQuestion1;
    }

    public void setSecurityQuestion1(String securityQuestion1) {
        this.securityQuestion1 = securityQuestion1;
    }

    public String getSecurityQuestionAnswer1() {
        return securityQuestionAnswer1;
    }

    public void setSecurityQuestionAnswer1(String securityQuestionAnswer1) {
        this.securityQuestionAnswer1 = securityQuestionAnswer1;
    }

    public String getSecurityQuestion2() {
        return securityQuestion2;
    }

    public void setSecurityQuestion2(String securityQuestion2) {
        this.securityQuestion2 = securityQuestion2;
    }

    public String getSecurityQuestionAnswer2() {
        return securityQuestionAnswer2;
    }

    public void setSecurityQuestionAnswer2(String securityQuestionAnswer2) {
        this.securityQuestionAnswer2 = securityQuestionAnswer2;
    }

    public String getSecurityQuestion3() {
        return securityQuestion3;
    }

    public void setSecurityQuestion3(String securityQuestion3) {
        this.securityQuestion3 = securityQuestion3;
    }

    public String getSecurityQuestionAnswer3() {
        return securityQuestionAnswer3;
    }

    public void setSecurityQuestionAnswer3(String securityQuestionAnswer3) {
        this.securityQuestionAnswer3 = securityQuestionAnswer3;
    }

    public Date getSecurityModifyTime() {
        return securityModifyTime;
    }

    public void setSecurityModifyTime(Date securityModifyTime) {
        this.securityModifyTime = securityModifyTime;
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
        public final String TABLE_NAME = "sys_user";
        /**
         * 主键
         */
        public final String userId = "userId";
        /**
         * 角色编号
         */
        public final String roleId = "roleId";
        /**
         * 工号
         */
        public final String jobNumber = "jobNumber";
        /**
         * 用户账号
         */
        public final String userAccount = "userAccount";
        /**
         * 密码
         */
        public final String password = "password";
        /**
         * 密码最后修改时间
         */
        public final String passwordModifyTime = "passwordModifyTime";
        /**
         * 昵称
         */
        public final String nickname = "nickname";
        /**
         * 头像
         */
        public final String avatar = "avatar";
        /**
         * 生日
         */
        public final String birthday = "birthday";
        /**
         * 年龄
         */
        public final String age = "age";
        /**
         * 性别
         */
        public final String sex = "sex";
        /**
         * 手机号
         */
        public final String phone = "phone";
        /**
         * 微信号
         */
        public final String wxOpenid = "wxOpenid";
        /**
         * 邮箱
         */
        public final String email = "email";
        /**
         * 个性签名
         */
        public final String signature = "signature";
        /**
         * 经度
         */
        public final String lng = "lng";
        /**
         * 纬度
         */
        public final String lat = "lat";
        /**
         * 用户类别
         */
        public final String userType = "userType";
        /**
         * 组织机构编号
         */
        public final String orgId = "orgId";
        /**
         * 是否管理员
         */
        public final String administrator = "administrator";
        /**
         * 登录状态
         */
        public final String loginStatus = "loginStatus";
        /**
         * 最后登录时间
         */
        public final String loginTime = "loginTime";
        /**
         * 最后登录地址
         */
        public final String loginIp = "loginIp";
        /**
         * 账号冻结时间
         */
        public final String freezeTime = "freezeTime";
        /**
         * 账号冻结原因
         */
        public final String freezeCause = "freezeCause";
        /**
         * 密保问题1
         */
        public final String securityQuestion1 = "securityQuestion1";
        /**
         * 密保问题答案1
         */
        public final String securityQuestionAnswer1 = "securityQuestionAnswer1";
        /**
         * 密保问题2
         */
        public final String securityQuestion2 = "securityQuestion2";
        /**
         * 密保问题答案2
         */
        public final String securityQuestionAnswer2 = "securityQuestionAnswer2";
        /**
         * 密保问题3
         */
        public final String securityQuestion3 = "securityQuestion3";
        /**
         * 密保问题答案3
         */
        public final String securityQuestionAnswer3 = "securityQuestionAnswer3";
        /**
         * 密保修改时间
         */
        public final String securityModifyTime = "securityModifyTime";
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