package com.d7c.springboot.client.controllers.activiti;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.oauth2.springboot.SecurityUtil;
import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.tools.json.SFJsonUtil;
import com.d7c.springboot.client.controllers.WebBaseController;
import com.d7c.springboot.client.services.activiti.ActivitiProcessInstanceService;

/**
 * @Title: ActivitiProcessInstanceController
 * @Package: com.d7c.springboot.client.controllers.activiti
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: activiti 流程实例操作控制类
 */
@RestController
@RequestMapping(value = "/activiti/processInstance")
public class ActivitiProcessInstanceController extends WebBaseController {
    /**
     * activiti 流程定义服务
     */
    @Resource(name = "activitiProcessInstanceServiceImpl")
    private ActivitiProcessInstanceService activitiProcessInstanceService;

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
        return activitiProcessInstanceService.listProcessDefinition(page);
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
        Map<String, Object> map = StringUtil.isBlank(variables) ? new HashMap<String, Object>()
                : SFJsonUtil.jsonToMap(variables);
        map.put("applyUserId", SecurityUtil.getUserId());

        return activitiProcessInstanceService.runProcessInstance(processDefinitionKey, businessKey, map);
    }

    /**
     * @Title: startProcessInstance
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:33:54
     * @Description: 启动流程实例，需要用户具有 ROLE_ACTIVITI_ADMIN 角色。
     * @param processDefinitionKey  流程实例 Key
     * @param instanceName          流程实例名称
     * @param businessKey           业务编号
     * @param variables             流程实例参数
     * @return PageResult
     */
    @PostMapping(value = "/startProcessInstance")
    public PageResult startProcessInstance(@RequestParam(value = "processDefinitionKey") String processDefinitionKey,
            @RequestParam(value = "instanceName", required = false) String instanceName,
            @RequestParam(value = "businessKey") String businessKey,
            @RequestParam(value = "variables", required = false) String variables) {
        Map<String, Object> params = StringUtil.isBlank(variables) ? null : SFJsonUtil.jsonToMap(variables);
        return activitiProcessInstanceService.startProcessInstance(processDefinitionKey, instanceName, businessKey,
                params);
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
        return activitiProcessInstanceService.deleteProcessInstance(processDefinitionId);
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
        return activitiProcessInstanceService.suspendedProcessInstanceByProcessDefinitionIdAndDate(processDefinitionId,
                suspensionDate);
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
        return activitiProcessInstanceService.activateProcessInstanceByProcessDefinitionIdAndDate(processDefinitionId,
                activationDate);
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
        return activitiProcessInstanceService.suspendedProcessInstance(processDefinitionId);
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
        return activitiProcessInstanceService.activateProcessInstance(processDefinitionId);
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
        return PageResult.ok(activitiProcessInstanceService.getVariables(processInstanceId));
    }

}