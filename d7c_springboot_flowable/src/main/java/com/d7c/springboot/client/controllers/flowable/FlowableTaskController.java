package com.d7c.springboot.client.controllers.flowable;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
