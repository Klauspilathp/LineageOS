package com.gnol.springboot.client.controllers.sys;

import java.io.FileNotFoundException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gnol.fastDFS.spring.boot.autoconfigure.FastDFService;
import com.gnol.mybatis.spring.boot.autoconfigure.CurrDataSource;
import com.gnol.mybatis.spring.boot.autoconfigure.DataSourceType;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.services.sys.SysTestService;
import com.gnol.springboot.client.services.test1.Test1TestService;
import com.gnol.springboot.common.dos.sys.SysTest;
import com.gnol.springboot.common.dos.test1.Test1Test;

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

    @RequestMapping(value = "/test2")
    @ResponseBody
    public PageResult test2() {
        List<SysTest> listBy = sysTestService.listBy(null);
        return PageResult.ok(listBy);
    }

    @RequestMapping(value = "/test3")
    @ResponseBody
    @CurrDataSource(DataSourceType.SLAVE)
    public PageResult test3() {
        List<Test1Test> listBy = test1TestService.listBy(null);
        return PageResult.ok(listBy);
    }

    @RequestMapping(value = "/test4")
    @ResponseBody
    public PageResult test4() {
        PageData pd = this.getPageData();
        pd.put("sys_test", sysTestService.listBy(null));
        pd.put("test1_test", test1TestService.listByTest1(null));
        return PageResult.ok(pd);
    }

    @RequestMapping(value = "/test5")
    @ResponseBody
    public PageResult test5(String id) {
        SysTest test = new SysTest();
        test.setName("insert sys_test " + id);
        int insert = sysTestService.insert(test);
        return PageResult.ok("插入 " + insert + " 条");
    }

    @RequestMapping(value = "/test6")
    @ResponseBody
    @CurrDataSource(DataSourceType.SLAVE)
    public PageResult test6(String id) {
        Test1Test test = new Test1Test();
        test.setText("insert test1_test " + id);
        int insert = test1TestService.insert(test);
        return PageResult.ok("插入 " + insert + " 条");
    }

    @RequestMapping(value = "/test7")
    @ResponseBody
    public PageResult test7() {
        return sysTestService.insertTwoDataBase();
    }

}