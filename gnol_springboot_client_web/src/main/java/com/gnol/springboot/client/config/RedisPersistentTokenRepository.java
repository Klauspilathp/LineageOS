package com.gnol.springboot.client.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;

import com.gnol.plugins.core.StringUtil;
import com.gnol.plugins.tools.date.TimeConstant;

/**
 * @Title: RedisPersistentTokenRepository
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月17日 下午5:39:01
 * @Description: redis 中持久化记住我 token 实现
 * 原理分析：org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices
 */
@Service(value = "redisPersistentTokenRepository")
public class RedisPersistentTokenRepository implements PersistentTokenRepository {
    private static final Logger logger = LoggerFactory.getLogger(RedisPersistentTokenRepository.class);
    /**
     * token 有效期，单位秒
     */
    private static Long TOKEN_EXPIRATION = TimeConstant.DAY_SECOND;
    /**
     * 使用 StringRedisSerializer 序列化数据的 redis 模板
     */
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Long getTokenExpiration() {
        return RedisPersistentTokenRepository.TOKEN_EXPIRATION;
    }

    @Value("${gnol.web.token-xpiration:86400}")
    public void setTokenExpiration(long tokenExpiration) {
        RedisPersistentTokenRepository.TOKEN_EXPIRATION = tokenExpiration;
    }

    /**
     * 存储 token 对象 org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken
     */
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        logger.debug("{} 用户选择记住密码，创建了 token，键为 {}，值为 {}", token.getUsername(), token.getSeries(),
                token.getTokenValue());
        String tokenKey = generateTokenKey(token.getSeries());
        Map<String, String> map = new HashMap<String, String>(4);
        map.put("username", token.getUsername());
        map.put("tokenValue", token.getTokenValue());
        map.put("date", String.valueOf(token.getDate().getTime()));
        // 设置 token
        stringRedisTemplate.opsForHash().putAll(tokenKey, map);
        stringRedisTemplate.expire(tokenKey, TOKEN_EXPIRATION, TimeUnit.SECONDS);
        // 根据 username 设置 token 键
        stringRedisTemplate.opsForValue().set(generateUsernameKey(token.getUsername()), token.getSeries(),
                TOKEN_EXPIRATION, TimeUnit.SECONDS);
    }

    /**
     * @param series        存放 token 的键
     * @param tokenValue    token 值
     * @param lastUsed      最后访问时间 
     */
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        String tokenKey = generateTokenKey(series);
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(tokenKey);
        if (map == null || map.isEmpty()) {
            return;
        }
        map.put("tokenValue", tokenValue);
        map.put("date", String.valueOf(lastUsed.getTime()));
        // 设置 token
        stringRedisTemplate.opsForHash().putAll(tokenKey, map);
        stringRedisTemplate.expire(tokenKey, TOKEN_EXPIRATION, TimeUnit.SECONDS);
        // 根据 username 设置 token 键
        stringRedisTemplate.opsForValue().set(generateUsernameKey(StringUtil.toString(map.get("username"))), series,
                TOKEN_EXPIRATION, TimeUnit.SECONDS);
    }

    /**
     * @param seriesId  存放 token 的键
     */
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(generateTokenKey(seriesId));
        if (map == null || map.isEmpty()) {
            return null;
        }
        return new PersistentRememberMeToken(StringUtil.toString(map.get("username")), // 用户名
                seriesId, StringUtil.toString(map.get("tokenValue")), // token 值
                new Date(StringUtil.tolong(map.get("date")))); // 最后操作时间
    }

    /**
     * @param username  用户名、账户
     */
    @Override
    public void removeUserTokens(String username) {
        logger.debug("{} 用户进行了注销操作。", username);
        String usernameKey = generateUsernameKey(username);
        String series = stringRedisTemplate.opsForValue().get(usernameKey);
        if (StringUtil.isBlank(series)) {
            return;
        }
        stringRedisTemplate.delete(generateTokenKey(series));
        stringRedisTemplate.delete(usernameKey);
    }

    /**
     * 生成 token key
     */
    private String generateTokenKey(String series) {
        return "REMEMBERME:TOKEN:" + series;
    }

    /**
     * 生成 username key
     */
    private String generateUsernameKey(String username) {
        return "REMEMBERME:USERNAME:" + username;
    }

}