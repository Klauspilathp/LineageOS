package com.d7c.springboot.client.controllers.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Title: IndexController
 * @Package: com.d7c.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2019年06月17日 19:42:16
 * @Description: d7c 系统登录 Controller
 */
@Controller
public class IndexController {

    /**
     * @Title: index
     * @author: 吴佳隆
     * @data: 2020年7月14日 下午5:43:19
     * @Description: 登录成功后首页
     * @return String
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * @Title: queue
     * @author: 吴佳隆
     * @data: 2020年7月14日 下午5:09:25
     * @Description: 去聊天页面
     * @return String
     */
    @RequestMapping(value = "/queue", method = RequestMethod.GET)
    public String queue() {
        return "queue";
    }

    /**
     * @Title: topic
     * @author: 吴佳隆
     * @data: 2020年7月14日 下午5:53:00
     * @Description: 去聊天室页面
     * @return String
     */
    @RequestMapping(value = "/topic", method = RequestMethod.GET)
    public String topic() {
        return "topic";
    }

}