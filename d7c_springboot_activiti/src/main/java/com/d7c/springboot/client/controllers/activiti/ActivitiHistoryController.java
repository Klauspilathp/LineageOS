package com.d7c.springboot.client.controllers.activiti;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.oauth2.spring.boot.SecurityUtil;
import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.controllers.WebBaseController;

/**
 * @Title: ActivitiHistoryController
 * @Package: com.d7c.springboot.client.controllers.activiti
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: activiti 流程历史记录的操作控制类
 */
@RestController
@RequestMapping(value = "/activiti/history")
public class ActivitiHistoryController extends WebBaseController {
    /**
     * 提供对流程历史记录存储库的访问服务
     */
    @Autowired
    private HistoryService historyService;

    /**
     * @Title: listHistoricTaskInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午3:10:58
     * @Description: 分页查询任务代理人的历史代理任务实例
     * @param page
     * @param assignee      代理人，如果不传，默认查当前登录人所完成的历史任务实例。
     * @return PageResult
     */
    @GetMapping(value = "/listHistoricTaskInstanceByAssignee")
    public PageResult listHistoricTaskInstanceByAssignee(Page<PageData> page) {
        PageData pd = this.getPageData();
        // 代理人，如果不传，默认查当前登录人所完成的历史任务实例。
        String assignee = pd.getString("assignee");
        if (StringUtil.isBlank(assignee)) {
            assignee = SecurityUtil.getUserId().toString();
        }

        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(assignee) // 代理人
                .processInstanceId(pd.getString("processInstanceId")) // 流程实例 ID
                .orderByHistoricTaskInstanceEndTime().desc().listPage(page.getCurrentResult(), page.getPageSize());
        return PageResult.ok(historicTaskInstances);
    }

}