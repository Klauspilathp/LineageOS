package top.d7c.springboot.client.controllers.sharding;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.context.IdService;
import top.d7c.springboot.client.controllers.WebBaseController;
import top.d7c.springboot.client.dos.sharding.ShardingCity;
import top.d7c.springboot.client.services.sharding.ShardingCityService;

/**
 * @Title: ShardingCityController
 * @Package: top.d7c.springboot.client.controllers.sharding
 * @author: 吴佳隆
 * @date: 2020年08月03日 14:28:26
 * @Description: ShardingCity Controller
 */
@RestController
@RequestMapping(value = "/sharding/city")
public class ShardingCityController extends WebBaseController {
    /**
     * ShardingCity Service 实现
     */
    @Resource(name = "shardingCityServiceImpl")
    private ShardingCityService shardingCityService;
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
        List<ShardingCity> shardingCities = new ArrayList<ShardingCity>();
        for (int i = 0; i < 100; i++) {
            long cityId = idService.getLong(ShardingCity.M.TABLE_NAME);
            shardingCities.add(new ShardingCity(cityId, "city" + cityId));
        }
        shardingCityService.insertBatch(shardingCities);
        return PageResult.ok(shardingCities);
    }

}