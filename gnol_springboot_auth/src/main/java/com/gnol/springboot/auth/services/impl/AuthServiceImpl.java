package com.gnol.springboot.auth.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.springboot.auth.config.GnolConstant;
import com.gnol.springboot.auth.daos.sys.ExtSysUserDao;
import com.gnol.springboot.auth.services.AuthService;
import com.gnol.springboot.auth.services.ResourceService;
import com.gnol.springboot.auth.services.TokenService;
import com.gnol.springboot.common.dos.sys.SysUser;
import com.gnol.springboot.common.enums.auth.AuthTypeEnum;
import com.gnol.springboot.common.enums.sys.StatusEnum;

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
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    /**
     * gnol系统_用户表扩展 Dao
     */
    @Resource(name = "extSysUserDao")
    private ExtSysUserDao sysUserDao;
    /**
     * SHA1 的 PasswordEncoder 加密实现类
     */
    @Resource(name = "sha1PasswordEncoder")
    private PasswordEncoder passwordEncoder;
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
    public PageResult login(PageData pd) {
        // 授权类型
        AuthTypeEnum authTypeEnum = AuthTypeEnum.forKey(pd.get(GnolConstant.AUTH_TYPE));
        if (authTypeEnum == null) {
            return PageResult.error("认证类型错误！");
        }
        String username = pd.getString("username");
        if (StringUtil.isBlank(username)) {
            return PageResult.error("username 不能为空！");
        }
        String password = pd.getString("password");
        if (StringUtil.isBlank(password)) {
            return PageResult.error("password 不能为空！");
        }
        Map<String, Object> claims = new HashMap<String, Object>();
        switch (authTypeEnum) {
            case WEB:
                SysUser sysUser = sysUserDao.getSysUserByUserAccount(username);
                if (sysUser == null) {
                    return PageResult.error("the user does not exist !");
                }
                if (!StatusEnum.equalValue(StatusEnum.NORMAL, sysUser.getStatus())) {
                    return PageResult.error("the user account has been disabled !");
                }
                if (!passwordEncoder.matches(password, // 密文密码
                        username + "&" + sysUser.getPassword() // 盐值 + 加密后的密码
                )) {
                    return PageResult.error("wrong password !");
                }
                claims.put("userId", sysUser.getUserId());
                claims.put("roleId", sysUser.getRoleId());
                claims.put("username", username);
                claims.put("subject", authTypeEnum.getKey());
                logger.debug("{} 用户正在尝试登陆！", username);
                break;
            case WAP:

                break;
            case MOBILE:

                break;
            default:
                return PageResult.error("暂不支持该认证类型！");
        }
        return PageResult.ok(tokenService.getToken(claims));
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
        if (claims.getExpiration().before(new Date())) {
            return PageResult.error("授权已过期");
        }
        return PageResult.ok(claims.toString());
    }

    @Override
    public PageResult logout(PageData pd) {
        return PageResult.ok();
    }

}
