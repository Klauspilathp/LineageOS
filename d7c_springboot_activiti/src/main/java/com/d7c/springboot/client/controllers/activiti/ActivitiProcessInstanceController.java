package com.d7c.springboot.client.controllers.activiti;

import java.util.List;
import java.util.Map;

import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.tools.idfactory.uuid.UUID;
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
     * @Title: listProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:25:14
     * @Description: 分页查询流程实例列表
     * @param page
     * @return PageResult
     */
    @GetMapping(value = "/listProcessInstance")
    public PageResult listProcessDefinition(Page<PageData> page) {
        org.activiti.api.runtime.shared.query.Page<ProcessInstance> processInstances = processRuntime
                .processInstances(Pageable.of(page.getCurrentResult(), page.getPageSize()));
        page.setTotalResult(processInstances.getTotalItems());
        return PageResult.ok(processInstances.getContent()).setPage(page);
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
    @GetMapping(value = "/runProcessInstance")
    public PageResult runProcessInstance(@RequestParam("processDefinitionKey") String processDefinitionKey,
            @RequestParam("businessKey") String businessKey, @RequestParam("variables") Map<String, Object> variables) {
        org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
        return PageResult.ok(processInstance);
    }

    /**
     * @Title: startProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:33:54
     * @Description: 启动流程实例
     * @param processDefinitionKey  流程实例 Key
     * @param instanceName          流程实例名称
     * @param businessKey           业务编号
     * @param instanceVariable      流程实例参数
     * @return PageResult
     */
    @GetMapping(value = "/startProcessInstance")
    public PageResult startProcessInstance(@RequestParam("processDefinitionKey") String processDefinitionKey,
            @RequestParam("instanceName") String instanceName, @RequestParam("businessKey") String businessKey,
            @RequestParam("variables") Map<String, Object> variables) {
        if (StringUtil.isBlank(processDefinitionKey)) {
            return PageResult.error("processDefinitionKey 不能为空！");
        }
        if (StringUtil.isBlank(instanceName)) {
            instanceName = UUID.getUUID().nextStr();
        }
        ProcessInstance processInstance = processRuntime.start(
                ProcessPayloadBuilder.start().withProcessDefinitionKey(processDefinitionKey).withName(instanceName)
                        .withBusinessKey(businessKey).withVariable("variables", variables).build());
        return PageResult.ok(processInstance);
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
    public PageResult deleteProcessInstance(@RequestParam("processDefinitionId") String processDefinitionId) {
        ProcessInstance processInstance = processRuntime
                .delete(ProcessPayloadBuilder.delete().withProcessInstanceId(processDefinitionId).build());
        return PageResult.ok(processInstance);
    }

    /**
     * @Title: suspendedProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:43:14
     * @Description: 挂起流程实例
     * @param processDefinitionId   流程定义 ID
     * @return PageResult
     */
    @GetMapping(value = "/suspendedProcessInstance")
    public PageResult suspendedProcessInstance(@RequestParam("processDefinitionId") String processDefinitionId) {
        ProcessInstance processInstance = processRuntime
                .suspend(ProcessPayloadBuilder.suspend().withProcessInstanceId(processDefinitionId).build());
        return PageResult.ok(processInstance);
    }

    /**
     * @Title: activateProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:44:37
     * @Description: 激活流程实例
     * @param processDefinitionId   流程定义 ID
     * @return PageResult
     */
    @GetMapping(value = "/activateProcessInstance")
    public PageResult activateProcessInstance(@RequestParam("processDefinitionId") String processDefinitionId) {
        ProcessInstance processInstance = processRuntime
                .resume(ProcessPayloadBuilder.resume().withProcessInstanceId(processDefinitionId).build());
        return PageResult.ok(processInstance);
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
    public PageResult getVariables(@RequestParam("processInstanceId") String processInstanceId) {
        List<VariableInstance> variables = processRuntime
                .variables(ProcessPayloadBuilder.variables().withProcessInstanceId(processInstanceId).build());
        return PageResult.ok(variables);
    }

}