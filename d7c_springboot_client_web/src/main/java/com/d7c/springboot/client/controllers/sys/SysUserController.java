package com.d7c.springboot.client.controllers.sys;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.d7c.oauth2.springboot.SecurityUtil;
import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.config.D7cConstant;
import com.d7c.springboot.client.config.D7cProperties;
import com.d7c.springboot.client.controllers.WebBaseController;
import com.d7c.springboot.client.services.sys.SysUserService;

/**
 * @Title: SysUserController
 * @Package: com.d7c.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:35
 * @Description: d7c 系统_用户表 Controller
 */
@Controller
@RequestMapping(value = "/sys/user")
public class SysUserController extends WebBaseController {
    /**
     * d7c 系统_用户表 Service
     */
    @Resource(name = "sysUserServiceImpl")
    private SysUserService sysUserService;
    /**
     * SHA1 的 PasswordEncoder 加密实现类
     */
    @Resource(name = "sha1PasswordEncoder")
    private PasswordEncoder passwordEncoder;
    /**
     * d7c 系统自定义属性
     */
    @Resource(name = "d7cProperties")
    private D7cProperties d7cProperties;

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
        return "sys/user/user_index";
    }

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:40:20
     * @Description: 用户列表查询
     * @param page
     * @return PageResult
     */
    @RequestMapping(value = "/listPDPage")
    @RolesAllowed("sys_user:index")
    @ResponseBody
    public PageResult listPDPage(Page<PageData> page) {
        PageData pd = this.getPageData();
        pd.put(D7cConstant.SESSION_USER_ID, SecurityUtil.getUserId());
        page.setArgs(pd);
        // 查询用户列表
        return sysUserService.listPDPage(page);
    }

    /**
     * @Title: goAdd
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:40:32
     * @Description: 去新增系统用户页面
     * @return String
     */
    @GetMapping(value = "/goAdd")
    @RolesAllowed("sys_user:goAdd")
    public String goAdd() {
        return "sys/user/user_edit";
    }

    /**
     * @Title: add
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:40:49
     * @Description: 新增系统用户
     * @return PageResult
     */
    @RequestMapping(value = "/add")
    @RolesAllowed("sys_user:add")
    @ResponseBody
    public PageResult add() {
        PageData pd = this.getPageData();
        String userAccount = pd.getString("userAccount");
        if (StringUtil.isBlank(userAccount)) {
            return PageResult.error("请输入账号！");
        }
        String password = pd.getString("password");
        String chkpwd = pd.getString("chkpwd");
        // 密码为空，使用默认密码
        if (StringUtil.isBlank(password) || StringUtil.isBlank(chkpwd)) {
            password = d7cProperties.getDefaultPassword();
            chkpwd = d7cProperties.getDefaultPassword();
        }
        if (!password.equals(chkpwd)) {
            return PageResult.error("密码和确认密码不一致！");
        }
        pd.put("password", passwordEncoder.encode(userAccount + "&" + password));
        Long userId = SecurityUtil.getUserId();
        pd.put("addUser", userId);
        pd.put(D7cConstant.SESSION_USER_ID, userId);
        return sysUserService.insertUser(pd);
    }

    /**
     * @Title: goEdit
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:41:15
     * @Description: 去编辑系统用户页面
     * @return String
     */
    @GetMapping(value = "/goEdit")
    @RolesAllowed("sys_user:goEdit")
    public String goEdit() {
        return "sys/user/user_edit";
    }

    /**
     * @Title: edit
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:43:08
     * @Description: 保存编辑系统用户
     * @return PageResult
     */
    @RequestMapping(value = "/edit")
    @RolesAllowed("sys_user:edit")
    @ResponseBody
    public PageResult edit() {
        PageData pd = this.getPageData();
        String userAccount = pd.getString("userAccount");
        if (StringUtil.isBlank(userAccount)) {
            return PageResult.error("请输入账号！");
        }
        String password = pd.getString("password");
        String chkpwd = pd.getString("chkpwd");
        if (StringUtil.isNotBlank(password) && StringUtil.isNotBlank(chkpwd)) {
            if (!password.equals(chkpwd)) {
                return PageResult.error("密码和确认密码不一致！");
            }
            pd.put("password", passwordEncoder.encode(userAccount + "&" + password));
        } else {
            pd.put("password", "");
        }
        Long userId = SecurityUtil.getUserId();
        pd.put("modifyUser", userId);
        pd.put(D7cConstant.SESSION_USER_ID, userId);
        return sysUserService.updateUser(pd);
    }

    /**
     * @Title: info
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:46:18
     * @Description: 去系统用户详情页面
     * @return String
     */
    @GetMapping(value = "/info")
    @RolesAllowed("sys_user:info")
    public String info() {
        return "sys/user/user_info";
    }

    /**
     * @Title: getByKey
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:46:33
     * @Description: 根据主键查询系统用户信息
     * @return PageResult
     */
    @RequestMapping(value = "/getByKey")
    @RolesAllowed("sys_user:info")
    @ResponseBody
    public PageResult getByKey() {
        PageData pd = this.getPageData();
        // 查询的用户编号
        Long userId = pd.getLong("userId");
        // 用户编号不为 1 的用户不能查看用户编号为 D7cConstant.SUPER_ADMIN_ID 的用户
        if (userId != null && (!userId.equals(D7cConstant.SUPER_ADMIN_ID)
                || SecurityUtil.getUserId().equals(D7cConstant.SUPER_ADMIN_ID))) {
            pd = sysUserService.getPDByKey(userId);
        }
        return PageResult.ok(pd);
    }

    /**
     * @Title: del
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:47:41
     * @Description: 删除系统用户
     * @return PageResult
     */
    @RequestMapping(value = "/del")
    @RolesAllowed("sys_user:del")
    @ResponseBody
    public PageResult del() {
        PageData pd = this.getPageData();
        Long userId = SecurityUtil.getUserId();
        pd.put("modifyUser", userId);
        pd.put(D7cConstant.SESSION_USER_ID, userId);
        return sysUserService.updateStatus(pd);
    }

    /**
     * @Title: hasExist
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:47:59
     * @Description: 判断数据库中账号、手机号、邮箱是否已经存在并且不是当前用户
     * @return PageResult
     */
    @RequestMapping(value = "/hasExist")
    @ResponseBody
    public PageResult hasExist() {
        PageData pd = this.getPageData();
        return sysUserService.hasExist(pd);
    }

    /**
     * @Title: batchDel
     * @author: 吴佳隆
     * @data: 2020年7月19日 上午8:48:37
     * @Description: 批量软删除用户
     * @return PageResult
     */
    @RequestMapping(value = "/batchDel")
    @RolesAllowed("sys_user:batchDel")
    @ResponseBody
    public PageResult batchDel() {
        PageData pd = this.getPageData();
        Long userId = SecurityUtil.getUserId();
        pd.put("modifyUser", userId);
        pd.put(D7cConstant.SESSION_USER_ID, userId);
        return sysUserService.updateBatchStatus(pd);
    }

}