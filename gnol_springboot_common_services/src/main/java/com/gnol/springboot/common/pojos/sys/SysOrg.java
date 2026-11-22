package com.gnol.springboot.common.pojos.sys;

import com.gnol.springboot.common.enums.sys.LevelEnum;
import com.gnol.springboot.common.enums.sys.OrgTypeEnum;
import com.gnol.springboot.common.pojos.BasePojo;

/**
 * @Title: SysOrg
 * @Package: com.gnol.springboot.common.pojos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 09:13:40
 * @Description: gnol 系统组织机构 pojo
 */
public class SysOrg extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 主键
     */
    private Long orgId;
    /**
     * 组织机构中文名称
     */
    private String orgNameCN;
    /**
     * 组织机构英文名称
     */
    private String orgNameEN;
    /**
     * 组织机构别名
     */
    private String orgAlias;
    /**
     * 组织机构类型
     */
    private Integer orgType;
    /**
     * 组织机构所在地
     */
    private String orgAddress;
    /**
     * 组织机构代码
     */
    private String orgCode;
    /**
     * 组织机构级别
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 负责人
     */
    private String principal;
    /**
     * 联系地址
     */
    private String contactAddress;
    /**
     * 邮政编码
     */
    private String postalcode;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 联系QQ
     */
    private String qq;
    /**
     * 联系微信
     */
    private String weixin;
    /**
     * 联系邮箱
     */
    private String email;
    /**
     * 父组织机构编号
     */
    private Long parentId;
    /**
     * 所在集团总部编号
     */
    private Long blocHQId;
    /**
     * 所在集团总部中文名称
     */
    private String blocHQNameCN;
    /**
     * 所在区域总部编号
     */
    private Long areaHQId;
    /**
     * 所在区域总部中文名称
     */
    private String areaHQNameCN;
    /**
     * 所在分公司编号
     */
    private Long companyId;
    /**
     * 所在分公司中文名称
     */
    private String companyNameCN;
    /**
     * 备注
     */
    private String remark;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgNameCN() {
        return orgNameCN;
    }

    public void setOrgNameCN(String orgNameCN) {
        this.orgNameCN = orgNameCN;
    }

    public String getOrgNameEN() {
        return orgNameEN;
    }

    public void setOrgNameEN(String orgNameEN) {
        this.orgNameEN = orgNameEN;
    }

    public String getOrgAlias() {
        return orgAlias;
    }

    public void setOrgAlias(String orgAlias) {
        this.orgAlias = orgAlias;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public void setOrgType_Enum(OrgTypeEnum orgType) {
        this.orgType = orgType.getKey();
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setLevel_Enum(LevelEnum level) {
        this.level = level.getKey();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getBlocHQId() {
        return blocHQId;
    }

    public void setBlocHQId(Long blocHQId) {
        this.blocHQId = blocHQId;
    }

    public String getBlocHQNameCN() {
        return blocHQNameCN;
    }

    public void setBlocHQNameCN(String blocHQNameCN) {
        this.blocHQNameCN = blocHQNameCN;
    }

    public Long getAreaHQId() {
        return areaHQId;
    }

    public void setAreaHQId(Long areaHQId) {
        this.areaHQId = areaHQId;
    }

    public String getAreaHQNameCN() {
        return areaHQNameCN;
    }

    public void setAreaHQNameCN(String areaHQNameCN) {
        this.areaHQNameCN = areaHQNameCN;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyNameCN() {
        return companyNameCN;
    }

    public void setCompanyNameCN(String companyNameCN) {
        this.companyNameCN = companyNameCN;
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
        public final String TABLE_NAME = "sys_org";
        /**
         * 主键
         */
        public final String orgId = "orgId";
        /**
         * 组织机构中文名称
         */
        public final String orgNameCN = "orgNameCN";
        /**
         * 组织机构英文名称
         */
        public final String orgNameEN = "orgNameEN";
        /**
         * 组织机构别名
         */
        public final String orgAlias = "orgAlias";
        /**
         * 组织机构类型
         */
        public final String orgType = "orgType";
        /**
         * 组织机构所在地
         */
        public final String orgAddress = "orgAddress";
        /**
         * 组织机构代码
         */
        public final String orgCode = "orgCode";
        /**
         * 组织机构级别
         */
        public final String level = "level";
        /**
         * 排序
         */
        public final String sort = "sort";
        /**
         * 负责人
         */
        public final String principal = "principal";
        /**
         * 联系地址
         */
        public final String contactAddress = "contactAddress";
        /**
         * 邮政编码
         */
        public final String postalcode = "postalcode";
        /**
         * 联系电话
         */
        public final String phone = "phone";
        /**
         * 联系QQ
         */
        public final String qq = "qq";
        /**
         * 联系微信
         */
        public final String weixin = "weixin";
        /**
         * 联系邮箱
         */
        public final String email = "email";
        /**
         * 父组织机构编号
         */
        public final String parentId = "parentId";
        /**
         * 所在集团总部编号
         */
        public final String blocHQId = "blocHQId";
        /**
         * 所在集团总部中文名称
         */
        public final String blocHQNameCN = "blocHQNameCN";
        /**
         * 所在区域总部编号
         */
        public final String areaHQId = "areaHQId";
        /**
         * 所在区域总部中文名称
         */
        public final String areaHQNameCN = "areaHQNameCN";
        /**
         * 所在分公司编号
         */
        public final String companyId = "companyId";
        /**
         * 所在分公司中文名称
         */
        public final String companyNameCN = "companyNameCN";
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