package com.gnol.springboot.client.controllers.evection;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gnol.springboot.client.controllers.WebBaseController;

/**
 * @Title: EvectionFlowController
 * @Package: com.gnol.springboot.client.controllers.evection
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: 出差流程控制类
 */
@RestController
@RequestMapping(value = "/evection/processDefinition")
public class EvectionProcessDefinitionController extends WebBaseController {
    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private RepositoryService repositoryService;
    
    
    
    

}