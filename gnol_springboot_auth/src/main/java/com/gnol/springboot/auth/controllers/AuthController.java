package com.gnol.springboot.auth.controllers;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.auth.services.AuthService;
import com.gnol.springboot.common.mappings.auth.AuthMapping;

/**
 * @Title: AuthController
 * @Package: com.gnol.springboot.auth.controllers
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:49:43
 * @Description: 认证控制器
 */
@RestController
public class AuthController implements AuthMapping {
    /**
     * 授权服务实现
     */
    @Resource(name = "authServiceImpl")
    private AuthService authService;

    /**
     * 通过网关发送 POST 请求 http://127.0.0.1:8080/auth/login，传入 user_type 参数
     */
    @Override
    public PageResult login(PageData pd) {
        return authService.auth(pd);
    }

    @Override
    public PageResult validate(PageData pd) {
        return authService.validate(pd);
    }

    @Override
    public PageResult logout(PageData pd) {
        return authService.logout(pd);
    }

}