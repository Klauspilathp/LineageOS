package com.gnol.springboot.client.config;

/**
 * @Title: GnolConstant
 * @Package: com.gnol.springboot.client.config
 * @author: 吴佳隆
 * @date: 2019年12月10日 下午6:54:28
 * @Description: gnol 属性文件相关常量
 */
public class GnolConstant {
    /*********************************************** system ********************************************/
    // --- 当前服务器编号，0~1023，com.gnol.plugins.tools.idfactory.idworker.IdWorker 中 workerId 使用
    public static final String SERVER_NO = "SERVER_NO";
    // --- session 前缀名
    public static final String JSESSIONID = "GNOL_JSESSIONID";

    /*********************************************** session ********************************************/
    // --- 绑定到会话的属性名
    public static final String SESSION_ATTR_NAME = "SESSION_ATTR_NAME";
    // --- 验证码
    public static final String SESSION_VERIFY_CODE = "SESSION_VERIFY_CODE";
    // --- session 中的用户信息及角色信息
    public static final String SESSION_USER = "SESSION_USER";
    // --- 当前登录用户编号在 PageData 中的 KEY
    public static final String SESSION_USER_ID = "SESSION_USER_ID";
    // --- 全部菜单
    public static final String SESSION_ALL_MENULIST = "SESSION_ALL_MENULIST";
    // --- 当前菜单前缀
    public static final String SESSION_CURR_MENULIST_ = "SESSION_CURR_MENULIST_";

    /*********************************************** web ***********************************************/
    // 超级管理员编号
    public static final Long SUPER_ADMIN_ID = 1L;
    // 系统预定义角色编号
    public static final Long ROLE_ID_1 = 1L; // 系统管理员
    public static final Long ROLE_ID_2 = 2L; // 集团管理员
    public static final Long ROLE_ID_3 = 3L; // 区域管理员
    public static final Long ROLE_ID_4 = 4L; // 分公司管理员
    public static final Long ROLE_ID_5 = 5L; // 部门管理员
    // 去登录页面
    public static final String WEB_TOLOGIN = "/toLogin.shtml";
    // --- 系统名
    public static final String WEB_SYSTEM_NAME = "web.system.name";
    // --- 访问限制接口路径，用于验证哪些请求路径可以通过
    public static final String WEB_PATH_ACCESS_LIMIT = "web.path.access.limit";
    // --- 是否开启登录背景音乐
    public static final String WEB_IS_MUSIC = "web.is.music";
    // 系统默认密码
    public static final String WEB_DEFAULT_PASSWORD = "web.default.password";

    /*********************************************** api ***********************************************/
    // api 不校验权限的 uri
    public static String API_AUTH_IGNORE_URI = "api.auth.ignore.uri";

    /*********************************************** rest **********************************************/

    /*********************************************** rpc ***********************************************/

    /*********************************************** websocket *****************************************/

    /*********************************************** task **********************************************/

}