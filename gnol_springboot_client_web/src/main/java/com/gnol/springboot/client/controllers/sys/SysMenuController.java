package com.gnol.springboot.client.controllers.sys;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.StringUtil;
import com.gnol.redis.spring.boot.autoconfigure.RedisService;
import com.gnol.springboot.client.config.GnolConstant;
import com.gnol.springboot.client.config.RedisPersistentTokenRepository;
import com.gnol.springboot.client.config.SecurityUtil;
import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.dtos.tree.ZTree;
import com.gnol.springboot.client.services.sys.SysMenuService;
import com.gnol.springboot.client.services.sys.SysUserService;
import com.gnol.springboot.common.dos.sys.SysMenu;
import com.gnol.springboot.common.dos.sys.SysUser;
import com.gnol.springboot.common.enums.sys.MenuFuncEnum;
import com.gnol.springboot.common.enums.sys.StatusEnum;

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
    @GetMapping(value = "/authMenu")
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

    /**
     * @Title: index
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:31:26
     * @Description: 菜单管理首页
     * @return String
     */
    @GetMapping(value = "/index")
    @RolesAllowed("sys_menu:index")
    public String index() {
        return "sys/menu/menu_ztree";
    }

    /**
     * @Title: listZTreeFormTreeFrameByParentId_SYNC
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:33:28
     * @Description: 同步查询菜单树
     * @param menuId
     * @return PageResult
     */
    @GetMapping(value = "/listZTreeFormTreeFrameByParentId_SYNC")
    @ResponseBody
    public PageResult listZTreeFormTreeFrameByParentId_SYNC(Integer menuId) {
        List<ZTree> zTrees = sysMenuService.listZTreeFormTreeFrameByParentId_SYNC(0);
        return PageResult.ok(zTrees);
    }

    /**
     * @Title: listAllZTreeByParentId
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:35:06
     * @Description: 去菜单列表页
     * @return String
     */
    @GetMapping(value = "/listAllZTreeByParentId")
    @RolesAllowed("sys_menu:listAllZTreeByParentId")
    public String menu_list() {
        return "sys/menu/menu_list";
    }

    /**
     * @Title: listAllZTreeByParentId
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:36:21
     * @Description: 根据父级菜单查询所有子级菜单列表
     * @return PageResult
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/listAllZTreeByParentId")
    @ResponseBody
    public PageResult listAllZTreeByParentId() {
        PageData pd = this.getPageData();
        int menuId = StringUtil.toInt(pd.get("menuId"));
        List<PageData> pds = sysMenuService.listMenuByParentId(menuId);
        // 根据菜单编号获取菜单对象
        if (menuId > 0) {
            pd.putAll(sysMenuService.getPDByKey(menuId));
        }
        return PageResult.ok(pds).setExtData(pd);
    }

    /**
     * @Title: goEditMenu
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:39:32
     * @Description: 去编辑菜单页面
     * @return String
     */
    @GetMapping(value = "/goEditMenu")
    @RolesAllowed("sys_menu:goEditMenu")
    public String goEditMenu() {
        return "sys/menu/menu_edit";
    }

    /**
     * @Title: goEditMenu
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:43:28
     * @Description: 查询要编辑的菜单信息
     * @param menuId        菜单主键
     * @return PageResult
     */
    @RequestMapping(value = "/goEditMenu")
    @ResponseBody
    public PageResult goEditMenu(Integer menuId) {
        PageData pd = sysMenuService.getPDByKey(menuId);
        if (pd == null || pd.isEmpty()) {
            return PageResult.ok();
        }
        PageData emptyPageData = this.getEmptyPageData();

        int parentId = StringUtil.toInt(pd.get("parentId"));
        if (parentId > 0) {
            SysMenu parentMenu = sysMenuService.getByKey(parentId);
            emptyPageData.put("parentMenuName", parentMenu.getMenuName());
        }
        emptyPageData.put("parentId", parentId);
        return PageResult.ok(pd).setExtData(emptyPageData);
    }

    /**
     * @Title: edit
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:45:27
     * @Description: 编辑菜单
     * @param sysMenu       要编辑的菜单对象
     * @return ModelAndView
     */
    @RequestMapping(value = "/edit")
    @RolesAllowed("sys_menu:edit")
    @ResponseBody
    public PageResult edit(SysMenu sysMenu) {
        sysMenu.setModifyUser(SecurityUtil.getUserId());
        sysMenuService.update(sysMenu);
        return PageResult.ok(sysMenu.getParentId());
    }

    /**
     * @Title: goAdd
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:39:32
     * @Description: 去新增菜单页面
     * @return String
     */
    @GetMapping(value = "/goAdd")
    @RolesAllowed("sys_menu:goAdd")
    public String goAdd() {
        return "sys/menu/menu_edit";
    }

    /**
     * @Title: getParentMenu
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:48:00
     * @Description: 新增菜单时财讯父菜单信息
     * @return PageResult
     */
    @RequestMapping(value = "/getParentMenu")
    @ResponseBody
    public PageResult getParentMenu() {
        PageData pd = this.getPageData();
        Integer menuId = StringUtil.toInteger(pd.remove("menuId"));
        SysMenu parentMenu = sysMenuService.getByKey(menuId);
        if (parentMenu != null) {
            pd.put("parentMenuName", parentMenu.getMenuName());
            pd.put("menuType", parentMenu.getMenuType());
        }
        return PageResult.ok(pd);
    }

    /**
     * @Title: add
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:49:38
     * @Description: 新增菜单
     * @param sysMenu       要新增的菜单对象
     * @return PageResult
     */
    @RequestMapping(value = "/add")
    @RolesAllowed("sys_menu:add")
    @ResponseBody
    public PageResult add(SysMenu sysMenu) {
        sysMenu.setAddUser(SecurityUtil.getUserId());
        sysMenu.setStatus_Enum(StatusEnum.NORMAL);
        MenuFuncEnum menuFuncEnum = MenuFuncEnum.forKey(sysMenu.getMenuFunc());
        if (menuFuncEnum != null && menuFuncEnum != MenuFuncEnum.BUTTON) {
            sysMenu.setIcon("menu-icon fa fa-leaf black");
        }
        sysMenuService.insert(sysMenu);
        return PageResult.ok(sysMenu.getParentId());
    }

    /**
     * @Title: del
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:50:47
     * @Description: 菜单软删除
     * @return PageResult
     */
    @RequestMapping(value = "/del")
    @RolesAllowed("sys_menu:del")
    @ResponseBody
    public PageResult del() {
        PageData pd = this.getPageData();
        pd.put("modifyUser", SecurityUtil.getUserId());
        return sysMenuService.updateStatus(pd);
    }

}