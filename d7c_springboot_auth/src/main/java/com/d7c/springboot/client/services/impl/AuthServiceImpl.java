package com.d7c.springboot.client.services.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.config.D7cConstant;
import com.d7c.springboot.client.daos.sys.ExtSysUserDao;
import com.d7c.springboot.client.services.AuthService;
import com.d7c.springboot.client.services.ResourceService;
import com.d7c.springboot.client.services.TokenService;
import com.d7c.springboot.common.dos.sys.SysUser;
import com.d7c.springboot.common.enums.auth.AuthTypeEnum;
import com.d7c.springboot.common.enums.sys.StatusEnum;

import io.jsonwebtoken.Claims;

/**
 * @Title: AuthServiceImpl
 * @Package: com.d7c.springboot.client.services.impl
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午4:34:59
 * @Description: 授权服务实现
 */
@Service("authServiceImpl")
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    /**
     * d7c系统_用户表扩展 Dao
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
     * 访问资源实现
     */
    @Resource(name = "resourceServiceImpl")
    private ResourceService resourceService;

    @Override
    public PageResult login(PageData pd) {
        // 授权类型
        AuthTypeEnum authTypeEnum = AuthTypeEnum.forKey(pd.get(D7cConstant.AUTH_TYPE));
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
        Object resourceId = null;
        String auth_type = null;
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
                resourceId = sysUser.getRoleId();
                auth_type = authTypeEnum.getKey();
                claims.put("userId", sysUser.getUserId());
                claims.put("username", username);
                logger.debug("{} 用户正在尝试登陆！", username);
                break;
            case WAP:

                break;
            case MOBILE:

                break;
            default:
                return PageResult.error("暂不支持该认证类型！");
        }
        claims.put("resourceId", resourceId);
        claims.put(D7cConstant.AUTH_TYPE, auth_type);
        return PageResult.ok(tokenService.getToken(claims));
    }

    @Override
    public PageResult validate(PageData pd) {
        Claims claims = tokenService.getClaimsByToken(pd);
        if (claims == null) {
            return PageResult.error("token 错误！");
        }
        return resourceService.isPermitted(pd, claims) ? PageResult.ok(claims) : PageResult.error("没有访问权限！");
    }

    @Override
    public PageResult logout(PageData pd) {
        return PageResult.ok();
    }

}
