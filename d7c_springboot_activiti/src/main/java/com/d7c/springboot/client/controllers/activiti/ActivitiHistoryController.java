package com.d7c.springboot.client.controllers.activiti;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @Title: listHistoricActivityInstanceById
     * @author: 吴佳隆
     * @data: 2021年1月21日 上午8:47:31
     * @Description: 根据流程实例 ID 或流程定义 ID 查询历史信息。
     * @param processInstanceId
     * @param processDefinitionId
     * @return PageResult
     */
    @GetMapping(value = "/listHistoricActivityInstanceById")
    public PageResult listHistoricActivityInstanceById(@RequestParam("processInstanceId") String processInstanceId,
            @RequestParam("processDefinitionId") String processDefinitionId) {
        if (StringUtil.isBlank(processInstanceId) && StringUtil.isBlank(processDefinitionId)) {
            return PageResult.error("请传入流程实例 ID 或流程定义 ID！");
        }
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService
                .createHistoricActivityInstanceQuery();
        if (StringUtil.isNotBlank(processInstanceId)) {
            historicActivityInstanceQuery.processInstanceId(processInstanceId);
        } else {
            historicActivityInstanceQuery.processDefinitionId(processDefinitionId);
        }
        historicActivityInstanceQuery.orderByHistoricActivityInstanceEndTime().desc();
        List<org.activiti.engine.history.HistoricActivityInstance> historicActivityInstances = historicActivityInstanceQuery
                .list();

        List<PageData> pds = new ArrayList<PageData>();
        for (org.activiti.engine.history.HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            pds.add(PageData.build().set("id", historicActivityInstance.getId())
                    .set("activityId", historicActivityInstance.getActivityId())
                    .set("activityName", historicActivityInstance.getActivityName())
                    .set("activityType", historicActivityInstance.getActivityType())
                    .set("processDefinitionId", historicActivityInstance.getProcessDefinitionId())
                    .set("processInstanceId", historicActivityInstance.getProcessInstanceId())
                    .set("executionId", historicActivityInstance.getExecutionId())
                    .set("taskId", historicActivityInstance.getTaskId())
                    .set("calledProcessInstanceId", historicActivityInstance.getCalledProcessInstanceId())
                    .set("assignee", historicActivityInstance.getAssignee())
                    .set("startTime", historicActivityInstance.getStartTime())
                    .set("endTime", historicActivityInstance.getEndTime())
                    .set("durationInMillis", historicActivityInstance.getDurationInMillis())
                    .set("deleteReason", historicActivityInstance.getDeleteReason())
                    .set("tenantId", historicActivityInstance.getTenantId()));
        }
        return PageResult.ok(pds);
    }

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
        List<org.activiti.engine.history.HistoricTaskInstance> historicTaskInstances = historyService
                .createHistoricTaskInstanceQuery().taskAssignee(assignee) // 代理人
                .processInstanceId(pd.getString("processInstanceId")) // 流程实例 ID
                .orderByHistoricTaskInstanceEndTime().desc().listPage(page.getCurrentResult(), page.getPageSize());

        List<PageData> pds = new ArrayList<PageData>();
        for (org.activiti.engine.history.HistoricTaskInstance historicActivityInstance : historicTaskInstances) {
            pds.add(PageData.build().set("id", historicActivityInstance.getId())
                    .set("name", historicActivityInstance.getName())
                    .set("description", historicActivityInstance.getDescription())
                    .set("priority", historicActivityInstance.getPriority())
                    .set("owner", historicActivityInstance.getOwner())
                    .set("assignee", historicActivityInstance.getAssignee())
                    .set("processInstanceId", historicActivityInstance.getProcessInstanceId())
                    .set("executionId", historicActivityInstance.getExecutionId())
                    .set("processDefinitionId", historicActivityInstance.getProcessDefinitionId())
                    .set("createTime", historicActivityInstance.getCreateTime())
                    .set("taskDefinitionKey", historicActivityInstance.getTaskDefinitionKey())
                    .set("dueDate", historicActivityInstance.getDueDate())
                    .set("category", historicActivityInstance.getCategory())
                    .set("parentTaskId", historicActivityInstance.getParentTaskId())
                    .set("tenantId", historicActivityInstance.getTenantId())
                    .set("formKey", historicActivityInstance.getFormKey())
                    .set("taskLocalVariables", historicActivityInstance.getTaskLocalVariables())
                    .set("processVariables", historicActivityInstance.getProcessVariables())
                    .set("claimTime", historicActivityInstance.getClaimTime())
                    .set("businessKey", historicActivityInstance.getBusinessKey())
                    .set("deleteReason", historicActivityInstance.getDeleteReason())
                    .set("startTime", historicActivityInstance.getStartTime())
                    .set("endTime", historicActivityInstance.getEndTime())
                    .set("durationInMillis", historicActivityInstance.getDurationInMillis())
                    .set("workTimeInMillis", historicActivityInstance.getWorkTimeInMillis())
                    .set("claimTime", historicActivityInstance.getClaimTime()));
        }
        return PageResult.ok(pds);
    }

}