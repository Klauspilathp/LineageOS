package com.d7c.springboot.client.services.sharding.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.d7c.plugins.core.context.AbstractBaseService;
import com.d7c.springboot.client.daos.sharding.BaseShardingCityDao;
import com.d7c.springboot.client.daos.sharding.ExtShardingCityDao;
import com.d7c.springboot.client.dos.sharding.ShardingCity;
import com.d7c.springboot.client.services.sharding.ShardingCityService;

/**
 * @Title: ShardingCityServiceImpl
 * @Package: com.d7c.springboot.client.services.sharding.impl
 * @author: 吴佳隆
 * @date: 2020年08月03日 14:28:26
 * @Description: ShardingCity Service 实现
 */
@Service(value = "shardingCityServiceImpl")
public class ShardingCityServiceImpl extends AbstractBaseService<BaseShardingCityDao, ShardingCity, Long>
        implements ShardingCityService {
    /**
     * ShardingCity扩展 Dao
     */
    @Resource(name = "extShardingCityDao")
    private ExtShardingCityDao shardingCityDao;

}