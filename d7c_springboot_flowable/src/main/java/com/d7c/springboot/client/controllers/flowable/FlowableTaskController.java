package com.d7c.springboot.client.controllers.flowable;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.tools.json.SFJsonUtil;
import com.d7c.springboot.client.controllers.WebBaseController;
import com.d7c.springboot.client.services.flowable.FlowableTaskService;

/**
 * @Title: FlowableTaskController
 * @Package: com.d7c.springboot.client.controllers.flowable
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:58:32
 * @Description: flowable 流程任务操作控制类
 */
@RestController
@RequestMapping(value = "/flowable/task")
public class FlowableTaskController extends WebBaseController {
    /**
     * flowable 流程任务操作服务
     */
    @Resource(name = "flowableTaskServiceImpl")
    private FlowableTaskService flowableTaskService;

    /**
     * @Title: taskRollback
     * @author: 吴佳隆
     * @data: 2021年5月11日 上午8:27:22
     * @Description: 任务回滚
     * @param taskId
     * @param userId
     * @param variables
     * @return PageResult
     */
    @PostMapping(value = "/taskRollback")
    public PageResult taskRollback(@RequestParam(value = "taskId") String taskId,
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "variables", required = false) String variables) {
        Map<String, Object> map = StringUtil.isBlank(variables) ? new HashMap<String, Object>()
                : SFJsonUtil.jsonToMap(variables);
        return flowableTaskService.taskRollback(taskId, userId, map);
    }

}
