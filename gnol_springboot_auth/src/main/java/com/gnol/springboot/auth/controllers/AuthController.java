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