package top.d7c.springboot.client.controllers.sys;

import java.io.FileNotFoundException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.d7c.fastDFS.springboot.autoconfigure.FastDFSService;
import top.d7c.mybatis.springboot.autoconfigure.CurrDataSource;
import top.d7c.mybatis.springboot.autoconfigure.DataSourceType;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.tools.lang.RandomNumber;
import top.d7c.springboot.client.controllers.WebBaseController;
import top.d7c.springboot.client.services.sys.SysTestService;
import top.d7c.springboot.client.services.test1.Test1TestService;
import top.d7c.springboot.common.configurations.ehcache.EhcacheService;
import top.d7c.springboot.common.dos.sys.SysTest;
import top.d7c.springboot.common.dos.test1.Test1Test;

/**
 * @Title: SysTestController
 * @Package: top.d7c.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年6月8日 下午3:15:37
 * @Description: d7c 系统测试 Controller
 */
@Controller
@RequestMapping(value = "/sys/test")
public class SysTestController extends WebBaseController {
    @Autowired
    private FastDFSService fastDFSService;
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
    /**
     * ehcache 缓存服务实现
     */
    @Autowired
    private EhcacheService ehcacheService;

    @RequestMapping(value = "/test1")
    public String test1(ModelMap map) throws FileNotFoundException {
        System.out.println(fastDFSService);
        /*File file = new File("E:\\Trash\\temp\\logo.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        String uploadFile = fastDFSService.uploadFile(fileInputStream, file.length(), "png", null);
        System.out.println(uploadFile);*/

        /*byte[] downloadFileByByte = fastDFSService
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

    /**
     * 启用事务后在同一个事务内不能分库 test7 会不能使用；
     * 启用事务后 test8 能使用，但事务不完整。
     */
    @RequestMapping(value = "/test8")
    @ResponseBody
    public PageResult test8() {
        int random = RandomNumber.getUnboundedInt();
        SysTest test = new SysTest();
        test.setName("insert two database by sys_test " + random);
        int insert = sysTestService.insert(test);

        // int a = 1 / 0;

        Test1Test test1 = new Test1Test();
        test1.setText("insert two database by test1_test " + random);
        int insert1 = test1TestService.insert1(test1);
        return PageResult.ok("sys_test：" + insert + ", test1_test：" + insert1 + ", random：" + random + "。");
    }

    @RequestMapping(value = "/test9")
    @ResponseBody
    public PageResult test9() {
        ehcacheService.addObject("abc", "ecd");
        return PageResult.ok(ehcacheService.getObject("abc"));
    }

}