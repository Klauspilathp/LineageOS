package com.gnol.springboot.common.pojos.sys;

import com.gnol.springboot.common.pojos.BasePojo;

/**
 * @Title: SysDict
 * @Package: com.gnol.springboot.common.pojos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 09:13:40
 * @Description: gnol 系统字典 pojo
 */
public class SysDict extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 主键
     */
    private Long dictId;
    /**
     * 父字典编号
     */
    private Long parentId;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 字典key
     */
    private String dictKey;
    /**
     * 字典中文value
     */
    private String dictCNValue;
    /**
     * 字典英文value
     */
    private String dictENValue;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictCNValue() {
        return dictCNValue;
    }

    public void setDictCNValue(String dictCNValue) {
        this.dictCNValue = dictCNValue;
    }

    public String getDictENValue() {
        return dictENValue;
    }

    public void setDictENValue(String dictENValue) {
        this.dictENValue = dictENValue;
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
        public final String TABLE_NAME = "sys_dict";
        /**
         * 主键
         */
        public static final String dictId = "dictId";
        /**
         * 父字典编号
         */
        public static final String parentId = "parentId";
        /**
         * 字典类型
         */
        public static final String dictType = "dictType";
        /**
         * 字典key
         */
        public static final String dictKey = "dictKey";
        /**
         * 字典中文value
         */
        public static final String dictCNValue = "dictCNValue";
        /**
         * 字典英文value
         */
        public static final String dictENValue = "dictENValue";
        /**
         * 排序
         */
        public static final String sort = "sort";
        /**
         * 备注
         */
        public static final String remark = "remark";
        /**
         * 添加时间
         */
        public static final String addTime = "addTime";
        /**
         * 添加人
         */
        public static final String addUser = "addUser";
        /**
         * 修改时间
         */
        public static final String modifyTime = "modifyTime";
        /**
         * 修改人
         */
        public static final String modifyUser = "modifyUser";
        /**
         * 数据状态，0：删除，1：正常，2：冻结
         */
        public static final String status = "status";
        /**
         * 数据当前版本
         */
        public static final String checkValue = "checkValue";
    }

}