package com.gnol.springboot.common.pojos.sys;

import com.gnol.springboot.common.enums.sys.LevelEnum;
import com.gnol.springboot.common.pojos.BasePojo;

/**
 * @Title: SysTerritory
 * @Package: com.gnol.springboot.common.pojos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 09:13:40
 * @Description: gnol 系统地域 pojo
 */
public class SysTerritory extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 主键
     */
    private Long territoryId;
    /**
     * 地域中文名称
     */
    private String territoryNameCN;
    /**
     * 地域英文名称
     */
    private String territoryNameEN;
    /**
     * 地域别名
     */
    private String territoryAlias;
    /**
     * 地域代码
     */
    private String territoryCode;
    /**
     * 地域级别
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 父地域编号
     */
    private Long parentId;
    /**
     * 所在国家编号
     */
    private Long stateId;
    /**
     * 所在国家中文名称
     */
    private String stateNameCN;
    /**
     * 所在省/州编号
     */
    private Long provinceId;
    /**
     * 所在省/州中文名称
     */
    private String provinceNameCN;
    /**
     * 所在城市编号
     */
    private Long cityId;
    /**
     * 所在城市中文名称
     */
    private String cityNameCN;
    /**
     * 所在区县编号
     */
    private Long countyId;
    /**
     * 所在区县中文名称
     */
    private String countyNameCN;
    /**
     * 所在街道编号
     */
    private Long streetId;
    /**
     * 所在街道中文名称
     */
    private String streetNameCN;
    /**
     * 关联业务主键（扩展字段）
     */
    private Long busId;
    /**
     * 备注
     */
    private String remark;

    public Long getTerritoryId() {
        return territoryId;
    }

    public void setTerritoryId(Long territoryId) {
        this.territoryId = territoryId;
    }

    public String getTerritoryNameCN() {
        return territoryNameCN;
    }

    public void setTerritoryNameCN(String territoryNameCN) {
        this.territoryNameCN = territoryNameCN;
    }

    public String getTerritoryNameEN() {
        return territoryNameEN;
    }

    public void setTerritoryNameEN(String territoryNameEN) {
        this.territoryNameEN = territoryNameEN;
    }

    public String getTerritoryAlias() {
        return territoryAlias;
    }

    public void setTerritoryAlias(String territoryAlias) {
        this.territoryAlias = territoryAlias;
    }

    public String getTerritoryCode() {
        return territoryCode;
    }

    public void setTerritoryCode(String territoryCode) {
        this.territoryCode = territoryCode;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateNameCN() {
        return stateNameCN;
    }

    public void setStateNameCN(String stateNameCN) {
        this.stateNameCN = stateNameCN;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceNameCN() {
        return provinceNameCN;
    }

    public void setProvinceNameCN(String provinceNameCN) {
        this.provinceNameCN = provinceNameCN;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityNameCN() {
        return cityNameCN;
    }

    public void setCityNameCN(String cityNameCN) {
        this.cityNameCN = cityNameCN;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public String getCountyNameCN() {
        return countyNameCN;
    }

    public void setCountyNameCN(String countyNameCN) {
        this.countyNameCN = countyNameCN;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public String getStreetNameCN() {
        return streetNameCN;
    }

    public void setStreetNameCN(String streetNameCN) {
        this.streetNameCN = streetNameCN;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
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
        public final String TABLE_NAME = "sys_territory";
        /**
         * 主键
         */
        public final String territoryId = "territoryId";
        /**
         * 地域中文名称
         */
        public final String territoryNameCN = "territoryNameCN";
        /**
         * 地域英文名称
         */
        public final String territoryNameEN = "territoryNameEN";
        /**
         * 地域别名
         */
        public final String territoryAlias = "territoryAlias";
        /**
         * 地域代码
         */
        public final String territoryCode = "territoryCode";
        /**
         * 地域级别
         */
        public final String level = "level";
        /**
         * 排序
         */
        public final String sort = "sort";
        /**
         * 父地域编号
         */
        public final String parentId = "parentId";
        /**
         * 所在国家编号
         */
        public final String stateId = "stateId";
        /**
         * 所在国家中文名称
         */
        public final String stateNameCN = "stateNameCN";
        /**
         * 所在省/州编号
         */
        public final String provinceId = "provinceId";
        /**
         * 所在省/州中文名称
         */
        public final String provinceNameCN = "provinceNameCN";
        /**
         * 所在城市编号
         */
        public final String cityId = "cityId";
        /**
         * 所在城市中文名称
         */
        public final String cityNameCN = "cityNameCN";
        /**
         * 所在区县编号
         */
        public final String countyId = "countyId";
        /**
         * 所在区县中文名称
         */
        public final String countyNameCN = "countyNameCN";
        /**
         * 所在街道编号
         */
        public final String streetId = "streetId";
        /**
         * 所在街道中文名称
         */
        public final String streetNameCN = "streetNameCN";
        /**
         * 关联业务主键（扩展字段）
         */
        public final String busId = "busId";
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