package com.gnol.springboot.gateway.services.gateway.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.gateway.services.gateway.RouteService;

/**
 * @Title: RouteServiceImpl
 * @Package: com.gnol.springboot.gateway.services.gateway.impl
 * @author: 吴佳隆
 * @date: 2020年8月1日 下午1:19:00
 * @Description: 路由服务实现
 */
@Service(value = "routeServiceImpl")
public class RouteServiceImpl implements RouteService {
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public PageResult delById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult delAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult addRoute(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult refresh() {
        // TODO Auto-generated method stub
        return null;
    }

}