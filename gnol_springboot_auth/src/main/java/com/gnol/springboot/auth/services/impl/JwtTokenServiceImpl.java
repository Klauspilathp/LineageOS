package com.gnol.springboot.auth.services.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gnol.jwt.spring.boot.autoconfigure.JwtUtil;
import com.gnol.plugins.core.PageData;
import com.gnol.springboot.auth.services.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

/**
 * @Title: JwtTokenServiceImpl
 * @Package: com.gnol.springboot.auth.services.impl
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:36:19
 * @Description: jwt token 服务实现
 */
@Service("jwtTokenServiceImpl")
public class JwtTokenServiceImpl implements TokenService {
    /**
     * jwt token 工具类
     */
    @Resource(name = "jwtUtil")
    private JwtUtil jwtUtil;

    @Override
    public String getToken(Map<String, Object> claims) {
        String token = null;
        if (claims == null || claims.isEmpty()) {
            token = jwtUtil.generateToken(jwtUtil.getJwtProperties().getSubject(),
                    jwtUtil.getJwtProperties().getSecret(), jwtUtil.getJwtProperties().getExpiration());
        } else {
            Object subject = claims.remove("subject");
            token = jwtUtil.generateToken(claims,
                    subject == null ? jwtUtil.getJwtProperties().getSubject() : subject.toString(),
                    jwtUtil.getJwtProperties().getSecret(), jwtUtil.getJwtProperties().getExpiration());
        }
        return token;
    }

    @Override
    public Claims validateToken(PageData pd) {
        if (pd == null || pd.isEmpty()) {
            return null;
        }
        return jwtUtil.getClaimsByToken(pd.getString("token"), jwtUtil.getJwtProperties().getSecret());
    }

    @Override
    public String updateToken(PageData pd) {
        String token = "";
        if (pd == null || pd.isEmpty()) {
            return token;
        }
        try {
            token = jwtUtil.updateToken(pd.getString("token"), jwtUtil.getJwtProperties().getSecret(),
                    jwtUtil.getJwtProperties().getExpiration());
        } catch (JwtException e) {
            e.printStackTrace();
        }
        return token;
    }

}