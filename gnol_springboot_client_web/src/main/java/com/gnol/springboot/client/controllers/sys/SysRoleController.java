package com.gnol.springboot.client.controllers.sys;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.client.config.GnolConstant;
import com.gnol.springboot.client.config.SecurityUtil;
import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.dtos.tree.SelectTree;
import com.gnol.springboot.client.dtos.tree.ZTree;
import com.gnol.springboot.client.services.sys.SysRoleMenuService;
import com.gnol.springboot.client.services.sys.SysRoleService;
import com.gnol.springboot.common.dos.sys.SysRole;

/**
 * @Title: SysRoleController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年7月19日 下午4:51:47
 * @Description: gnol 系统角色表 Controller
 */
@Controller
@RequestMapping(value = "/sys/role")
public class SysRoleController extends WebBaseController {
    /**
     * gnol 系统角色表 Service
     */
    @Resource(name = "sysRoleServiceImpl")
    private SysRoleService sysRoleService;
    /**
     * gnol 系统角色菜单表 Service
     */
    @Resource(name = "sysRoleMenuServiceImpl")
    private SysRoleMenuService sysRoleMenuService;

    /**
     * @Title: index
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:53:12
     * @Description: 角色管理首页
     * @return String
     */
    @GetMapping(value = "/index")
    @RolesAllowed("sys_role:index")
    public String index() {
        return "sys/role/role_index";
    }

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:54:13
     * @Description: 查询角色列表
     * @param page
     * @return PageResult
     */
    @RequestMapping(value = "/listPDPage")
    @RolesAllowed("sys_role:index")
    @ResponseBody
    public PageResult listPDPage(Page<PageData> page) {
        PageData pd = this.getPageData();
        pd.put(GnolConstant.SESSION_USER_ID, SecurityUtil.getUserId());
        page.setArgs(pd);
        // 查询用户列表
        return sysRoleService.listPDPage(page);
    }

    /**
     * @Title: goAdd
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:40:32
     * @Description: 去新增角色页面
     * @return String
     */
    @GetMapping(value = "/goAdd")
    @RolesAllowed("sys_role:goAdd")
    public String goAdd() {
        return "sys/role/role_edit";
    }

    /**
     * @Title: listTopSelectTreeBy
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:56:43
     * @Description: 查询顶级角色列表
     * @param page
     * @return PageResult
     */
    @RequestMapping(value = "/listTopSelectTreeBy")
    @ResponseBody
    public PageResult listTopSelectTreeBy(Page<PageData> page) {
        List<SelectTree> roleZtree = sysRoleService.listTopSelectTreeBy();
        PageData pd = this.getPageData();
        return PageResult.ok(roleZtree).setExtData(pd);
    }

    /**
     * @Title: add
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午4:57:53
     * @Description: 新增角色
     * @return PageResult
     */
    @RequestMapping(value = "/add")
    @RolesAllowed("sys_role:add")
    @ResponseBody
    public PageResult add() {
        PageData pd = this.getPageData();
        Long userId = SecurityUtil.getUserId();
        pd.put("addUser", userId);
        pd.put(GnolConstant.SESSION_USER_ID, userId);
        return sysRoleService.insertRole(pd);
    }

    /**
     * @Title: goEdit
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:40:32
     * @Description: 去编辑角色页面
     * @return String
     */
    @GetMapping(value = "/goEdit")
    @RolesAllowed("sys_role:goEdit")
    public String goEdit() {
        return "sys/role/role_edit";
    }

    /**
     * @Title: getParentRole
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:00:11
     * @Description: 查询当前角色及其父角色信息
     * @return ModelAndView
     */
    @RequestMapping(value = "/goEdit")
    @RolesAllowed("sys_role:goEdit")
    @ResponseBody
    public PageResult getParentRole() {
        PageData pd = this.getPageData();
        pd = sysRoleService.getPDByKey(pd.getLong("roleId"));
        SysRole parentRole = sysRoleService.getByKey(pd.getLong("parentId"));
        if (parentRole != null) {
            pd.put("parentRoleName", parentRole.getRoleName());
        }
        return PageResult.ok(pd);
    }

    /**
     * @Title: edit
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:00:54
     * @Description: 编辑角色
     * @return PageResult
     */
    @RequestMapping(value = "/edit")
    @RolesAllowed("sys_role:edit")
    @ResponseBody
    public PageResult edit() {
        PageData pd = this.getPageData();
        Long userId = SecurityUtil.getUserId();
        pd.put("modifyUser", userId);
        pd.put(GnolConstant.SESSION_USER_ID, userId);
        return sysRoleService.updateRole(pd);
    }

    /**
     * @Title: del
     * @author: 吴佳隆
     * @data: 2020年4月12日 上午11:32:56
     * @Description: 软删除角色
     * @return PageResult
     */
    @RequestMapping(value = "/del")
    @RolesAllowed("sys_role:del")
    @ResponseBody
    public PageResult del() {
        PageData pd = this.getPageData();
        Long userId = SecurityUtil.getUserId();
        pd.put("modifyUser", userId);
        pd.put(GnolConstant.SESSION_USER_ID, userId);
        return sysRoleService.updateStatus(pd);
    }

    /**
     * @Title: goAuth
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:02:08
     * @Description: 去授权页面
     * @return String
     */
    @GetMapping(value = "/goAuth")
    @RolesAllowed("sys_role:goAuth")
    public String goAuth() {
        return "sys/role/role_auth";
    }

    /**
     * @Title: listAuthByRoleId
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午5:03:15
     * @Description: 查询角色授权数据
     * @return PageResult
     */
    @RequestMapping(value = "/goAuth")
    @RolesAllowed("sys_role:goAuth")
    @ResponseBody
    public PageResult listAuthByRoleId() {
        PageData pd = this.getPageData();
        pd.put(GnolConstant.SESSION_USER_ID, SecurityUtil.getUserId());
        List<ZTree> zTrees = sysRoleMenuService.listAuthByRoleId(pd);
        return PageResult.ok(zTrees).setExtData(pd);
    }

    /**
     * @Title: saveAuth
     * @author: 吴佳隆
     * @data: 2020年4月17日 下午7:15:00
     * @Description: 保存权限
     * @return PageResult
     */
    @RequestMapping(value = "/saveAuth")
    @RolesAllowed("sys_role:saveAuth")
    @ResponseBody
    public PageResult saveAuth() {
        PageData pd = this.getPageData();
        pd.put(GnolConstant.SESSION_USER_ID, SecurityUtil.getUserId());
        return sysRoleMenuService.saveAuth(pd);
    }

}