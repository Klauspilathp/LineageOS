package top.d7c.springboot.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import top.d7c.plugins.tools.json.SFJsonUtil;

/**
 * @Title: MyCommandLineRunner
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2021年4月25日 下午12:49:20
 * @Description: 项目启动后立即执行一次。
 */
@Component
@Order(value = 1)
public class MyCommandLineRunner implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) throws Exception {
        logger.info(args == null ? "null" : SFJsonUtil.jsonToStr(args));
    }

}
