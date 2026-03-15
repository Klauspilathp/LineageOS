package com.gnol.springboot.auth.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.redis.spring.boot.autoconfigure.RedisService;
import com.gnol.springboot.auth.services.AuthService;

/**
 * @Title: AuthServiceImpl
 * @Package: com.gnol.springboot.auth.services.impl
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:34:59
 * @Description: 授权服务实现
 */
@Service("authServiceImpl")
public class AuthServiceImpl implements AuthService {
    /**
     * redis 缓存服务实现
     */
    @Resource(name = "redisServiceImpl")
    private RedisService redisService;

    @Override
    public PageResult auth(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult validate(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult logout(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

}
