package com.d7c.springboot.client.services.activiti.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d7c.oauth2.spring.boot.SecurityUtil;
import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.services.activiti.ActivitiHistoryService;

/**
 * @Title: ActivitiHistoryServiceImpl
 * @Package: com.d7c.springboot.client.services.activiti.impl
 * @author: 吴佳隆
 * @date: 2021年3月31日 下午2:57:37
 * @Description: activiti 流程历史记录的操作服务实现
 */
@Service(value = "activitiHistoryServiceImpl")
public class ActivitiHistoryServiceImpl implements ActivitiHistoryService {
    /**
     * 提供对流程历史记录存储库的访问服务
     */
    @Autowired
    private HistoryService historyService;

    @Override
    public List<PageData> listHistoricActivityInstanceById(String processInstanceId, String processDefinitionId) {
        if (StringUtil.isBlank(processInstanceId) && StringUtil.isBlank(processDefinitionId)) {
            return null;
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
        return pds;
    }

    @Override
    public PageResult listHistoricTaskInstanceByAssignee(Page<PageData> page) {
        PageData pd = page.getArgs();
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
        return PageResult.ok(pds).setPage(page);
    }

}