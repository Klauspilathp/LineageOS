package com.gnol.springboot.client.services.evection.impl;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gnol.springboot.client.services.evection.EvectionFlowService;

/**
 * @Title: EvectionFlowServiceImpl
 * @Package: com.gnol.springboot.client.services.evection.impl
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:48:57
 * @Description: 出差流程服务实现
 */
@Service(value = "evectionFlowServiceImpl")
public class EvectionFlowServiceImpl implements EvectionFlowService {
    private static final Logger logger = LoggerFactory.getLogger(EvectionFlowServiceImpl.class);
    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private HistoryService historyService;

}