package com.d7c.springboot.client.daos.sharding;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.context.BaseDao;
import com.d7c.springboot.client.dos.sharding.ShardingUser;

/**
 * @Title: BaseShardingUserDao
 * @Package: com.d7c.springboot.client.daos.sharding
 * @author: 吴佳隆
 * @date: 2020年08月03日 14:27:46
 * @Description: ShardingUser基础 Dao
 */
@Repository(value = "baseShardingUserDao")
public interface BaseShardingUserDao extends BaseDao<ShardingUser, Long> {

}