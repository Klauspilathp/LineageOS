package com.gnol.springboot.client.daos.sharding;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.client.dos.sharding.ShardingCity;

/**
 * @Title: BaseShardingCityDao
 * @Package: com.gnol.springboot.client.daos.sharding
 * @author: 吴佳隆
 * @date: 2020年08月03日 14:28:26
 * @Description: ShardingCity基础 Dao
 */
@Repository(value = "baseShardingCityDao")
public interface BaseShardingCityDao extends BaseDao<ShardingCity, Long> {

}