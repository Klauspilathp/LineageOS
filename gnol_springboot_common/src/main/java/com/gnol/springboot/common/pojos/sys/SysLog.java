package com.gnol.springboot.common.pojos.sys;

import com.gnol.springboot.common.pojos.BasePojo;

/**
 * @Title: SysLog
 * @Package: com.gnol.springboot.common.pojos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 09:13:40
 * @Description: gnol 系统日志 pojo
 */
public class SysLog extends BasePojo implements Cloneable {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 主键
     */
    private Long logId;
    /**
     * 请求路径
     */
    private String requestUri;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 异常信息
     */
    private String exceptionInfo;
    /**
     * 请求方地址
     */
    private String remoteAddr;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 设备/操作系统名称
     */
    private String deviceName;
    /**
     * 浏览器名称
     */
    private String browserName;
    /**
     * 日志类型
     */
    private Integer logType;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    @Override
    public Object clone() {
        SysLog sysLog = null;
        try {
            sysLog = (SysLog) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return sysLog;
    }

    public static class __Names {
        /**
         * 当前实体对应的数据库表名
         */
        public final String TABLE_NAME = "sys_log";
        /**
         * 主键
         */
        public final String logId = "logId";
        /**
         * 请求路径
         */
        public final String requestUri = "requestUri";
        /**
         * 请求方式
         */
        public final String requestMethod = "requestMethod";
        /**
         * 请求参数
         */
        public final String requestParams = "requestParams";
        /**
         * 异常信息
         */
        public final String exceptionInfo = "exceptionInfo";
        /**
         * 请求方地址
         */
        public final String remoteAddr = "remoteAddr";
        /**
         * 用户代理
         */
        public final String userAgent = "userAgent";
        /**
         * 设备/操作系统名称
         */
        public final String deviceName = "deviceName";
        /**
         * 浏览器名称
         */
        public final String browserName = "browserName";
        /**
         * 日志类型
         */
        public final String logType = "logType";
        /**
         * 添加时间
         */
        public final String addTime = "addTime";
        /**
         * 添加人
         */
        public final String addUser = "addUser";
    }

}