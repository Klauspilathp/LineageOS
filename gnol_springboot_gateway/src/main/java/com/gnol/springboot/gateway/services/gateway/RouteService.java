package com.gnol.springboot.gateway.services.gateway;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;

/**
 * @Title: RouteService
 * @Package: com.gnol.springboot.gateway.services.gateway
 * @author: 吴佳隆
 * @date: 2020年8月1日 下午1:18:23
 * @Description: 路由服务
 */
public interface RouteService {

    /**
     * @Title: delById
     * @author: 吴佳隆
     * @data: 2020年8月1日 下午2:05:48
     * @Description: 根据 id 删除路由
     * @param id
     * @return PageResult
     */
    PageResult delById(String id);

    /**
     * @Title: delAll
     * @author: 吴佳隆
     * @data: 2020年8月1日 下午2:09:22
     * @Description: 删除全部路由规则
     * @return PageResult
     */
    PageResult delAll();

    /**
     * @Title: addRoute
     * @author: 吴佳隆
     * @data: 2020年8月1日 下午1:56:21
     * @Description: 添加路由规则
     * @param pd
     * @return PageResult
     */
    PageResult addRoute(PageData pd);

    /**
     * @Title: refresh
     * @author: 吴佳隆
     * @data: 2020年8月1日 下午1:31:39
     * @Description: 刷新路由规则
     * @return PageResult
     */
    PageResult refresh();

}