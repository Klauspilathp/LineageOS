package com.gnol.springboot.client.daos.sharding;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.client.dos.sharding.ShardingUser;

/**
 * @Title: BaseShardingUserDao
 * @Package: com.gnol.springboot.client.daos.sharding
 * @author: 吴佳隆
 * @date: 2020年08月03日 14:27:46
 * @Description: ShardingUser基础 Dao
 */
@Repository(value = "baseShardingUserDao")
public interface BaseShardingUserDao extends BaseDao<ShardingUser, Long> {

}