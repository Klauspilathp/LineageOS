package com.d7c.springboot.client.services.flowable.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.services.flowable.FlowableProcessInstanceService;

/**
 * @Title: FlowableProcessInstanceServiceImpl
 * @Package: com.d7c.springboot.client.services.flowable.impl
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:51:02
 * @Description: flowable 流程实例操作服务实现
 */
@Service(value = "flowableProcessInstanceServiceImpl")
public class FlowableProcessInstanceServiceImpl implements FlowableProcessInstanceService {
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public PageResult listProcessInstance(Page<PageData> page) {
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        PageData args = page.getArgs();
        if (args != null && !args.isEmpty()) {
            String deploymentId = args.getString("deploymentId");
            if (StringUtil.isNotBlank(deploymentId)) {
                processInstanceQuery.deploymentId(deploymentId);
            }
        }

        long count = processInstanceQuery.count();
        int totalResult = (int) (count > Integer.MAX_VALUE ? Integer.MAX_VALUE : count);
        page.setTotalResult(totalResult);

        List<PageData> pds = null;
        if (count > 0) {
            pds = new ArrayList<PageData>();
            List<ProcessInstance> processInstances = processInstanceQuery.orderByStartTime().desc()
                    .listPage(page.getCurrentResult(), page.getPageSize());
            for (ProcessInstance processInstance : processInstances) {
                pds.add(PageData.build().set("processInstanceId", processInstance.getId()) // 流程实例 ID
                        .set("processDefinitionId", processInstance.getProcessDefinitionId())
                        .set("processDefinitionName", processInstance.getProcessDefinitionName()));
            }
        }

        return PageResult.ok(pds).setPage(page);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult startProcessInstanceById(String processDefinitionId, String businessKey,
            Map<String, Object> variables) {
        if (StringUtil.isBlank(processDefinitionId)) {
            return PageResult.error("processDefinitionId 不能为空！");
        }

        boolean isBusinessKeyBlank = StringUtil.isBlank(businessKey);

        ProcessInstance processInstance = null;
        if (variables == null || variables.isEmpty()) {
            if (isBusinessKeyBlank) {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
            } else {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey);
            }
        } else {
            if (isBusinessKeyBlank) {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
            } else {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
            }
        }

        if (processInstance == null) {
            return PageResult.error("流程实例不存在！");
        }

        PageData pd = PageData.build().set("processInstanceId", processInstance.getId())
                .set("name", processInstance.getName())
                .set("processDefinitionId", processInstance.getProcessDefinitionId())
                .set("processDefinitionKey", processInstance.getProcessDefinitionKey())
                .set("startTime", processInstance.getStartTime()).set("startUserId", processInstance.getStartUserId());

        return PageResult.ok(pd);
    }

}
