package com.gnol.springboot.client.controllers.sys;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gnol.plugins.core.PageResult;
import com.gnol.redis.spring.boot.autoconfigure.RedisService;
import com.gnol.springboot.client.config.GnolConstant;
import com.gnol.springboot.client.config.RedisPersistentTokenRepository;
import com.gnol.springboot.client.config.SecurityUtil;
import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.services.sys.SysMenuService;
import com.gnol.springboot.client.services.sys.SysUserService;
import com.gnol.springboot.common.dos.sys.SysUser;

/**
 * @Title: SysMenuController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年7月14日 上午9:45:56
 * @Description: gnol 系统菜单表 Controller
 */
@Controller
@RequestMapping(value = "/sys/menu")
public class SysMenuController extends WebBaseController {
    /**
     * gnol 系统菜单表 Service
     */
    @Resource(name = "sysMenuServiceImpl")
    private SysMenuService sysMenuService;
    /**
     * redis 缓存服务实现
     */
    @Resource(name = "redisServiceImpl")
    private RedisService redisService;
    /**
     * gnol 系统_用户表 Service
     */
    @Resource(name = "sysUserServiceImpl")
    private SysUserService sysUserService;

    /**
     * @Title: authMenu
     * @author: 吴佳隆
     * @data: 2020年7月14日 上午9:49:23
     * @Description: 当前登录用户的授权菜单列表
     * @return PageResult
     */
    @RequestMapping(value = "/authMenu", method = RequestMethod.GET)
    @ResponseBody
    public PageResult authMenu() {
        Long userId = SecurityUtil.getUserId();
        Object listMenuTree = redisService
                .getObject(redisService.generateKey(GnolConstant.MENULIST, SecurityUtil.getUserId()));
        if (listMenuTree == null) {
            SysUser user = sysUserService.getByKey(userId);
            if (user != null) {
                listMenuTree = sysMenuService.listMenuTreeByRoleId(user.getRoleId());
                if (listMenuTree == null) { // 防止缓存击穿
                    listMenuTree = new ArrayList<>(1);
                }
                redisService.addObject(redisService.generateKey(GnolConstant.MENULIST, userId),
                        RedisPersistentTokenRepository.TOKEN_EXPIRATION, listMenuTree);
            }
        }
        return PageResult.ok(listMenuTree);
    }

}