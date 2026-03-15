package com.gnol.springboot.auth.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: Auth2Controller
 * @Package: com.gnol.springboot.auth.controllers
 * @author: 吴佳隆
 * @date: 2020年6月27日 下午7:42:47
 * @Description: auth2 认证控制器
 */
@RestController
@RequestMapping("/auth2")
public class Auth2Controller {

    @GetMapping(value = "/current")
    public Principal getUser(Principal principal) {
        return principal;
    }

}