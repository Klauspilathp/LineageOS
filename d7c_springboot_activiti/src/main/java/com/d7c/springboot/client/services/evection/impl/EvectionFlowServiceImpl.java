package com.d7c.springboot.client.services.evection.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.api.task.model.Task;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.context.IdService;
import com.d7c.springboot.client.services.evection.EvectionFlowService;

/**
 * @Title: EvectionFlowServiceImpl
 * @Package: com.d7c.springboot.client.services.evection.impl
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:48:57
 * @Description: 出差流程服务实现
 */
@Service(value = "evectionFlowServiceImpl")
public class EvectionFlowServiceImpl implements EvectionFlowService {
    private static final Logger logger = LoggerFactory.getLogger(EvectionFlowServiceImpl.class);
    public static final String PROCESS_DEFINITION_KEY = "evectionProcess";
    /**
     * ID 生成服务
     */
    @Resource(name = "dbIdServiceImpl")
    private IdService idService;
    /**
     * 提供对流程进行控制的服务
     */
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 提供访问 {@link Task} 和表单相关操作的服务。
     */
    @Autowired
    private TaskService taskService;

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult applyEvection(PageData pd) {
        String applyUserId = pd.getString("applyUserId");
        if (StringUtil.isBlank(applyUserId)) {
            return PageResult.error("applyUserId 不能为空！");
        }
        String businessKey = pd.getString("businessKey");
        if (StringUtil.isBlank(businessKey)) {
            businessKey = String.valueOf(idService.getLong(PROCESS_DEFINITION_KEY));
        }

        Map<String, Object> variables = new HashMap<String, Object>();

        // 启动请假流程实例
        org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(PROCESS_DEFINITION_KEY, businessKey, variables);

        // 查询当前任务
        org.activiti.engine.task.Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey)
                .singleResult();

        taskService.claim(task.getId(), applyUserId);

        // 向出差流程表中插入业务数据
        String processDefinitionId = processInstance.getProcessDefinitionId();

        logger.debug("出差申请的流程定义 ID : {}", processDefinitionId);
        return PageResult.ok();
    }

    @Override
    public PageResult pmApprove(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult gmApprove(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult personnelReport(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult endEvection(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

}