package com.gnol.springboot.client.controllers.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.client.controllers.WebBaseController;

/**
 * @Title: SysTestController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年6月8日 下午3:15:37
 * @Description: gnol 系统测试 Controller
 */
@Controller
@RequestMapping(value = "/sys/test")
public class SysTestController extends WebBaseController {

    @RequestMapping(value = "/test1")
    @ResponseBody
    public PageResult test1() {
        return PageResult.ok("SysTestController.test1");
    }

    @RequestMapping(value = "/test2")
    @ResponseBody
    public PageResult test2() {
        return PageResult.ok("SysTestController.test2");
    }

}