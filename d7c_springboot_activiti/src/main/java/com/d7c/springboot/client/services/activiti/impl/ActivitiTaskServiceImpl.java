package com.d7c.springboot.client.services.activiti.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.services.activiti.ActivitiTaskService;

/**
 * @Title: ActivitiTaskServiceImpl
 * @Package: com.d7c.springboot.client.services.activiti.impl
 * @author: 吴佳隆
 * @date: 2021年3月31日 下午2:36:44
 * @Description: activiti 流程任务操作服务实现
 */
@Service(value = "activitiTaskServiceImpl")
public class ActivitiTaskServiceImpl implements ActivitiTaskService {
    /**
     * 提供与用户集成的流程任务存储库操作、访问的服务，内部实现依赖 {@link TaskService}，访问时用户需要拥有 ACTIVITI_USER 角色的权限。
     */
    @Autowired
    private TaskRuntime taskRuntime;
    /**
     * 提供访问 {@link Task} 和表单相关操作的服务。
     */
    @Autowired
    private TaskService taskService;

    // @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult deleteCandidateUser(String taskId, String candidateUser) {
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }
        if (StringUtil.isBlank(candidateUser)) {
            return PageResult.error("candidateUser 不能为空！");
        }

        taskService.deleteCandidateUser(taskId, candidateUser);
        return PageResult.ok();
    }

    // @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult addCandidateUser(String taskId, String candidateUser) {
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }
        if (StringUtil.isBlank(candidateUser)) {
            return PageResult.error("candidateUser 不能为空！");
        }
        taskService.addCandidateUser(taskId, candidateUser);
        return PageResult.ok();
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult releaseByCandidateUser(String taskId, String candidateUser) {
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }
        if (StringUtil.isBlank(candidateUser)) {
            return PageResult.error("candidateUser 不能为空！");
        }

        // 归还当前登录人的指定任务，当前登录人信息从 {@link SecurityManager} 中获取
        // Task task = taskRuntime.release(TaskPayloadBuilder.release().withTaskId(taskId).build());
        org.activiti.engine.task.Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(candidateUser)
                .singleResult();
        if (task == null) {
            return PageResult.error("任务不存在！");
        }
        if (StringUtil.isNotBlank(task.getAssignee())) {
            return PageResult.error("任务没有被领取！");
        }

        // 归还任务
        taskService.setAssignee(taskId, null);
        return PageResult.ok();
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult claimByCandidateUser(String taskId, String candidateUser) {
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }
        if (StringUtil.isBlank(candidateUser)) {
            return PageResult.error("candidateUser 不能为空！");
        }

        Task task = taskRuntime.task(taskId);
        if (task == null) {
            return PageResult.error("任务不存在！");
        }
        if (StringUtil.isNotBlank(task.getAssignee())) {
            return PageResult.error("任务已被领取！");
        }

        // 拾取任务
        taskService.claim(taskId, candidateUser);
        return PageResult.ok();
    }

    @Override
    public List<PageData> listTaskByCandidateUser(String candidateUser) {
        if (StringUtil.isBlank(candidateUser)) {
            return null;
        }
        List<org.activiti.engine.task.Task> tasks = taskService.createTaskQuery().taskCandidateUser(candidateUser)
                .list();

        List<PageData> pds = new ArrayList<PageData>();
        for (org.activiti.engine.task.Task task : tasks) {
            pds.add(PageData.build().set("id", task.getId()).set("name", task.getName())
                    .set("description", task.getDescription()).set("priority", task.getPriority())
                    .set("owner", task.getOwner()).set("assignee", task.getAssignee())
                    .set("processInstanceId", task.getProcessInstanceId()).set("executionId", task.getExecutionId())
                    .set("processDefinitionId", task.getProcessDefinitionId()).set("createTime", task.getCreateTime())
                    .set("taskDefinitionKey", task.getTaskDefinitionKey()).set("dueDate", task.getDueDate())
                    .set("category", task.getCategory()).set("parentTaskId", task.getParentTaskId())
                    .set("tenantId", task.getTenantId()).set("formKey", task.getFormKey())
                    .set("taskLocalVariables", task.getTaskLocalVariables())
                    .set("processVariables", task.getProcessVariables()).set("claimTime", task.getClaimTime())
                    .set("businessKey", task.getBusinessKey()));
        }
        return pds;
    }

    @Override
    public List<PageData> listTaskByKey(String taskDefinitionKey) {
        if (StringUtil.isBlank(taskDefinitionKey)) {
            return null;
        }
        List<org.activiti.engine.task.Task> tasks = taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey)
                .orderByTaskCreateTime().desc().list();

        List<PageData> pds = new ArrayList<PageData>();
        for (org.activiti.engine.task.Task task : tasks) {
            pds.add(PageData.build().set("id", task.getId()).set("name", task.getName())
                    .set("description", task.getDescription()).set("priority", task.getPriority())
                    .set("owner", task.getOwner()).set("assignee", task.getAssignee())
                    .set("processInstanceId", task.getProcessInstanceId()).set("executionId", task.getExecutionId())
                    .set("processDefinitionId", task.getProcessDefinitionId()).set("createTime", task.getCreateTime())
                    .set("taskDefinitionKey", task.getTaskDefinitionKey()).set("dueDate", task.getDueDate())
                    .set("category", task.getCategory()).set("parentTaskId", task.getParentTaskId())
                    .set("tenantId", task.getTenantId()).set("formKey", task.getFormKey())
                    .set("taskLocalVariables", task.getTaskLocalVariables())
                    .set("processVariables", task.getProcessVariables()).set("claimTime", task.getClaimTime())
                    .set("businessKey", task.getBusinessKey()));
        }
        return pds;
    }

    @Override
    public PageResult listTasks(Page<PageData> page) {
        org.activiti.api.runtime.shared.query.Page<Task> tasks = taskRuntime
                .tasks(Pageable.of(page.getCurrentResult(), page.getPageSize()));
        page.setTotalResult(tasks.getTotalItems());

        List<PageData> pds = new ArrayList<PageData>();
        for (org.activiti.api.task.model.Task task : tasks.getContent()) {
            pds.add(PageData.build().set("id", task.getId()).set("owner", task.getOwner())
                    .set("assignee", task.getAssignee()).set("name", task.getName())
                    .set("description", task.getDescription()).set("createdDate", task.getCreatedDate())
                    .set("claimedDate", task.getClaimedDate()).set("dueDate", task.getDueDate())
                    .set("priority", task.getPriority()).set("processDefinitionId", task.getProcessDefinitionId())
                    .set("processInstanceId", task.getProcessInstanceId()).set("parentTaskId", task.getParentTaskId())
                    .set("formKey", task.getFormKey()).set("completedDate", task.getCompletedDate())
                    .set("duration", task.getDuration())
                    .set("processDefinitionVersion", task.getProcessDefinitionVersion())
                    .set("businessKey", task.getBusinessKey()).set("standalone", task.isStandalone())
                    .set("taskDefinitionKey", task.getTaskDefinitionKey()));
        }
        return PageResult.ok(pds).setPage(page);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult completeTask(String taskId) {
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }

        org.activiti.api.task.model.Task task = taskRuntime.task(taskId);
        if (task == null) {
            return PageResult.error("任务不存在！");
        }
        if (StringUtil.isNotBlank(task.getAssignee())) {
            return PageResult.error("任务已被领取！");
        }

        // 拾取任务
        taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());

        // 完成任务
        org.activiti.api.task.model.Task complete = taskRuntime
                .complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());

        return PageResult.ok(PageData.build().set("id", complete.getId()).set("owner", complete.getOwner())
                .set("assignee", complete.getAssignee()).set("name", complete.getName())
                .set("description", complete.getDescription()).set("createdDate", complete.getCreatedDate())
                .set("claimedDate", complete.getClaimedDate()).set("dueDate", complete.getDueDate())
                .set("priority", complete.getPriority()).set("processDefinitionId", complete.getProcessDefinitionId())
                .set("processInstanceId", complete.getProcessInstanceId())
                .set("parentTaskId", complete.getParentTaskId()).set("formKey", complete.getFormKey())
                .set("completedDate", complete.getCompletedDate()).set("duration", complete.getDuration())
                .set("processDefinitionVersion", complete.getProcessDefinitionVersion())
                .set("businessKey", complete.getBusinessKey()).set("standalone", complete.isStandalone())
                .set("taskDefinitionKey", complete.getTaskDefinitionKey()));
    }

}