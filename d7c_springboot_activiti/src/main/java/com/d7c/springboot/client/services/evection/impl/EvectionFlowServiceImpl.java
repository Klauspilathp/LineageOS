package com.d7c.springboot.client.services.evection.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.api.task.model.Task;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.context.IdService;
import com.d7c.springboot.client.services.evection.EvectionFlowService;

/**
 * @Title: EvectionFlowServiceImpl
 * @Package: com.d7c.springboot.client.services.evection.impl
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:48:57
 * @Description: 出差流程服务实现
 */
@Service(value = "evectionFlowServiceImpl")
public class EvectionFlowServiceImpl implements EvectionFlowService {
    private static final Logger logger = LoggerFactory.getLogger(EvectionFlowServiceImpl.class);
    public static final String PROCESS_DEFINITION_KEY = "evectionProcess";
    /**
     * ID 生成服务
     */
    @Resource(name = "dbIdServiceImpl")
    private IdService idService;
    /**
     * 提供对流程进行控制的服务
     */
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 提供访问 {@link Task} 和表单相关操作的服务。
     */
    @Autowired
    private TaskService taskService;
    /**
     * 提供对流程历史记录存储库的访问服务
     */
    @Autowired
    private HistoryService historyService;

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult applyEvection(PageData pd) {
        String applyUserId = pd.getString("applyUserId");
        if (StringUtil.isBlank(applyUserId)) {
            return PageResult.error("applyUserId 不能为空！");
        }
        String businessKey = pd.getString("businessKey");
        if (StringUtil.isBlank(businessKey)) {
            businessKey = String.valueOf(idService.getLong(PROCESS_DEFINITION_KEY));
        }

        Map<String, Object> variables = new HashMap<String, Object>();

        // 启动出差流程实例
        org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(PROCESS_DEFINITION_KEY, businessKey, variables);

        // 查询当前任务
        org.activiti.engine.task.Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey)
                .singleResult();

        taskService.claim(task.getId(), applyUserId);

        // 向出差流程表中插入业务数据
        String processDefinitionId = processInstance.getProcessDefinitionId();

        logger.debug("出差申请的流程定义 ID : {}", processDefinitionId);
        return PageResult.ok();
    }

    @Override
    public PageResult pmApprove(PageData pd) {
        org.activiti.engine.task.Task task = null;
        String taskId = pd.getString("taskId");
        if (StringUtil.isNotBlank(taskId)) {
            // task = taskService.createTaskQuery().taskId(taskId).singleResult();
        }
        String businessKey = pd.getString("businessKey");
        if (StringUtil.isNotBlank(businessKey)) {
            // task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
        }
        String pmUserId = pd.getString("pmUserId");
        if (StringUtil.isNotBlank(pmUserId)) {
            // task = taskService.createTaskQuery().taskCandidateUser(pmUserId).singleResult();
        }
        if (StringUtil.isBlank(pmUserId)) {
            return PageResult.error("pmUserId 不能为空！");
        }
        task = taskService.createTaskQuery().taskId(taskId).taskCandidateUser(pmUserId).singleResult();
        if (task == null) {
            return PageResult.error("任务不存在！");
        }

        // 拾取任务
        taskService.claim(task.getId(), pmUserId);

        // 完成任务
        taskService.complete(task.getId());

        // 更新出差流程表

        return PageResult.ok();
    }

    @Override
    public PageResult gmApprove(PageData pd) {
        org.activiti.engine.task.Task task = null;
        String taskId = pd.getString("taskId");
        if (StringUtil.isNotBlank(taskId)) {
            // task = taskService.createTaskQuery().taskId(taskId).singleResult();
        }
        String businessKey = pd.getString("businessKey");
        if (StringUtil.isNotBlank(businessKey)) {
            // task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
        }
        String gmUserId = pd.getString("gmUserId");
        if (StringUtil.isNotBlank(gmUserId)) {
            // task = taskService.createTaskQuery().taskCandidateUser(gmUserId).singleResult();
        }
        if (StringUtil.isBlank(gmUserId)) {
            return PageResult.error("gmUserId 不能为空！");
        }
        task = taskService.createTaskQuery().taskId(taskId).taskCandidateUser(gmUserId).singleResult();
        if (task == null) {
            return PageResult.error("任务不存在！");
        }

        // 拾取任务
        taskService.claim(task.getId(), gmUserId);

        // 完成任务
        taskService.complete(task.getId());

        // 更新出差流程表

        return PageResult.ok();
    }

    @Override
    public PageResult personnelReport(PageData pd) {
        org.activiti.engine.task.Task task = null;
        String taskId = pd.getString("taskId");
        if (StringUtil.isNotBlank(taskId)) {
            // task = taskService.createTaskQuery().taskId(taskId).singleResult();
        }
        String businessKey = pd.getString("businessKey");
        if (StringUtil.isNotBlank(businessKey)) {
            // task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
        }
        String personnelUserId = pd.getString("personnelUserId");
        if (StringUtil.isNotBlank(personnelUserId)) {
            // task = taskService.createTaskQuery().taskCandidateUser(personnelUserId).singleResult();
        }
        if (StringUtil.isBlank(personnelUserId)) {
            return PageResult.error("personnelUserId 不能为空！");
        }
        task = taskService.createTaskQuery().taskId(taskId).taskCandidateUser(personnelUserId).singleResult();
        if (task == null) {
            return PageResult.error("任务不存在！");
        }

        // 拾取任务
        taskService.claim(task.getId(), personnelUserId);

        // 完成任务
        taskService.complete(task.getId());

        // 更新出差流程表

        return PageResult.ok();
    }

    @Override
    public PageResult endEvection(PageData pd) {
        org.activiti.engine.task.Task task = null;
        String taskId = pd.getString("taskId");
        if (StringUtil.isNotBlank(taskId)) {
            // task = taskService.createTaskQuery().taskId(taskId).singleResult();
        }
        String businessKey = pd.getString("businessKey");
        if (StringUtil.isNotBlank(businessKey)) {
            // task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
        }
        String personnelUserId = pd.getString("personnelUserId");
        if (StringUtil.isNotBlank(personnelUserId)) {
            // task = taskService.createTaskQuery().taskCandidateUser(personnelUserId).singleResult();
        }
        if (StringUtil.isBlank(personnelUserId)) {
            return PageResult.error("personnelUserId 不能为空！");
        }
        task = taskService.createTaskQuery().taskId(taskId).taskCandidateUser(personnelUserId).singleResult();
        if (task == null) {
            return PageResult.error("任务不存在！");
        }

        // 拾取任务
        taskService.claim(task.getId(), personnelUserId);

        // 完成任务
        taskService.complete(task.getId());

        // 更新出差流程表

        return PageResult.ok();
    }

    @Override
    public PageResult getFlowState(PageData pd) {
        String taskId = pd.getString("taskId");
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }
        org.activiti.engine.task.Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return PageResult.ok(PageData.build().set("status", "finish"));
        }
        return PageResult.ok(PageData.build().set("status", "processing").set("taskId", task.getId())
                .set("taskName", task.getName()).set("assignee", task.getAssignee()));
    }

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        return PageResult.ok(null).setPage(page);
    }

    @Override
    public PageResult listEvectionHistoryFlow(PageData pd) {
        String processInstanceId = pd.getString("processInstanceId");
        if (StringUtil.isBlank(processInstanceId)) {
            return PageResult.error("processInstanceId 不能为空！");
        }

        // 查询条件
        org.activiti.engine.history.HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService
                .createHistoricActivityInstanceQuery().processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceEndTime().desc();

        // 查询结果
        List<org.activiti.engine.history.HistoricActivityInstance> historicActivityInstances = historicActivityInstanceQuery
                .list();

        // 组装结果
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

}