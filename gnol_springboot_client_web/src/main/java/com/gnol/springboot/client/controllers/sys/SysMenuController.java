package com.gnol.springboot.client.controllers.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.services.sys.SysMenuService;

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
     * @Title: authMenu
     * @author: 吴佳隆
     * @data: 2020年7月14日 上午9:49:23
     * @Description: 当前登录用户的授权菜单列表
     * @return PageResult
     */
    @RequestMapping(value = "/authMenu", method = RequestMethod.GET)
    public PageResult authMenu() {
        return PageResult.ok();
    }

}