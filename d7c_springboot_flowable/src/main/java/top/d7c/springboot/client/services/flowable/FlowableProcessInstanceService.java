package top.d7c.springboot.client.services.flowable;

import java.io.InputStream;
import java.util.Map;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;

/**
 * @Title: FlowableProcessInstanceService
 * @Package: top.d7c.springboot.client.services.flowable
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:50:52
 * @Description: flowable 流程实例操作服务接口
 */
public interface FlowableProcessInstanceService {

    /**
     * @Title: listProcessInstance
     * @author: 吴佳隆
     * @data: 2021年5月8日 上午10:26:45
     * @Description: 分页查询流程实例列表
     * @param page
     * @return PageResult
     */
    PageResult listProcessInstance(Page<PageData> page);

    /**
     * @Title: startProcessInstanceById
     * @author: 吴佳隆
     * @data: 2021年5月8日 上午10:43:56
     * @Description: 启动流程实例
     * @param processDefinitionId   流程定义 ID
     * @param businessKey           标识流程实例的业务主键
     * @param variables             传递参数
     * @return PageResult
     */
    PageResult startProcessInstanceById(String processDefinitionId, String businessKey, Map<String, Object> variables);

    /**
     * @Title: deleteProcessInstance
     * @author: 吴佳隆
     * @data: 2021年5月8日 下午2:07:49
     * @Description: 删除流程实例
     * @param processInstanceId 流程实例 ID
     * @param deleteReason      删除原因
     * @return PageResult
     */
    PageResult deleteProcessInstance(String processInstanceId, String deleteReason);

    /**
     * @Title: getProcessDiagramInputStream
     * @author: 吴佳隆
     * @data: 2021年5月8日 下午3:07:33
     * @Description: 根据流程实例 ID 生成基于 BPMN 2.0 流程中的图交换信息图像的输入流
     * @param processInstanceId
     * @return InputStream
     */
    InputStream getProcessDiagramInputStream(String processInstanceId);

}
