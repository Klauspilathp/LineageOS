package com.d7c.springboot.client.controllers.evection;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.springboot.client.controllers.WebBaseController;
import com.d7c.springboot.client.services.evection.EvectionFlowService;

/**
 * @Title: EvectionFlowController
 * @Package: com.d7c.springboot.client.controllers.evection
 * @author: 吴佳隆
 * @date: 2021年4月25日 下午3:21:17
 * @Description: 出差流程控制类
 */
@RestController
@RequestMapping(value = "/evection/flow")
public class EvectionFlowController extends WebBaseController {
    /**
     * 出差流程服务
     */
    @Resource(name = "evectionFlowServiceImpl")
    private EvectionFlowService evectionFlowService;

}
