package com.gnol.springboot.auth.services;

import java.util.Map;

import com.gnol.plugins.core.PageData;

/**
 * @Title: TokenService
 * @Package: com.gnol.springboot.auth.services
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:35:45
 * @Description: token 服务接口
 */
public interface TokenService {

    /**
     * @Title: getToken
     * @author: 吴佳隆
     * @data: 2020年6月29日 下午12:27:22
     * @Description: 获取 token
     * @param claims    载荷
     * @return String
     */
    String getToken(Map<String, Object> claims);

    /**
     * @Title: validateToken
     * @author: 吴佳隆
     * @data: 2020年6月29日 下午12:27:37
     * @Description: 验证 token 是否有效
     * @param pd
     * @return boolean
     */
    boolean validateToken(PageData pd);

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