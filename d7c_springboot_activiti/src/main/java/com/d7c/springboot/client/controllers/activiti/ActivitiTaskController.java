package com.d7c.springboot.client.controllers.activiti;

import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
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
     * 提供对流程任务存储库操作、访问的服务
     */
    @Autowired
    private TaskRuntime taskRuntime;

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

    @GetMapping(value = "/taskId")
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