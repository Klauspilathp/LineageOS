package com.gnol.springboot.common.dos.security;

import com.gnol.springboot.common.dos.BasePojo;

/**
 * @Title: SecurityKey
 * @Package: com.gnol.springboot.common.dos.security
 * @author: 吴佳隆
 * @date: 2020年07月20日 12:02:41
 * @Description: gnol 系统安全模块_用户或系统密钥 pojo
 */
public class SecurityKey extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 应用主键
     */
    private String appid;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 备注
     */
    private String remark;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
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
        public final String TABLE_NAME = "security_key";
        /**
         * 应用主键
         */
        public final String appid = "appid";
        /**
         * 私钥
         */
        public final String privateKey = "privateKey";
        /**
         * 公钥
         */
        public final String publicKey = "publicKey";
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