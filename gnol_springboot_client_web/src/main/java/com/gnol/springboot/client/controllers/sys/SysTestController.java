package com.gnol.springboot.client.controllers.sys;

import java.io.FileNotFoundException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gnol.fastDFS.spring.boot.autoconfigure.FastDFService;
import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.services.sys.SysTestService;
import com.gnol.springboot.client.services.test1.Test1TestService;

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
    @Autowired
    private FastDFService fastDFService;
    /**
     * SysTest Service 实现
     */
    @Resource(name = "sysTestServiceImpl")
    private SysTestService sysTestService;
    /**
     * Test1Test Service 实现
     */
    @Resource(name = "test1TestServiceImpl")
    private Test1TestService test1TestService;

    @RequestMapping(value = "/test1")
    public String test1(ModelMap map) throws FileNotFoundException {
        System.out.println(fastDFService);
        /*File file = new File("E:\\Trash\\temp\\logo.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        String uploadFile = fastDFService.uploadFile(fileInputStream, file.length(), "png", null);
        System.out.println(uploadFile);*/

        /*byte[] downloadFileByByte = fastDFService
                .downloadFileByByte("group1/M00/00/6E/rBKEUV7h21eAIoz_AAABlYi1F6w031.png");*/
        map.addAttribute("name", "吴佳隆");
        return "sys/test/test1";
    }

}