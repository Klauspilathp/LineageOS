package top.d7c.springboot.client.services.flowable;

import top.d7c.plugins.core.PageResult;

/**
 * @Title: FlowableHistoryService
 * @Package: top.d7c.springboot.client.services.flowable
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:54:38
 * @Description: flowable 流程历史记录的操作服务接口
 */
public interface FlowableHistoryService {

    /**
     * @Title: listHistoryTask
     * @author: 吴佳隆
     * @data: 2021年5月8日 下午12:50:58
     * @Description: 根据流程实例 ID 查询历史任务列表
     * @param processInstanceId
     * @return PageResult
     */
    PageResult listHistoryTask(String processInstanceId);

}
