package com.gnol.springboot.common.pojos.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: SysId
 * @Package: com.gnol.springboot.common.pojos.sys
 * @author: 吴佳隆
 * @date: 2020年04月03日 12:15:02
 * @Description: gnol系统_主键操作 pojo
 */
public class SysId implements Serializable {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * id 表操作的唯一主键，字符串类型，建议设置成表名
     */
    private String idKey;
    /**
     * 起始值
     */
    private Long startValue;
    /**
     * 最后获取的值
     */
    private Long endValue;
    /**
     * 每次获取个数
     */
    private Integer perNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 当前被获取的值
     */
    private Long currValue;

    public SysId() {
        super();
    }

    public SysId(String idKey, Long startValue, Integer perNum) {
        super();
        this.idKey = idKey;
        this.startValue = startValue;
        this.endValue = startValue + perNum;
        this.perNum = perNum;
        this.remark = idKey + "的主键操作记录";
        this.addTime = new Date();
        this.currValue = startValue;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public Long getStartValue() {
        return startValue;
    }

    public void setStartValue(Long startValue) {
        this.startValue = startValue;
    }

    public Long getEndValue() {
        return endValue;
    }

    public void setEndValue(Long endValue) {
        this.endValue = endValue;
    }

    public Integer getPerNum() {
        return perNum;
    }

    public void setPerNum(Integer perNum) {
        this.perNum = perNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getCurrValue() {
        return currValue;
    }

    public void setCurrValue(Long currValue) {
        this.currValue = currValue;
    }

    @Override
    public String toString() {
        return "SysId [idKey=" + idKey + ", startValue=" + startValue + ", endValue=" + endValue + ", perNum=" + perNum
                + ", remark=" + remark + ", addTime=" + addTime + ", modifyTime=" + modifyTime + ", currValue="
                + currValue + "]";
    }

    public static class __Names {
        /**
         * 当前实体对应的数据库表名
         */
        public final String TABLE_NAME = "sys_id";
        /**
         * id 表操作的唯一主键，字符串类型，建议设置成表名
         */
        public final String idKey = "idKey";
        /**
         * 起始值
         */
        public final String startValue = "startValue";
        /**
         * 最后获取的值
         */
        public final String endValue = "endValue";
        /**
         * 每次获取个数
         */
        public final String perNum = "perNum";
        /**
         * 备注
         */
        public final String remark = "remark";
        /**
         * 添加时间
         */
        public final String addTime = "addTime";
        /**
         * 修改时间
         */
        public final String modifyTime = "modifyTime";
    }

}