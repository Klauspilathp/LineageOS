package com.gnol.springboot.client.dos.sharding;

import com.gnol.springboot.common.dos.BasePojo;

/**
 * @Title: ShardingCity
 * @Package: com.gnol.springboot.client.dos.sharding
 * @author: 吴佳隆
 * @date: 2020年08月03日 14:28:26
 * @Description: ShardingCity pojo
 */
public class ShardingCity extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    private Long cityId;
    private String cityName;

    public ShardingCity(Long cityId, String cityName) {
        super();
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public static class __Names {
        /**
         * 当前实体对应的数据库表名
         */
        public final String TABLE_NAME = "sharding_city";
        public final String cityId = "cityId";
        public final String cityName = "cityName";
    }

}