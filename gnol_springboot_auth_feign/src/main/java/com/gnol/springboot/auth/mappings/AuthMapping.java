package com.gnol.springboot.auth.mappings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;

/**
 * @Title: AuthMapping
 * @Package: com.gnol.springboot.auth.mappings
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午12:58:15
 * @Description: 认证服务接口
 */
public interface AuthMapping {

    /**
     * @Title: login
     * @author: 吴佳隆
     * @data: 2020年6月28日 下午4:05:17
     * @Description: 获取授权
     * @param pd
     * @return PageResult
     */
    @PostMapping(value = "/login")
    PageResult login(@RequestBody PageData pd);

    /**
     * @Title: validate
     * @author: 吴佳隆
     * @data: 2020年6月30日 下午12:08:10
     * @Description: 验证权限
     * @param token         token 字符串
     * @param servletPath   请求 uri
     * @return PageResult
     */
    @GetMapping(value = "/validate")
    PageResult validate(@RequestParam("token") String token, @RequestParam("servletPath") String servletPath);

    /**
     * @Title: logout
     * @author: 吴佳隆
     * @data: 2020年6月28日 下午4:05:42
     * @Description: 注销授权
     * @param pd
     * @return PageResult
     */
    @PostMapping(value = "/logout")
    PageResult logout(@RequestBody PageData pd);

}