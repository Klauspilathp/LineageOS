package com.gnol.springboot.client.controllers.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gnol.fastDFS.spring.boot.autoconfigure.FastDFSProperties;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.springboot.client.config.GnolProperties;
import com.gnol.springboot.client.controllers.WebBaseController;
import com.gnol.springboot.client.services.sys.SysImgService;
import com.gnol.springboot.common.dos.sys.SysImg;

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
     * gnol 系统自定义属性
     */
    @Resource(name = "gnolProperties")
    private GnolProperties gnolProperties;
    /**
     * fastDFS 属性配置
     */
    @Resource(name = "fastDFSProperties")
    private FastDFSProperties fastDFSProperties;
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

    /**
     * @Title: loginParam
     * @author: 吴佳隆
     * @data: 2020年7月14日 上午9:11:24
     * @Description: 登录页面参数
     * @return PageResult
     */
    @RequestMapping(value = "/loginParam", method = RequestMethod.GET)
    public PageResult loginParam() {
        PageData pd = this.getEmptyPageData();
        pd.put("SYSTEM_NAME", gnolProperties.getSystemName()); // 读取系统名称
        pd.put("FASTDFS_STORAGE_SERVERS", fastDFSProperties.getStorageServers()); // fastdfs 上图片访问地址
        // 系统登录背景图片
        pd.put(SysImg.M.imgType, "sys_loginImg");
        pd.put("listImg", sysImgService.listByImgType(pd));
        return PageResult.ok(pd);
    }

}