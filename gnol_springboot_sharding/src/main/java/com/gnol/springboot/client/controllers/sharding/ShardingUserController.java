package com.gnol.springboot.client.controllers.sharding;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.IdService;
import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.dos.sharding.ShardingUser;
import com.gnol.springboot.client.services.sharding.ShardingUserService;

/**
 * @Title: ShardingUserController
 * @Package: com.gnol.springboot.client.controllers.sharding
 * @author: 吴佳隆
 * @date: 2020年08月03日 14:27:46
 * @Description: ShardingUser Controller
 */
@RestController
@RequestMapping(value = "/sharding/user")
public class ShardingUserController extends WebBaseController {
    /**
     * ShardingUser Service 实现
     */
    @Resource(name = "shardingUserServiceImpl")
    private ShardingUserService shardingUserService;
    /**
     * ID 生成服务
     */
    @Resource(name = "cacheIdServiceImpl")
    private IdService idService;

    @GetMapping(value = "t1")
    public PageResult t1() {
        List<ShardingUser> shardingUsers = new ArrayList<ShardingUser>();
        for (int i = 0; i < 100; i++) {
            long userId = idService.getLong(ShardingUser.M.TABLE_NAME);
            shardingUsers.add(new ShardingUser(userId, "user" + userId, i % 2));
        }
        shardingUserService.insertBatch(shardingUsers);
        return PageResult.ok(shardingUsers);
    }

}