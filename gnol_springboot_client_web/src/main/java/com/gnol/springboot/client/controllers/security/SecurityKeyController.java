package com.gnol.springboot.client.controllers.security;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.services.security.SecurityKeyService;

/**
 * @Title: SecurityKeyController
 * @Package: com.gnol.springboot.client.controllers.security
 * @author: 吴佳隆
 * @date: 2020年07月20日 12:02:41
 * @Description: gnol 系统安全模块_用户或系统密钥 Controller
 */
@Controller
@RequestMapping(value = "/security/key")
public class SecurityKeyController extends WebBaseController {
    /**
     * gnol 系统安全模块_用户或系统密钥 Service 实现
     */
    @Resource(name = "securityKeyServiceImpl")
    private SecurityKeyService securityKeyService;

}