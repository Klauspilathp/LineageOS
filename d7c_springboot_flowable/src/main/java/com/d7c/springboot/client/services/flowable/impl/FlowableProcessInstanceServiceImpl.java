package com.d7c.springboot.client.services.flowable.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.services.flowable.FlowableProcessInstanceService;

/**
 * @Title: FlowableProcessInstanceServiceImpl
 * @Package: com.d7c.springboot.client.services.flowable.impl
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:51:02
 * @Description: flowable 流程实例操作服务实现
 */
@Service(value = "flowableProcessInstanceServiceImpl")
public class FlowableProcessInstanceServiceImpl implements FlowableProcessInstanceService {
    /**
     * 操作流程实例服务
     */
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 提供对流程定义和部署存储库的访问服务
     */
    @Autowired
    private RepositoryService repositoryService;
    /**
     * 操作流程任务服务
     */
    @Autowired
    private TaskService taskService;
    /**
     * 查询历史表数据服务
     */
    @Autowired
    private HistoryService historyService;

    /**
     * 流程表单操作服务，提供了一系列获取已经定义的表单信息、启动表单实例、渲染表单内容等功能。
     * 表单定义仅支持开始节点（流程启动时填写的表单信息）和任务节点（任务流转填写或修改的表单信息）上设置表单的动态内容。
     * 表单有三种方式：
     *    动态表单：只定义表单中字段的一些配置信息，比如字段的可读、可写、是否必须等属性信息，不能定义完成的表单页面。
     *    外置表单：只定义表单的 KEY，表单的 Value 自已维护，适合自己维护的表单页面。
     *    内置表单：使用内置的表单定义及渲染引擎。
     */
    /*@Autowired
    private FormService formService;*/

    @Override
    public PageResult listProcessInstance(Page<PageData> page) {
        if (page == null) {
            return PageResult.error("分页对象不能为空！");
        }

        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        PageData args = page.getArgs();
        if (args != null && !args.isEmpty()) {
            String deploymentId = args.getString("deploymentId");
            if (StringUtil.isNotBlank(deploymentId)) {
                processInstanceQuery.deploymentId(deploymentId);
            }
        }

        long count = processInstanceQuery.count();
        int totalResult = (int) (count > Integer.MAX_VALUE ? Integer.MAX_VALUE : count);
        page.setTotalResult(totalResult);

        List<PageData> pds = null;
        if (count > 0) {
            pds = new ArrayList<PageData>();
            List<ProcessInstance> processInstances = processInstanceQuery.orderByStartTime().desc()
                    .listPage(page.getCurrentResult(), page.getPageSize());
            for (ProcessInstance processInstance : processInstances) {
                pds.add(PageData.build().set("processInstanceId", processInstance.getId()) // 流程实例 ID
                        .set("processDefinitionId", processInstance.getProcessDefinitionId())
                        .set("processDefinitionName", processInstance.getProcessDefinitionName()));
            }
        }

        return PageResult.ok(pds).setPage(page);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult startProcessInstanceById(String processDefinitionId, String businessKey,
            Map<String, Object> variables) {
        if (StringUtil.isBlank(processDefinitionId)) {
            return PageResult.error("processDefinitionId 不能为空！");
        }

        boolean isBusinessKeyBlank = StringUtil.isBlank(businessKey);

        ProcessInstance processInstance = null;
        if (variables == null || variables.isEmpty()) {
            if (isBusinessKeyBlank) {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
            } else {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey);
            }
        } else {
            if (isBusinessKeyBlank) {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
            } else {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
            }
        }

        if (processInstance == null) {
            return PageResult.error("流程实例不存在！");
        }

        PageData pd = PageData.build().set("processInstanceId", processInstance.getId())
                .set("name", processInstance.getName())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey())
                .set("startTime", processInstance.getStartTime()).set("startUserId", processInstance.getStartUserId());

        return PageResult.ok(pd);
    }

    @Override
    public PageResult deleteProcessInstance(String processInstanceId, String deleteReason) {
        if (StringUtil.isBlank(processInstanceId)) {
            return PageResult.error("processInstanceId 不能为空！");
        }

        runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
        return PageResult.ok();
    }

    @Override
    public InputStream getProcessDiagramInputStream(String processInstanceId) {
        if (StringUtil.isNotBlank(processInstanceId)) {
            return null;
        }

        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = null;
        List<String> highLightedActivities = new ArrayList<String>();
        if (task == null) {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            if (historicProcessInstance != null) {
                processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            }
        } else {
            processDefinitionId = task.getProcessDefinitionId();
            highLightedActivities.add(task.getTaskDefinitionKey());
        }

        if (StringUtil.isBlank(processDefinitionId)) {
            return null;
        }

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        if (bpmnModel == null) {
            return null;
        }

        DefaultProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();

        return processDiagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivities, false);
    }

}
