package com.gnol.springboot.client.controllers.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Title: IndexController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2019年06月17日 19:42:16
 * @Description: gnol 系统登录 Controller
 */
@Controller
public class IndexController {

    /**
     * @Title: chat
     * @author: 吴佳隆
     * @data: 2020年7月14日 下午5:09:25
     * @Description: 去聊天页面
     * @return String
     */
    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String chat() {
        return "chat";
    }

}