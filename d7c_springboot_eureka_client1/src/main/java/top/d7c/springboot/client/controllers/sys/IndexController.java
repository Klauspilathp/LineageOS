package top.d7c.springboot.client.controllers.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.d7c.plugins.core.PageResult;

/**
 * @Title: IndexController
 * @Package: top.d7c.springboot.client.controlles.sys
 * @author: 吴佳隆
 * @date: 2020年6月17日 上午9:22:10
 * @Description: 测试服务发现
 */
@RestController
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * @Title: index
     * @author: 吴佳隆
     * @data: 2020年8月2日 上午10:38:29
     * @Description: 系统首页
     * @return PageResult
     */
    @RequestMapping(value = "/index")
    public PageResult index() {
        logger.info("登录成功");
        return PageResult.ok("登录成功");
    }

}