package com.d7c.springboot.client.services.flowable;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;

/**
 * @Title: FlowableProcessDefinitionService
 * @Package: com.d7c.springboot.client.services.flowable
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:48:49
 * @Description: flowable 流程定义操作服务接口
 */
public interface FlowableProcessDefinitionService {

    /**
     * @Title: listActiveProcessDefinition
     * @author: 吴佳隆
     * @data: 2021年5月8日 上午9:22:34
     * @Description: 分页查询当前部署中的流程定义
     * @param page
     * @return PageResult
     */
    PageResult listActiveProcessDefinition(Page<PageData> page);

    /**
     * @Title: suspendProcessDefinitionById
     * @author: 吴佳隆
     * @data: 2021年5月8日 下午5:36:52
     * @Description: 根据流程定义 ID 挂起流程
     * @param processDefinitionId
     * @return PageResult
     */
    PageResult suspendProcessDefinitionById(String processDefinitionId);

}
