package com.gnol.springboot.auth.services.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.redis.spring.boot.autoconfigure.RedisService;
import com.gnol.springboot.auth.services.AuthService;
import com.gnol.springboot.auth.services.ResourceService;
import com.gnol.springboot.auth.services.TokenService;
import com.gnol.springboot.common.enums.UserTypeEnum;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

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
     * 当前 token 用户信息存储的 key 前缀
     */
    private static final String TOKEN = "token:";
    /**
     * redis 缓存服务实现
     */
    @Resource(name = "redisServiceImpl")
    private RedisService redisService;
    /**
     * jwt token 服务实现
     */
    @Resource(name = "jwtTokenServiceImpl")
    private TokenService tokenService;
    /**
     * 菜单资源实现
     */
    @Resource(name = "menuResourceServiceImpl")
    private ResourceService menuResourceServiceImpl;
    /**
     * 接口资源实现
     */
    @Resource(name = "interfaceResourceServiceImpl")
    private ResourceService interfaceResourceServiceImpl;

    @Override
    public PageResult auth(PageData pd) {
        UserTypeEnum userTypeEnum = UserTypeEnum.forKey(pd.get("user_type"));
        if (userTypeEnum == null) {
            return PageResult.error("user_type 不能为空！");
        }
        boolean isAuth = false;
        String message = null;
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("sub", userTypeEnum.getKey());
        switch (userTypeEnum) { // 在相应的位置判断用户是否符合认证条件
            case SYSTEM_USER:
            case TERRITORY_USER:
            case ORG_USER:
                // 用来存在当前用户的唯一标识，例如 sessionId、userId、openId 等
                claims.put("jti", "sessionId");

                break;
            case MEMBER_USER:

                break;
            case VISITOR_USER:

                break;
            case WEIXIN_USER:
            case WEIXIN_MINI_USER:
            case ALI_USER:
            case ALI_MINI_USER:

                break;
        }
        isAuth = true;
        if (isAuth) {
            // 将用户信息存储到缓存中
            redisService.addObject(redisService.generateKey(TOKEN, claims.get("sub"), claims.get("jti")), null);
        }
        return isAuth ? PageResult.ok(tokenService.getToken(claims)) : PageResult.error(message);
    }

    @Override
    public PageResult validate(PageData pd) {
        Claims claims = null;
        try {
            claims = tokenService.validateToken(pd);
        } catch (JwtException e) {
            e.printStackTrace();
            return PageResult.error(e.getMessage());
        }
        return PageResult.ok(claims.toString());
    }

    @Override
    public PageResult logout(PageData pd) {
        Claims claims = null;
        try {
            claims = tokenService.validateToken(pd);
        } catch (JwtException e) {
            e.printStackTrace();
            return PageResult.error(e.getMessage());
        }
        redisService.delete(redisService.generateKey(TOKEN, claims.get("sub"), claims.get("jti")));
        return PageResult.ok();
    }

}
