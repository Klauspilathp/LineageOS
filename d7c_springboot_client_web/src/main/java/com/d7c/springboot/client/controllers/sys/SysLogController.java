package com.d7c.springboot.client.controllers.sys;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.controllers.WebBaseController;
import com.d7c.springboot.client.services.sys.SysLogService;

/**
 * @Title: SysLogController
 * @Package: com.d7c.springboot.client.controllers.sys
 * @author: 吴佳隆
 * @date: 2020年7月19日 下午1:40:56
 * @Description: d7c 系统日志 Controller
 */
public class SysLogController extends WebBaseController {
    /**
     * d7c 系统日志 Service
     */
    @Resource(name = "sysLogServiceImpl")
    private SysLogService sysLogService;

    /**
     * @Title: index
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午1:44:16
     * @Description: 日志管理首页
     * @return String
     */
    @GetMapping(value = "/index")
    @RolesAllowed("sys_log:index")
    public String index() {
        return "sys/log/log_index";
    }

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午1:45:25
     * @Description: 日志列表查询
     * @param page
     * @return PageResult
     */
    @RequestMapping(value = "/listPDPage")
    @RolesAllowed("sys_log:index")
    @ResponseBody
    public PageResult listPDPage(Page<PageData> page) {
        PageData pd = this.getPageData();
        page.setArgs(pd);
        // 查询日志列表
        return sysLogService.listPDPage(page);
    }

    /**
     * @Title: del
     * @author: 吴佳隆
     * @data: 2020年7月19日 下午1:45:30
     * @Description: 删除系统日志
     * @return PageResult
     */
    @RequestMapping(value = "/del")
    @RolesAllowed("sys_log:del")
    @ResponseBody
    public PageResult del() {
        PageData pd = this.getPageData();
        int count = sysLogService.deleteByKey(StringUtil.toLong(pd.get("logId")));
        PageResult result = null;
        if (count == 1) {
            result = PageResult.ok();
        } else {
            result = PageResult.error("日志删除失败！");
        }
        return result;
    }

}