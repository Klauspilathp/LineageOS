package com.gnol.springboot.client.services;

import com.gnol.plugins.core.PageData;

import io.jsonwebtoken.Claims;

/**
 * @Title: ResourceService
 * @Package: com.gnol.springboot.client.services
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:37:42
 * @Description: 资源服务接口
 */
public interface ResourceService {

    /**
     * @Title: isPermitted
     * @author: 吴佳隆
     * @data: 2020年7月21日 下午4:09:56
     * @Description: 是否有权限
     * @param pd        传入的参数
     * @param claims    token 载荷
     * @return boolean  返回 true 有权限
     */
    boolean isPermitted(PageData pd, Claims claims);

}