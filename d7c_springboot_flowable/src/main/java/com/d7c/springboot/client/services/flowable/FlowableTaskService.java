package com.d7c.springboot.client.services.flowable;

import java.util.List;
import java.util.Map;

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

    /**
     * @Title: completeTask
     * @author: 吴佳隆
     * @data: 2021年5月8日 下午2:26:30
     * @Description: 拾取并完成任务
     * @param taskId        任务 ID
     * @param userId        拾取并完成任务人 ID
     * @param variables     完成任务参数
     * @return PageResult
     */
    PageResult completeTask(String taskId, String userId, Map<String, Object> variables);

    /**
     * @Title: unclaim
     * @author: 吴佳隆
     * @data: 2021年5月8日 下午2:27:32
     * @Description: 归还任务
     * @param taskId        任务 ID
     * @return PageResult
     */
    PageResult unclaim(String taskId);

    /**
     * @Title: taskRollback
     * @author: 吴佳隆
     * @data: 2021年5月11日 上午8:18:13
     * @Description: 任务回滚
     * @param taskId        任务 ID
     * @param userId        任务处理人
     * @param variables     任务回退参数
     * @return PageResult
     */
    PageResult taskRollback(String taskId, String userId, Map<String, Object> variables);

}
