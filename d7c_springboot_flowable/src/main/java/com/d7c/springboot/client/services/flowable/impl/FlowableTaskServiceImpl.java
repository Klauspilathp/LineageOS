package com.d7c.springboot.client.services.flowable.impl;

import java.util.ArrayList;
import java.util.List;

import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.services.flowable.FlowableTaskService;

/**
 * @Title: FlowableTaskServiceImpl
 * @Package: com.d7c.springboot.client.services.flowable.impl
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:52:56
 * @Description: flowable 流程任务操作服务实现
 */
@Service(value = "flowableTaskServiceImpl")
public class FlowableTaskServiceImpl implements FlowableTaskService {
    /**
     * 操作流程任务服务
     */
    @Autowired
    private TaskService taskService;

    @Override
    public List<PageData> listTask(String deploymentId, String userIdForCandidateAndAssignee) {
        TaskQuery taskQuery = taskService.createTaskQuery();

        if (StringUtil.isNotBlank(deploymentId)) {
            taskQuery.deploymentId(deploymentId);
        }
        if (StringUtil.isNotBlank(userIdForCandidateAndAssignee)) {
            taskQuery.taskCandidateOrAssigned(userIdForCandidateAndAssignee);
        }

        List<Task> tasks = taskQuery.orderByTaskCreateTime().asc().list();

        List<PageData> pds = new ArrayList<PageData>();
        tasks.stream().forEach(task -> {
            pds.add(PageData.build().set("taskId", task.getId()) // 任务 ID
                    .set("taskName", task.getName()) // 任务名称
                    .set("assignee", task.getAssignee()) // 任务接收人
                    .set("claimTime", task.getClaimTime()) // 完成时间
                    .set("createTime", task.getCreateTime()) // 创建时间
                    .set("processDefinitionId", task.getProcessDefinitionId()) // 流程部署 ID
                    .set("processInstanceId", task.getProcessInstanceId()) // 流程实例 ID 
            );
        });

        return pds;
    }

    @Override
    public PageResult listRunTask(String processInstanceId) {
        if (StringUtil.isBlank(processInstanceId)) {
            return PageResult.error("processInstanceId 不能为空！");
        }

        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).orderByTaskCreateTime()
                .asc().list();

        List<PageData> pds = new ArrayList<PageData>();
        tasks.stream().forEach(task -> {
            pds.add(PageData.build().set("taskId", task.getId()) // 任务 ID
                    .set("taskName", task.getName()) // 任务名称
                    .set("assignee", task.getAssignee()) // 任务接收人
                    .set("claimTime", task.getClaimTime()) // 完成时间
                    .set("createTime", task.getCreateTime()) // 创建时间
                    .set("processDefinitionId", task.getProcessDefinitionId()) // 流程部署 ID
                    .set("processInstanceId", task.getProcessInstanceId()) // 流程实例 ID 
            );
        });

        return PageResult.ok(pds);
    }

}
