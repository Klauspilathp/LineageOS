package com.d7c.springboot.client.services.activiti;

import java.util.List;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;

/**
 * @Title: ActivitiHistoryService
 * @Package: com.d7c.springboot.client.services.activiti
 * @author: 吴佳隆
 * @date: 2021年3月31日 下午2:57:28
 * @Description: activiti 流程历史记录的操作服务接口
 */
public interface ActivitiHistoryService {

    /**
     * @Title: listHistoricActivityInstanceById
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:59:22
     * @Description: 根据流程实例 ID 或流程定义 ID 查询历史信息。
     * @param processInstanceId     流程实例 ID
     * @param processDefinitionId   流程定义 ID
     * @return List<PageData>
     */
    List<PageData> listHistoricActivityInstanceById(String processInstanceId, String processDefinitionId);

    /**
     * @Title: listHistoricTaskInstanceByAssignee
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午3:00:15
     * @Description: 分页查询任务代理人的历史代理任务实例
     * @param page
     * @return PageResult
     */
    PageResult listHistoricTaskInstanceByAssignee(Page<PageData> page);

}