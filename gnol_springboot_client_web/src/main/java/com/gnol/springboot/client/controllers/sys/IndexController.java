package com.gnol.springboot.client.controllers.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.services.sys.SysImgService;

/**
 * @Title: IndexController
 * @Package: com.gnol.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2019年06月17日 19:42:16
 * @Description: gnol 系统登录 Controller
 */
@Controller
public class IndexController extends WebBaseController {
    /**
     * gnol 系统图片管理 Service 实现
     */
    @Resource(name = "sysImgServiceImpl")
    private SysImgService sysImgService;

    /**
     * @Title: index
     * @author: 吴佳隆
     * @data: 2020年7月13日 下午6:51:28
     * @Description: 去登录页面
     * @return String
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * @Title: main
     * @author: 吴佳隆
     * @data: 2020年7月13日 下午6:51:43
     * @Description: 登录成功后去系统首页
     * @return String
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main() {
        return "sys/index/main";
    }

}