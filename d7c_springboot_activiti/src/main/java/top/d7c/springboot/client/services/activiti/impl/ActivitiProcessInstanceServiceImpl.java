package top.d7c.springboot.client.services.activiti.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.StringUtil;
import top.d7c.plugins.tools.idfactory.uuid.UUID;
import top.d7c.springboot.client.services.activiti.ActivitiProcessInstanceService;

/**
 * @Title: ActivitiProcessInstanceServiceImpl
 * @Package: top.d7c.springboot.client.services.activiti.impl
 * @author: 吴佳隆
 * @date: 2021年3月31日 下午12:52:07
 * @Description: activiti 流程实例操作服务实现
 */
@Service(value = "activitiProcessInstanceServiceImpl")
public class ActivitiProcessInstanceServiceImpl implements ActivitiProcessInstanceService {
    /**
     * 操作流程实例服务
     */
    @Autowired
    private ProcessRuntime processRuntime;
    /**
     * 提供对流程进行控制的服务
     */
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 提供对流程定义和部署存储库的访问服务
     */
    @Autowired
    private RepositoryService repositoryService;

    @Override
    public PageResult listProcessDefinition(Page<PageData> page) {
        org.activiti.api.runtime.shared.query.Page<org.activiti.api.process.model.ProcessInstance> processInstances = processRuntime
                .processInstances(Pageable.of(page.getCurrentResult(), page.getPageSize()));
        page.setTotalResult(processInstances.getTotalItems());

        List<PageData> pds = new ArrayList<PageData>();
        for (org.activiti.api.process.model.ProcessInstance processInstance : processInstances.getContent()) {
            pds.add(PageData.build().set("id", processInstance.getId()).set("name", processInstance.getName())
                    .set("startDate", processInstance.getStartDate()).set("initiator", processInstance.getInitiator())
                    .set("businessKey", processInstance.getBusinessKey())
                    .set("processDefinitionId", processInstance.getProcessDefinitionId())
                    .set("processDefinitionKey", processInstance.getProcessDefinitionKey())
                    .set("parentId", processInstance.getParentId())
                    .set("processDefinitionVersion", processInstance.getProcessDefinitionVersion()));
        }
        return PageResult.ok(pds).setPage(page);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult runProcessInstance(String processDefinitionKey, String businessKey,
            Map<String, Object> variables) {
        org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(processDefinitionKey, businessKey, variables);

        PageData pd = PageData.build().set("id", processInstance.getId()).set("name", processInstance.getName())
                .set("businessKey", processInstance.getBusinessKey())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey())
                .set("activityId", processInstance.getActivityId())
                .set("deploymentId", processInstance.getDeploymentId())
                .set("startTime", processInstance.getStartTime());

        return PageResult.ok(pd);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult startProcessInstance(String processDefinitionKey, String instanceName, String businessKey,
            Map<String, Object> variables) {
        if (StringUtil.isBlank(processDefinitionKey)) {
            return PageResult.error("processDefinitionKey 不能为空！");
        }
        if (StringUtil.isBlank(instanceName)) {
            instanceName = UUID.getUUID().nextStr();
        }
        org.activiti.api.process.model.ProcessInstance processInstance = processRuntime.start(
                ProcessPayloadBuilder.start().withProcessDefinitionKey(processDefinitionKey).withName(instanceName)
                        .withBusinessKey(businessKey).withVariable("variables", variables).build());

        PageData pd = PageData.build().set("id", processInstance.getId()).set("name", processInstance.getName())
                .set("startDate", processInstance.getStartDate()).set("initiator", processInstance.getInitiator())
                .set("businessKey", processInstance.getBusinessKey())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey());
        return PageResult.ok(pd);
    }

    // @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult deleteProcessInstance(String processDefinitionId) {
        org.activiti.api.process.model.ProcessInstance processInstance = processRuntime
                .delete(ProcessPayloadBuilder.delete().withProcessInstanceId(processDefinitionId).build());

        PageData pd = PageData.build().set("id", processInstance.getId()).set("name", processInstance.getName())
                .set("startDate", processInstance.getStartDate()).set("initiator", processInstance.getInitiator())
                .set("businessKey", processInstance.getBusinessKey())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey());
        return PageResult.ok(pd);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult suspendedProcessInstanceByProcessDefinitionIdAndDate(String processDefinitionId,
            Date suspensionDate) {
        if (suspensionDate == null || suspensionDate.before(new Date())) {
            suspensionDate = new Date();
        }

        repositoryService.suspendProcessDefinitionById(processDefinitionId, true, // true 挂起该流程定义下的所有流程实例
                suspensionDate);
        return PageResult.ok();
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult activateProcessInstanceByProcessDefinitionIdAndDate(String processDefinitionId,
            Date activationDate) {
        if (activationDate == null || activationDate.before(new Date())) {
            activationDate = new Date();
        }

        repositoryService.activateProcessDefinitionById(processDefinitionId, true, // true 激活该流程定义下的所有流程实例
                activationDate);
        return PageResult.ok();
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult suspendedProcessInstance(String processDefinitionId) {
        org.activiti.api.process.model.ProcessInstance processInstance = processRuntime
                .suspend(ProcessPayloadBuilder.suspend().withProcessInstanceId(processDefinitionId).build());

        PageData pd = PageData.build().set("id", processInstance.getId()).set("name", processInstance.getName())
                .set("startDate", processInstance.getStartDate()).set("initiator", processInstance.getInitiator())
                .set("businessKey", processInstance.getBusinessKey())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey());
        return PageResult.ok(pd);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult activateProcessInstance(String processDefinitionId) {
        // 底层用的是 runtimeService.activateProcessInstanceById(processInstanceId);
        org.activiti.api.process.model.ProcessInstance processInstance = processRuntime
                .resume(ProcessPayloadBuilder.resume().withProcessInstanceId(processDefinitionId).build());

        PageData pd = PageData.build().set("id", processInstance.getId()).set("name", processInstance.getName())
                .set("startDate", processInstance.getStartDate()).set("initiator", processInstance.getInitiator())
                .set("businessKey", processInstance.getBusinessKey())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey());
        return PageResult.ok(pd);
    }

    @Override
    public List<PageData> getVariables(String processInstanceId) {
        List<org.activiti.api.model.shared.model.VariableInstance> variables = processRuntime
                .variables(ProcessPayloadBuilder.variables().withProcessInstanceId(processInstanceId).build());

        List<PageData> pds = new ArrayList<PageData>();
        for (org.activiti.api.model.shared.model.VariableInstance variableInstance : variables) {
            pds.add(PageData.build().set("name", variableInstance.getName()).set("type", variableInstance.getType())
                    .set("processInstanceId", variableInstance.getProcessInstanceId())
                    .set("taskId", variableInstance.getTaskId())
                    .set("taskVariable", variableInstance.isTaskVariable()));
        }
        return pds;
    }

}