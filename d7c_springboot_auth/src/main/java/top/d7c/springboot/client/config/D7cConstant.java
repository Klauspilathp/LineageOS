package top.d7c.springboot.client.config;

/**
 * @Title: D7cConstant
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2019年12月10日 下午6:54:28
 * @Description: d7c 属性文件相关常量
 */
public class D7cConstant {
    /*********************************************** system ********************************************/
    // --- 当前服务器编号，0~1023，top.d7c.plugins.tools.idfactory.idworker.IdWorker 中 workerId 使用
    public static final String SERVER_NO = "SERVER_NO";

    /*********************************************** auth **********************************************/
    // --- 请求参数中授权类型的 key
    public static final String AUTH_TYPE = "auth_type";

}