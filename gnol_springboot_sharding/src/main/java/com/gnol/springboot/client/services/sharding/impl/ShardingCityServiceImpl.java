package com.gnol.springboot.client.services.sharding.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.context.AbstractBaseService;
import com.gnol.springboot.client.daos.sharding.BaseShardingCityDao;
import com.gnol.springboot.client.daos.sharding.ExtShardingCityDao;
import com.gnol.springboot.client.dos.sharding.ShardingCity;
import com.gnol.springboot.client.services.sharding.ShardingCityService;

/**
 * @Title: ShardingCityServiceImpl
 * @Package: com.gnol.springboot.client.services.sharding.impl
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