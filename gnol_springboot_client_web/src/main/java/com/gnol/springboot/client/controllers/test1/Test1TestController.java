package com.gnol.springboot.client.controllers.test1;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.services.test1.Test1TestService;

/**
 * @Title: Test1TestController
 * @Package: com.gnol.springboot.client.controllers.test1
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:52:38
 * @Description: Test1Test Controller
 */
@Controller
@RequestMapping(value = "/test1/test")
public class Test1TestController extends WebBaseController {
    /**
     * Test1Test Service 实现
     */
    @Resource(name = "test1TestServiceImpl")
    private Test1TestService test1TestService;

}