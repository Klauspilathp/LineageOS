package com.d7c.springboot.client.controllers.flowable;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.springboot.client.controllers.WebBaseController;
import com.d7c.springboot.client.services.flowable.FlowableProcessDefinitionService;

/**
 * @Title: FlowableProcessDefinitionController
 * @Package: com.d7c.springboot.client.controllers.flowable
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:57:55
 * @Description: flowable 流程定义操作控制类
 */
@RestController
@RequestMapping(value = "/flowable/processDefinition")
public class FlowableProcessDefinitionController extends WebBaseController {
    /**
     * flowable 流程定义操作服务
     */
    @Resource(name = "flowableProcessDefinitionServiceImpl")
    private FlowableProcessDefinitionService flowableProcessDefinitionService;

}
