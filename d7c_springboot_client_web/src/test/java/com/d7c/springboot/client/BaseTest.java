package com.d7c.springboot.client;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Title: BaseTest
 * @Package: com.d7c.springboot.client
 * @author: 吴佳隆
 * @date: 2020年6月8日 上午8:27:40
 * @Description: 基础测试类，junit 测试类都需要继承此类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestWebApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@Transactional
public class BaseTest {
    @Autowired
    WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void contextLoads() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

}