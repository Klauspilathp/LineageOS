package com.gnol.springboot.client.controllers.sys;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
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
    /**
     * oauth2 rest 服务客户端
     */
    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @PostMapping(value = "/test1")
    public PageResult test1(Principal principal) {
        System.out.println(principal);
        return PageResult.ok("SysTestController.test1");
    }

    @GetMapping(value = "/test2")
    public PageResult test2() {
        System.out.println(server);
        /*CustomUserDetails userDetails = SecurityUtil.getUserDetails();
        Authentication authentication = SecurityUtil.getAuthentication();
        String username = SecurityUtil.getUsername();
        Long userId = SecurityUtil.getUserId();*/
        return PageResult.ok("SysTestController.test2");
    }

    @GetMapping(value = "/test3")
    public PageResult test3() {
        OAuth2AccessToken accessToken = oAuth2RestTemplate.getAccessToken();
        return PageResult.ok(accessToken);
    }

}