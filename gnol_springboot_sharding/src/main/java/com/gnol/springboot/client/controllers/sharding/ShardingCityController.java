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
import com.gnol.springboot.client.dos.sharding.ShardingCity;
import com.gnol.springboot.client.services.sharding.ShardingCityService;

/**
 * @Title: ShardingCityController
 * @Package: com.gnol.springboot.client.controllers.sharding
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