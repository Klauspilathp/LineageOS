package com.d7c.springboot.client.services.activiti;

import java.util.List;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;

/**
 * @Title: ActivitiTaskService
 * @Package: com.d7c.springboot.client.services.activiti
 * @author: 吴佳隆
 * @date: 2021年3月31日 下午2:36:29
 * @Description: activiti 流程任务操作服务接口
 */
public interface ActivitiTaskService {

    /**
     * @Title: deleteCandidateUser
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:38:00
     * @Description: 从组任务中删除候选人。
     * @param taskId        任务编号
     * @param candidateUser 候选人
     * @return PageResult
     */
    PageResult deleteCandidateUser(String taskId, String candidateUser);

    /**
     * @Title: addCandidateUser
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:39:19
     * @Description: 向组任务中添加任务候选人。
     * @param taskId        任务编号
     * @param candidateUser 候选人
     * @return PageResult
     */
    PageResult addCandidateUser(String taskId, String candidateUser);

    /**
     * @Title: releaseByCandidateUser
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:39:56
     * @Description: 组任务的候选人退回任务。
     * @param taskId        任务编号
     * @param candidateUser 候选人
     * @return PageResult
     */
    PageResult releaseByCandidateUser(String taskId, String candidateUser);

    /**
     * @Title: claimByCandidateUser
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:41:26
     * @Description: 组任务的候选人拾取任务。
     * @param taskId        任务编号
     * @param candidateUser 候选人
     * @return PageResult
     */
    PageResult claimByCandidateUser(String taskId, String candidateUser);

    /**
     * @Title: listTaskByCandidateUser
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:42:07
     * @Description: 查询候选人任务列表。
     * @param candidateUser 候选人
     * @return List<PageData>
     */
    List<PageData> listTaskByCandidateUser(String candidateUser);

    /**
     * @Title: listTaskByKey
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:42:57
     * @Description: 根据给定的 taskDefinitionKey（UserTask 的 id） 查询任务列表。
     * @param taskDefinitionKey 任务定义键
     * @return List<PageData>
     */
    List<PageData> listTaskByKey(String taskDefinitionKey);

    /**
     * @Title: listTasks
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:43:40
     * @Description: 分页查询流程实例列表。
     * @param page
     * @return PageResult
     */
    PageResult listTasks(Page<PageData> page);

    /**
     * @Title: completeTask
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午2:44:27
     * @Description: 执行个人任务。
     * @param taskId        任务 ID
     * @return PageResult
     */
    PageResult completeTask(String taskId);

}