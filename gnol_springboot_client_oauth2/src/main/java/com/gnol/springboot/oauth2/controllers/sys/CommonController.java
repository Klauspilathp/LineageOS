package com.gnol.springboot.oauth2.controllers.sys;

import java.security.Principal;
import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.oauth2.controllers.WebBaseController;

/**
 * @Title: CommonController
 * @Package: com.gnol.springboot.oauth2.controllers.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:45
 * @Description: gnol 系统_公共 Controller
 */
@RestController
@RequestMapping(value = "/common")
public class CommonController extends WebBaseController {

    /**
     * @Title: getTime
     * @author: 吴佳隆
     * @data: 2020年5月1日 上午9:43:02
     * @Description: 获取服务器时间
     * @return PageResult
     */
    @RequestMapping(value = "/getTime")
    @ResponseBody
    public PageResult getTime() {
        return PageResult.ok(new Date());
    }

    /**
     * @Title: user_info
     * @author: 吴佳隆
     * @data: 2020年7月26日 下午2:52:37
     * @Description: 获取当前登录用户信息
     * @param user
     * @return Principal
     */
    @GetMapping("/user_info")
    public Principal user_info(Principal user) {
        return user;
    }

}