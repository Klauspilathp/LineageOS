package com.d7c.springboot.common.actuator.endpoint;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.core.env.Environment;

import com.d7c.plugins.core.PageResult;

/**
 * @Title: CustomEndPoint
 * @Package: com.d7c.springboot.common.actuator.endpoint
 * @author: 吴佳隆
 * @date: 2021年1月4日 下午1:49:39
 * @Description: 自定义监控端点，访问 /actuator/custom-endPoint 端点会出现自定义信息。
 */
@Endpoint(id = "custom-endPoint") // 构建 REST API 请求的唯一路径，不能包含下划线“_”。
public class CustomEndPoint {
    /**
     * 当前应用运行时环境
     */
    private Environment environment;

    public CustomEndPoint() {
        super();
    }

    public CustomEndPoint(Environment environment) {
        super();
        this.environment = environment;
    }

    /**
     * 响应状态为 200，如果没有返回值响应 404。
     * @WriteOperation POST 请求
     * @DeleteOperation DELETE 请求
     */
    @ReadOperation // GET 请求
    public PageResult customRead() {
        String hostAddress;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            hostAddress = "127.0.0.1";
        }
        return PageResult.ok("my name is " + environment.getProperty(ConfigFileApplicationListener.CONFIG_NAME_PROPERTY)
                + " and my visiting address is http://" + hostAddress + ":"
                + environment.getProperty("server.port", ""));
    }

}