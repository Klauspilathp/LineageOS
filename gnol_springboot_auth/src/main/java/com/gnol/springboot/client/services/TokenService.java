package com.gnol.springboot.client.services;

import java.util.Map;

import com.gnol.plugins.core.PageData;

import io.jsonwebtoken.Claims;

/**
 * @Title: TokenService
 * @Package: com.gnol.springboot.client.services
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:35:45
 * @Description: token 服务接口
 */
public interface TokenService {

    /**
     * @Title: getToken
     * @author: 吴佳隆
     * @data: 2020年6月29日 下午12:27:22
     * @Description: 生成 token
     * @param claims    载荷
     * @return String
     */
    String getToken(Map<String, Object> claims);

    /**
     * @Title: getClaimsByToken
     * @author: 吴佳隆
     * @data: 2020年6月29日 下午12:27:37
     * @Description: 根据 token 获取载荷
     * @param pd
     * @return Claims
     */
    Claims getClaimsByToken(PageData pd);

    /**
     * @Title: updateToken
     * @author: 吴佳隆
     * @data: 2020年6月29日 下午12:31:27
     * @Description: 刷新 token
     * @param pd
     * @return String
     */
    String updateToken(PageData pd);

}