package top.d7c.springboot.client.controllers.sharding;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.context.IdService;
import top.d7c.springboot.client.controllers.WebBaseController;
import top.d7c.springboot.client.dos.sharding.ShardingUser;
import top.d7c.springboot.client.services.sharding.ShardingUserService;

/**
 * @Title: ShardingUserController
 * @Package: top.d7c.springboot.client.controllers.sharding
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

    /**
     * 测试新增
     */
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

    /**
     * 测试查询
     */
    @GetMapping(value = "t2")
    public PageResult t2() {
        PageData pd = this.getEmptyPageData();
        pd.put(ShardingUser.M.sex, 1);
        List<ShardingUser> shardingUsers = shardingUserService.listBy(pd);
        return PageResult.ok(shardingUsers);
    }

}