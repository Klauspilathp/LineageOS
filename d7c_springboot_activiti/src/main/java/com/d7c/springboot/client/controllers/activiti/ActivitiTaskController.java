package com.d7c.springboot.client.controllers.activiti;

import java.util.List;

import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.controllers.WebBaseController;

/**
 * @Title: ActivitiTaskController
 * @Package: com.d7c.springboot.client.controllers.activiti
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: activiti 流程任务操作控制类
 */
@RestController
@RequestMapping(value = "/activiti/task")
public class ActivitiTaskController extends WebBaseController {
    /**
     * 提供与用户集成的流程任务存储库操作、访问的服务，内部实现依赖 {@link TaskService}，访问时用户需要拥有 ACTIVITI_USER 角色的权限。
     */
    @Autowired
    private TaskRuntime taskRuntime;
    /**
     * 提供访问 {@link Task} 和表单相关操作的服务。
     */
    @Autowired
    private TaskService taskService;

    /**
     * @Title: deleteCandidateUser
     * @author: 吴佳隆
     * @data: 2021年1月21日 下午12:04:52
     * @Description: 从租任务中删除候选人
     * @param taskId
     * @param candidateUser
     * @return PageResult
     */
    @GetMapping(value = "/deleteCandidateUser")
    public PageResult deleteCandidateUser(@RequestParam("taskId") String taskId,
            @RequestParam("candidateUser") String candidateUser) {
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }
        if (StringUtil.isBlank(candidateUser)) {
            return PageResult.error("candidateUser 不能为空！");
        }
        taskService.deleteCandidateUser(taskId, candidateUser);
        return PageResult.ok();
    }

    /**
     * @Title: addCandidateUser
     * @author: 吴佳隆
     * @data: 2021年1月21日 下午12:03:28
     * @Description: 向组任务中添加任务候选人
     * @param taskId
     * @param candidateUser
     * @return PageResult
     */
    @GetMapping(value = "/addCandidateUser")
    public PageResult addCandidateUser(@RequestParam("taskId") String taskId,
            @RequestParam("candidateUser") String candidateUser) {
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }
        if (StringUtil.isBlank(candidateUser)) {
            return PageResult.error("candidateUser 不能为空！");
        }
        taskService.addCandidateUser(taskId, candidateUser);
        return PageResult.ok();
    }

    /**
     * @Title: releaseByCandidateUser
     * @author: 吴佳隆
     * @data: 2021年1月21日 上午11:58:10
     * @Description: 组任务的候选人退回任务
     * @param taskId
     * @param candidateUser
     * @return PageResult
     */
    @GetMapping(value = "/releaseByCandidateUser")
    public PageResult releaseByCandidateUser(@RequestParam("taskId") String taskId,
            @RequestParam("candidateUser") String candidateUser) {
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }
        if (StringUtil.isBlank(candidateUser)) {
            return PageResult.error("candidateUser 不能为空！");
        }
        // 归还当前登录人的指定任务，当前登录人信息从 {@link SecurityManager} 中获取
        // Task task = taskRuntime.release(TaskPayloadBuilder.release().withTaskId(taskId).build());
        org.activiti.engine.task.Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(candidateUser)
                .singleResult();
        if (task == null) {
            return PageResult.error("任务不存在！");
        }
        if (StringUtil.isNotBlank(task.getAssignee())) {
            return PageResult.error("任务没有被领取！");
        }
        // 归还任务
        taskService.setAssignee(taskId, null);
        return PageResult.ok();
    }

    /**
     * @Title: claimByCandidateUser
     * @author: 吴佳隆
     * @data: 2021年1月21日 上午10:29:27
     * @Description: 组任务的候选人拾取任务
     * @param taskId
     * @param candidateUser
     * @return PageResult
     */
    @GetMapping(value = "/claimByCandidateUser")
    public PageResult claimByCandidateUser(@RequestParam("taskId") String taskId,
            @RequestParam("candidateUser") String candidateUser) {
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }
        if (StringUtil.isBlank(candidateUser)) {
            return PageResult.error("candidateUser 不能为空！");
        }
        Task task = taskRuntime.task(taskId);
        if (task == null) {
            return PageResult.error("任务不存在！");
        }
        if (StringUtil.isNotBlank(task.getAssignee())) {
            return PageResult.error("任务已被领取！");
        }
        // 拾取任务
        taskService.claim(taskId, candidateUser);
        return PageResult.ok();
    }

    /**
     * @Title: listTaskByCandidateUser
     * @author: 吴佳隆
     * @data: 2021年1月21日 上午10:23:12
     * @Description: 查询候选人任务列表
     * @param candidateUser
     * @return PageResult
     */
    @GetMapping(value = "/listTaskByCandidateUser")
    public PageResult listTaskByCandidateUser(@RequestParam("candidateUser") String candidateUser) {
        if (StringUtil.isBlank(candidateUser)) {
            return PageResult.error("candidateUser 不能为空！");
        }
        List<org.activiti.engine.task.Task> tasks = taskService.createTaskQuery().taskCandidateUser(candidateUser)
                .list();
        return PageResult.ok(tasks);
    }

    /**
     * @Title: listTaskByKey
     * @author: 吴佳隆
     * @data: 2021年1月19日 上午9:49:49
     * @Description: 根据给定的 taskDefinitionKey（UserTask 的 id） 查询任务列表。
     * @param taskDefinitionKey 任务定义键
     * @return PageResult
     */
    @GetMapping(value = "/listTaskByKey")
    public PageResult listTaskByKey(@RequestParam("taskDefinitionKey") String taskDefinitionKey) {
        if (StringUtil.isBlank(taskDefinitionKey)) {
            return PageResult.error("taskDefinitionKey 不能为空！");
        }
        List<org.activiti.engine.task.Task> tasks = taskService.createTaskQuery().taskDefinitionKey(taskDefinitionKey)
                .orderByTaskCreateTime().desc().list();
        return PageResult.ok(tasks);
    }

    /**
     * @Title: listTasks
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:53:34
     * @Description: 分页查询流程实例列表
     * @param page
     * @return PageResult
     */
    @GetMapping(value = "/listTasks")
    public PageResult listTasks(Page<PageData> page) {
        org.activiti.api.runtime.shared.query.Page<Task> tasks = taskRuntime
                .tasks(Pageable.of(page.getCurrentResult(), page.getPageSize()));
        page.setTotalResult(tasks.getTotalItems());
        return PageResult.ok(tasks.getContent()).setPage(page);
    }

    /**
     * @Title: completeTask
     * @author: 吴佳隆
     * @data: 2021年1月20日 下午5:44:45
     * @Description: 执行个人任务
     * @param taskId        任务 ID
     * @return PageResult
     */
    @GetMapping(value = "/completeTask")
    public PageResult completeTask(@RequestParam("taskId") String taskId) {
        if (StringUtil.isBlank(taskId)) {
            return PageResult.error("taskId 不能为空！");
        }
        Task task = taskRuntime.task(taskId);
        if (task == null) {
            return PageResult.error("任务不存在！");
        }
        if (StringUtil.isNotBlank(task.getAssignee())) {
            return PageResult.error("任务已被领取！");
        }
        // 拾取任务
        taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
        // 完成任务
        Task complete = taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
        return PageResult.ok(complete);
    }

}