package com.gnol.springboot.client.controllers.sys;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.client.controllers.WebBaseController;

/**
 * @Title: SysTestController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年6月8日 下午3:15:37
 * @Description: gnol 系统测试 Controller
 */
@RestController
@RequestMapping(value = "/sys/test")
public class SysTestController extends WebBaseController {
    /**
     * 服务属性配置
     */
    @Autowired
    private ServerProperties server;
    @Autowired
    private SecurityProperties security;
    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @PostMapping(value = "/test1")
    public PageResult test1(Principal principal) {
        System.out.println(principal);
        return PageResult.ok("SysTestController.test1");
    }

    @GetMapping(value = "/test2")
    public PageResult test2() {
        String hostAddress = server.getAddress().getHostAddress();
        return PageResult.ok("SysTestController.test2");
    }

}