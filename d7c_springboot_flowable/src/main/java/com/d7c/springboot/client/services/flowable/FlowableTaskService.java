package com.d7c.springboot.client.services.flowable;

import java.util.List;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;

/**
 * @Title: FlowableTaskService
 * @Package: com.d7c.springboot.client.services.flowable
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:52:43
 * @Description: flowable 流程任务操作服务接口
 */
public interface FlowableTaskService {

    /**
     * @Title: listTask
     * @author: 吴佳隆
     * @data: 2021年5月8日 下午12:37:25
     * @Description: 任务列表
     * @param deploymentId                  流程部署 ID
     * @param userIdForCandidateAndAssignee 任务接收者或候选人
     * @return List<PageData>
     */
    List<PageData> listTask(String deploymentId, String userIdForCandidateAndAssignee);

    /**
     * @Title: listRunTask
     * @author: 吴佳隆
     * @data: 2021年5月8日 下午12:50:58
     * @Description: 根据流程实例 ID 查询正在执行的任务列表
     * @param processInstanceId
     * @return PageResult
     */
    PageResult listRunTask(String processInstanceId);

}
