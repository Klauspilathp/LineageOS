package com.gnol.springboot.client.config;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gnol.plugins.core.StringUtil;
import com.gnol.springboot.client.daos.sys.ExtSysUserDao;
import com.gnol.springboot.client.services.sys.SysMenuService;
import com.gnol.springboot.common.dos.sys.SysUser;
import com.gnol.springboot.common.enums.sys.StatusEnum;

/**
 * @Title: UserDetailsServiceImpl
 * @Package: com.gnol.springboot.client.config
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
     * gnol 系统菜单表 Service
     */
    @Resource(name = "sysMenuServiceImpl")
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtil.isBlank(username)) {
            throw new UsernameNotFoundException("userAccount cannot be empty !");
        }
        SysUser sysUser = sysUserDao.getSysUserByUserAccount(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("the user does not exist !");
        }
        Set<SimpleGrantedAuthority> authorities = sysMenuService.listPermissionsByRoleId(sysUser.getRoleId());
        User user = new User(username, // 用户名
                username + "&" + sysUser.getPassword(), // 盐值 + 加密后的密码
                StatusEnum.equalValue(StatusEnum.NORMAL, sysUser.getStatus()), // 账号是否被启用
                true, // 账号是否没有过期
                true, // 凭据是否没有过期
                true, // 账号是否没有被锁定
                authorities); // 权限列表
        logger.debug("{} 用户正在尝试登陆！", username);
        return user;
    }

}