package top.d7c.springboot.client.services.activiti;

import java.util.Date;
import java.util.List;
import java.util.Map;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;

/**
 * @Title: ActivitiProcessInstanceService
 * @Package: top.d7c.springboot.client.services.activiti
 * @author: 吴佳隆
 * @date: 2021年3月31日 下午12:51:57
 * @Description: activiti 流程实例操作服务接口
 */
public interface ActivitiProcessInstanceService {

    /**
     * @Title: listProcessDefinition
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:13:20
     * @Description: 分页查询流程实例列表
     * @param page
     * @return PageResult
     */
    PageResult listProcessDefinition(Page<PageData> page);

    /**
     * @Title: runProcessInstance
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午12:54:45
     * @Description: 启动流程实例
     * @param processDefinitionKey  流程实例 Key
     * @param businessKey           业务编号
     * @param variables             流程实例参数
     * @return PageResult
     */
    PageResult runProcessInstance(String processDefinitionKey, String businessKey, Map<String, Object> variables);

    /**
     * @Title: startProcessInstance
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:17:07
     * @Description: 启动流程实例，需要用户具有 ROLE_ACTIVITI_ADMIN 角色。
     * @param processDefinitionKey  流程实例 Key
     * @param instanceName          流程实例名称
     * @param businessKey           业务编号
     * @param variables             流程实例参数
     * @return PageResult
     */
    PageResult startProcessInstance(String processDefinitionKey, String instanceName, String businessKey,
            Map<String, Object> variables);

    /**
     * @Title: deleteProcessInstance
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:21:38
     * @Description: 删除流程实例。
     * @param processDefinitionId   流程定义 ID
     * @return PageResult
     */
    PageResult deleteProcessInstance(String processDefinitionId);

    /**
     * @Title: suspendedProcessInstanceByProcessDefinitionIdAndDate
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:23:35
     * @Description: 指定时间挂起流程定义下的所有流程实例。
     * @param processDefinitionId   流程定义 ID
     * @param suspensionDate        挂起时间
     * @return PageResult
     */
    PageResult suspendedProcessInstanceByProcessDefinitionIdAndDate(String processDefinitionId, Date suspensionDate);

    /**
     * @Title: activateProcessInstanceByProcessDefinitionIdAndDate
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:25:25
     * @Description: 指定时间激活流程定义下的所有流程实例。
     * @param processDefinitionId   流程定义 ID
     * @param activationDate        激活时间
     * @return PageResult
     */
    PageResult activateProcessInstanceByProcessDefinitionIdAndDate(String processDefinitionId, Date activationDate);

    /**
     * @Title: suspendedProcessInstance
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:26:59
     * @Description: 挂起单个流程实例，需要用户具有 ROLE_ACTIVITI_ADMIN 角色。
     * @param processDefinitionId   流程定义 ID
     * @return PageResult
     */
    PageResult suspendedProcessInstance(String processDefinitionId);

    /**
     * @Title: activateProcessInstance
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:28:39
     * @Description: 激活单个流程实例，需要用户具有 ROLE_ACTIVITI_ADMIN 角色。
     * @param processDefinitionId   流程定义 ID
     * @return PageResult
     */
    PageResult activateProcessInstance(String processDefinitionId);

    /**
     * @Title: getVariables
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:30:05
     * @Description: 获取流程实例全局参数。
     * @param processInstanceId     流程定义 ID
     * @return List<PageData>
     */
    List<PageData> getVariables(String processInstanceId);

}