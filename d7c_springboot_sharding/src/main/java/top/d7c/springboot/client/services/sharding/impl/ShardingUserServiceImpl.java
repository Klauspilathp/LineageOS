package top.d7c.springboot.client.services.sharding.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import top.d7c.plugins.core.context.AbstractBaseService;
import top.d7c.springboot.client.daos.sharding.BaseShardingUserDao;
import top.d7c.springboot.client.daos.sharding.ExtShardingUserDao;
import top.d7c.springboot.client.dos.sharding.ShardingUser;
import top.d7c.springboot.client.services.sharding.ShardingUserService;

/**
 * @Title: ShardingUserServiceImpl
 * @Package: top.d7c.springboot.client.services.sharding.impl
 * @author: 吴佳隆
 * @date: 2020年08月03日 14:27:46
 * @Description: ShardingUser Service 实现
 */
@Service(value = "shardingUserServiceImpl")
public class ShardingUserServiceImpl extends AbstractBaseService<BaseShardingUserDao, ShardingUser, Long>
        implements ShardingUserService {
    /**
     * ShardingUser扩展 Dao
     */
    @Resource(name = "extShardingUserDao")
    private ExtShardingUserDao shardingUserDao;

}