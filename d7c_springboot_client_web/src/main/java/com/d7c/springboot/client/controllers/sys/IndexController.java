package com.d7c.springboot.client.controllers.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.d7c.fastDFS.springboot.autoconfigure.FastDFSProperties;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.springboot.client.config.D7cProperties;
import com.d7c.springboot.client.controllers.WebBaseController;
import com.d7c.springboot.client.services.sys.SysImgService;
import com.d7c.springboot.common.dos.sys.SysImg;

/**
 * @Title: IndexController
 * @Package: com.d7c.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2019年06月17日 19:42:16
 * @Description: d7c 系统登录 Controller
 */
@Controller
public class IndexController extends WebBaseController {
    /**
     * d7c 系统自定义属性
     */
    @Resource(name = "d7cProperties")
    private D7cProperties d7cProperties;
    /**
     * fastDFS 属性配置
     */
    @Resource(name = "fastDFSProperties")
    private FastDFSProperties fastDFSProperties;
    /**
     * d7c 系统图片管理 Service 实现
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
    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }

    /**
     * @Title: loginParam
     * @author: 吴佳隆
     * @data: 2020年7月14日 上午9:11:24
     * @Description: 登录页面参数
     * @return PageResult
     */
    @GetMapping(value = "/loginParam")
    public PageResult loginParam() {
        PageData pd = this.getEmptyPageData();
        pd.put("SYSTEM_NAME", d7cProperties.getSystemName()); // 读取系统名称
        pd.put("FASTDFS_STORAGE_SERVERS", fastDFSProperties.getStorageServers()); // fastdfs 上图片访问地址
        // 系统登录背景图片
        pd.put(SysImg.M.imgType, "sys_loginImg");
        pd.put("listImg", sysImgService.listByImgType(pd));
        return PageResult.ok(pd);
    }

    /**
     * @Title: main
     * @author: 吴佳隆
     * @data: 2020年7月13日 下午6:51:43
     * @Description: 登录成功后去系统首页
     * @return String
     */
    @GetMapping(value = "/main")
    public String main() {
        return "sys/index/main";
    }

    /**
     * @Title: welcome
     * @author: 吴佳隆
     * @data: 2020年7月16日 上午8:53:47
     * @Description: 首页欢迎页面
     * @return String
     */
    @GetMapping(value = "/welcome")
    public String welcome() {
        return "sys/index/welcome";
    }

}