package com.d7c.springboot.client.services.activiti;

import java.util.List;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;

/**
 * @Title: ActivitiProcessDefinitionService
 * @Package: com.d7c.springboot.client.services.activiti
 * @author: 吴佳隆
 * @date: 2021年3月31日 上午10:35:40
 * @Description: activiti 流程定义操作服务接口
 */
public interface ActivitiProcessDefinitionService {

    /**
     * @Title: getProcessDefinition
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午1:11:54
     * @Description: 根据流程定义 key 查询最新的流程定义。
     * @param processDefinitionKey  流程定义 key
     * @return PageData
     */
    PageData getProcessDefinition(String processDefinitionKey);

    /**
     * @Title: listProcessDefinitionByProcessDefinitionKey
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午1:12:57
     * @Description: 根据流程定义 key 查询流程定义列表。
     * @param processDefinitionKey  流程定义 key
     * @return List<PageData>
     */
    List<PageData> listProcessDefinitionByProcessDefinitionKey(String processDefinitionKey);

    /**
     * @Title: listProcessDefinition
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:02:33
     * @Description: 分页查询流程定义列表。
     * @param page
     * @return PageResult
     */
    PageResult listProcessDefinition(Page<PageData> page);

}