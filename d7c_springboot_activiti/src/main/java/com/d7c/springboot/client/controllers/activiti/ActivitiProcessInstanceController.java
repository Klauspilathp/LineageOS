package com.d7c.springboot.client.controllers.activiti;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.tools.idfactory.uuid.UUID;
import com.d7c.plugins.tools.json.SFJsonUtil;
import com.d7c.springboot.client.controllers.WebBaseController;

/**
 * @Title: ActivitiProcessInstanceController
 * @Package: com.d7c.springboot.client.controllers.activiti
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: activiti 流程实例操作控制类
 */
@RestController
@RequestMapping(value = "/activiti/deployment")
public class ActivitiProcessInstanceController extends WebBaseController {
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

    /**
     * @Title: listProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:25:14
     * @Description: 分页查询流程实例列表
     * @param page
     * @return PageResult
     */
    @GetMapping(value = "/listProcessInstance")
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

    /**
     * @Title: runProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:33:54
     * @Description: 启动流程实例
     * @param processDefinitionKey  流程实例 Key
     * @param businessKey           业务编号
     * @param instanceVariable      流程实例参数
     * @return PageResult
     */
    @PostMapping(value = "/runProcessInstance")
    public PageResult runProcessInstance(@RequestParam(value = "processDefinitionKey") String processDefinitionKey,
            @RequestParam(value = "businessKey") String businessKey,
            @RequestParam(value = "variables", required = false) String variables) {
        org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                processDefinitionKey, businessKey,
                StringUtil.isBlank(variables) ? null : SFJsonUtil.jsonToMap(variables));

        PageData pd = PageData.build().set("id", processInstance.getId()).set("name", processInstance.getName())
                .set("businessKey", processInstance.getBusinessKey())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey())
                .set("activityId", processInstance.getActivityId())
                .set("deploymentId", processInstance.getDeploymentId())
                .set("startTime", processInstance.getStartTime());
        return PageResult.ok(pd);
    }

    /**
     * @Title: startProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:33:54
     * @Description: 启动流程实例，需要用户具有 ROLE_ACTIVITI_ADMIN 角色。
     * @param processDefinitionKey  流程实例 Key
     * @param instanceName          流程实例名称
     * @param businessKey           业务编号
     * @param instanceVariable      流程实例参数
     * @return PageResult
     */
    @PostMapping(value = "/startProcessInstance")
    public PageResult startProcessInstance(@RequestParam(value = "processDefinitionKey") String processDefinitionKey,
            @RequestParam(value = "instanceName", required = false) String instanceName,
            @RequestParam(value = "businessKey") String businessKey,
            @RequestParam(value = "variables", required = false) String variables) {
        if (StringUtil.isBlank(processDefinitionKey)) {
            return PageResult.error("processDefinitionKey 不能为空！");
        }
        if (StringUtil.isBlank(instanceName)) {
            instanceName = UUID.getUUID().nextStr();
        }
        org.activiti.api.process.model.ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start().withProcessDefinitionKey(processDefinitionKey).withName(instanceName)
                .withBusinessKey(businessKey)
                .withVariable("variables", StringUtil.isBlank(variables) ? null : SFJsonUtil.jsonToMap(variables))
                .build());

        PageData pd = PageData.build().set("id", processInstance.getId()).set("name", processInstance.getName())
                .set("startDate", processInstance.getStartDate()).set("initiator", processInstance.getInitiator())
                .set("businessKey", processInstance.getBusinessKey())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey());
        return PageResult.ok(pd);
    }

    /**
     * @Title: deleteProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:41:21
     * @Description: 删除流程实例
     * @param processDefinitionId   流程定义 ID
     * @return PageResult
     */
    @GetMapping(value = "/deleteProcessInstance")
    public PageResult deleteProcessInstance(@RequestParam(value = "processDefinitionId") String processDefinitionId) {
        org.activiti.api.process.model.ProcessInstance processInstance = processRuntime
                .delete(ProcessPayloadBuilder.delete().withProcessInstanceId(processDefinitionId).build());

        PageData pd = PageData.build().set("id", processInstance.getId()).set("name", processInstance.getName())
                .set("startDate", processInstance.getStartDate()).set("initiator", processInstance.getInitiator())
                .set("businessKey", processInstance.getBusinessKey())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey());
        return PageResult.ok(pd);
    }

    /**
     * @Title: suspendedProcessInstanceByProcessDefinitionIdAndDate
     * @author: 吴佳隆
     * @data: 2021年1月21日 上午9:28:42
     * @Description: 指定时间挂起流程定义下的所有流程实例。
     * @param processDefinitionId
     * @param suspensionDate
     * @return PageResult
     */
    @GetMapping(value = "/suspendedProcessInstanceByProcessDefinitionIdAndDate")
    public PageResult suspendedProcessInstanceByProcessDefinitionIdAndDate(
            @RequestParam(value = "processDefinitionId") String processDefinitionId,
            @RequestParam(value = "suspensionDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date suspensionDate) {
        if (suspensionDate == null || suspensionDate.before(new Date())) {
            suspensionDate = new Date();
        }

        repositoryService.suspendProcessDefinitionById(processDefinitionId, true, // true 挂起该流程定义下的所有流程实例
                suspensionDate);
        return PageResult.ok();
    }

    /**
     * @Title: activateProcessInstanceByProcessDefinitionIdAndDate
     * @author: 吴佳隆
     * @data: 2021年1月21日 上午9:28:00
     * @Description: 指定时间激活流程定义下的所有流程实例。
     * @param processDefinitionId
     * @param activationDate
     * @return PageResult
     */
    @GetMapping(value = "/activateProcessInstanceByProcessDefinitionIdAndDate")
    public PageResult activateProcessInstanceByProcessDefinitionIdAndDate(
            @RequestParam(value = "processDefinitionId") String processDefinitionId,
            @RequestParam(value = "activationDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date activationDate) {
        if (activationDate == null || activationDate.before(new Date())) {
            activationDate = new Date();
        }

        repositoryService.activateProcessDefinitionById(processDefinitionId, true, // true 激活该流程定义下的所有流程实例
                activationDate);
        return PageResult.ok();
    }

    /**
     * @Title: suspendedProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:43:14
     * @Description: 挂起单个流程实例，需要用户具有 ROLE_ACTIVITI_ADMIN 角色。
     * @param processDefinitionId   流程定义 ID
     * @return PageResult
     */
    @GetMapping(value = "/suspendedProcessInstance")
    public PageResult suspendedProcessInstance(
            @RequestParam(value = "processDefinitionId") String processDefinitionId) {
        org.activiti.api.process.model.ProcessInstance processInstance = processRuntime
                .suspend(ProcessPayloadBuilder.suspend().withProcessInstanceId(processDefinitionId).build());

        PageData pd = PageData.build().set("id", processInstance.getId()).set("name", processInstance.getName())
                .set("startDate", processInstance.getStartDate()).set("initiator", processInstance.getInitiator())
                .set("businessKey", processInstance.getBusinessKey())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey());
        return PageResult.ok(pd);
    }

    /**
     * @Title: activateProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:44:37
     * @Description: 激活单个流程实例，需要用户具有 ROLE_ACTIVITI_ADMIN 角色。
     * @param processDefinitionId   流程定义 ID
     * @return PageResult
     */
    @GetMapping(value = "/activateProcessInstance")
    public PageResult activateProcessInstance(@RequestParam(value = "processDefinitionId") String processDefinitionId) {
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

    /**
     * @Title: getVariables
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:48:48
     * @Description: 获取流程实例全局参数
     * @param processInstanceId 流程定义 ID
     * @return PageResult
     */
    @GetMapping(value = "/getVariables")
    public PageResult getVariables(@RequestParam(value = "processInstanceId") String processInstanceId) {
        List<org.activiti.api.model.shared.model.VariableInstance> variables = processRuntime
                .variables(ProcessPayloadBuilder.variables().withProcessInstanceId(processInstanceId).build());

        List<PageData> pds = new ArrayList<PageData>();
        for (org.activiti.api.model.shared.model.VariableInstance variableInstance : variables) {
            pds.add(PageData.build().set("name", variableInstance.getName()).set("type", variableInstance.getType())
                    .set("processInstanceId", variableInstance.getProcessInstanceId())
                    .set("taskId", variableInstance.getTaskId())
                    .set("taskVariable", variableInstance.isTaskVariable()));
        }
        return PageResult.ok(pds);
    }

}