package com.d7c.springboot.client.config;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.d7c.oauth2.spring.boot.CustomUserDetails;
import com.d7c.plugins.core.StringUtil;
import com.d7c.redis.spring.boot.autoconfigure.RedisService;
import com.d7c.springboot.client.daos.sys.ExtSysUserDao;
import com.d7c.springboot.client.services.sys.SysMenuService;
import com.d7c.springboot.common.dos.sys.SysUser;
import com.d7c.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: UserDetailsServiceImpl
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月13日 下午3:37:41
 * @Description: spring security UserDetailsService 实现
 */
@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    /**
     * d7c系统_用户表扩展 Dao
     */
    @Resource(name = "extSysUserDao")
    private ExtSysUserDao sysUserDao;
    /**
     * d7c 系统菜单表 Service
     */
    @Resource(name = "sysMenuServiceImpl")
    private SysMenuService sysMenuService;
    /**
     * redis 缓存服务实现
     */
    @Resource(name = "redisServiceImpl")
    private RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        // 验证码
        String verifyCode = redisService
                .getString(redisService.generateKey(D7cConstant.SESSION_VERIFY_CODE, request.getSession().getId()));
        logger.debug("服务器端验证码为 {}，用户输入的验证码为 {}。", verifyCode, request.getParameter(D7cConstant.SESSION_VERIFY_CODE));
        if (StringUtil.isBlank(username)) {
            throw new UsernameNotFoundException("userAccount cannot be empty !");
        }
        SysUser sysUser = sysUserDao.getSysUserByUserAccount(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("the user does not exist !");
        }
        Set<SimpleGrantedAuthority> authorities = sysMenuService.listPermissionsByRoleId(sysUser.getRoleId());
        /*User user = new User(username, // 用户名
                username + "&" + sysUser.getPassword(), // 盐值 + 加密后的密码
                StatusEnum.equalValue(StatusEnum.NORMAL, sysUser.getStatus()), // 账号是否被启用
                true, // 账号是否没有过期
                true, // 凭据是否没有过期
                true, // 账号是否没有被锁定
                authorities); // 权限列表*/
        CustomUserDetails user = new CustomUserDetails(sysUser.getUserId(), // 系统用户主键
                username, // 用户名
                username + "&" + sysUser.getPassword(), // 盐值 + 加密后的密码
                StatusEnum.equalValue(StatusEnum.NORMAL, sysUser.getStatus()), // 账号是否被启用
                authorities); // 权限列表
        logger.debug("{} 用户正在尝试登陆！", username);
        return user;
    }

}