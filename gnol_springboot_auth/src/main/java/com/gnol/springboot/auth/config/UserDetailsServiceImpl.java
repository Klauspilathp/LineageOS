package com.gnol.springboot.auth.config;

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

import com.gnol.plugins.core.StringUtil;
import com.gnol.redis.spring.boot.autoconfigure.RedisService;
import com.gnol.springboot.auth.daos.sys.ExtSysMenuDao;
import com.gnol.springboot.auth.daos.sys.ExtSysUserDao;
import com.gnol.springboot.common.dos.sys.SysUser;
import com.gnol.springboot.common.enums.auth.AuthTypeEnum;
import com.gnol.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: UserDetailsServiceImpl
 * @Package: com.gnol.springboot.auth.config
 * @author: 吴佳隆
 * @date: 2020年7月13日 下午3:37:41
 * @Description: spring security UserDetailsService 实现
 */
@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    /**
     * gnol系统_用户表扩展 Dao
     */
    @Resource(name = "extSysUserDao")
    private ExtSysUserDao sysUserDao;
    /**
     * gnol 系统菜单表扩展 Dao
     */
    @Resource(name = "extSysMenuDao")
    private ExtSysMenuDao sysMenuDao;
    /**
     * redis 缓存服务实现
     */
    @Resource(name = "redisServiceImpl")
    private RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtil.isBlank(username)) {
            throw new UsernameNotFoundException("userAccount cannot be empty !");
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        // 授权类型
        AuthTypeEnum authTypeEnum = AuthTypeEnum.forKey(request.getParameter(GnolConstant.AUTH_TYPE));
        if (authTypeEnum == null) {
            throw new UsernameNotFoundException("auth_type cannot be empty !");
        }
        switch (authTypeEnum) {
            case WEB:
                SysUser sysUser = sysUserDao.getSysUserByUserAccount(username);
                if (sysUser == null) {
                    throw new UsernameNotFoundException("the user does not exist !");
                }
                Set<SimpleGrantedAuthority> authorities = sysMenuDao.listPermissionsByRoleId(sysUser.getRoleId());
                CustomUserDetails user = new CustomUserDetails(sysUser.getUserId(), // 系统用户主键
                        username, // 用户名
                        username + "&" + sysUser.getPassword(), // 盐值 + 加密后的密码
                        StatusEnum.equalValue(StatusEnum.NORMAL, sysUser.getStatus()), // 账号是否被启用
                        authorities); // 权限列表
                logger.debug("{} 用户正在尝试登陆！", username);
                return user;
            case WAP:

                break;
            case MOBILE:

                break;
            default:
                throw new UsernameNotFoundException("this authorization type is not supported !");
        }
        throw new UsernameNotFoundException("authorization failure !");
    }

}