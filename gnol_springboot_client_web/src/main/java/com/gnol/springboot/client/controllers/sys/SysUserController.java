package com.gnol.springboot.client.controllers.sys;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.services.sys.SysDictService;
import com.gnol.springboot.client.services.sys.SysOrgService;
import com.gnol.springboot.client.services.sys.SysRoleService;
import com.gnol.springboot.client.services.sys.SysUserService;

/**
 * @Title: SysUserController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:35
 * @Description: gnol 系统_用户表 Controller
 */
@Controller
@RequestMapping(value = "/sys/user")
public class SysUserController extends WebBaseController {
    /**
     * gnol 系统_用户表 Service
     */
    @Resource(name = "sysUserServiceImpl")
    private SysUserService sysUserService;
    /**
     * gnol 系统角色表 Service
     */
    @Resource(name = "sysRoleServiceImpl")
    private SysRoleService sysRoleService;
    /**
     * gnol 系统组织机构 Service
     */
    @Resource(name = "sysOrgServiceImpl")
    private SysOrgService sysOrgService;
    /**
     * gnol 系统字典 Service
     */
    @Resource(name = "sysDictServiceImpl")
    private SysDictService sysDictService;
    /**
     * SHA1 的 PasswordEncoder 加密实现类
     */
    @Resource(name = "sha1PasswordEncoder")
    private PasswordEncoder passwordEncoder;

    /**
     * @Title: index
     * @author: 吴佳隆
     * @data: 2020年7月18日 下午6:44:14
     * @Description: 系统用户首页
     * @return String
     */
    @GetMapping(value = "/index")
    @RolesAllowed("sys_user:index")
    public String index() {
        return "sys/user/index";
    }

}