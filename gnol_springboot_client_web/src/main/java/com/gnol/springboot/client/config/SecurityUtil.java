package com.gnol.springboot.client.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Title: SecurityUtil
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月18日 上午8:48:26
 * @Description: Security 工具类
 */
public class SecurityUtil extends SecurityContextHolder {

    /**
     * 获取授权凭证，包括当前用户所具有的角色信息、登录地址、sessionId、UserDetails 实现对象
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * UserDetails 实现对象
     */
    public static CustomUserDetails getUserDetails() {
        return (CustomUserDetails) getAuthentication().getPrincipal();
    }

    /**
     * 登录用户主键
     */
    public static Long getUserId() {
        CustomUserDetails userDetails = getUserDetails();
        if (userDetails == null) {
            return null;
        }
        return userDetails.getUserId();
    }

    /**
     * 登录用户名
     */
    public static String getUsername() {
        return getAuthentication().getName();
    }

}