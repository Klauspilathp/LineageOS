package com.d7c.springboot.client.services.flowable.impl;

import java.util.ArrayList;
import java.util.List;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.springboot.client.services.flowable.FlowableProcessDefinitionService;

/**
 * @Title: FlowableProcessDefinitionServiceImpl
 * @Package: com.d7c.springboot.client.services.flowable.impl
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:49:03
 * @Description: flowable 流程定义操作服务实现
 */
@Service(value = "flowableProcessDefinitionServiceImpl")
public class FlowableProcessDefinitionServiceImpl implements FlowableProcessDefinitionService {
    /**
     * 提供对流程定义和部署存储库的访问服务
     */
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 操作动态 BPMN 服务
     */
    /*@Autowired
    private DynamicBpmnService dynamicBpmnService;*/

    @Override
    public PageResult listActiveProcessDefinition(Page<PageData> page) {
        long count = repositoryService.createProcessDefinitionQuery().active().count();
        int totalResult = (int) (count > Integer.MAX_VALUE ? Integer.MAX_VALUE : count);
        page.setTotalResult(totalResult);

        List<PageData> pds = null;
        if (count > 0) {
            pds = new ArrayList<PageData>();
            List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().active().asc()
                    .listPage(page.getCurrentResult(), page.getPageSize());
            for (ProcessDefinition processDefinition : processDefinitions) {
                pds.add(PageData.build().set("processDefinitionId", processDefinition.getId()) // 流程定义 ID
                        .set("name", processDefinition.getName()) // 流程定义名称
                        .set("deploymentId", processDefinition.getDeploymentId()) // 流程部署 ID
                        .set("key", processDefinition.getKey()) // 流程定义版本唯一名称
                );
            }
        }
        return PageResult.ok(pds).setPage(page);
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult suspendProcessDefinitionById(String processDefinitionId) {
        // SUSPENSION_STATE_ 字段为 1 表示流程被激活，为 2 表示流程被挂起。
        repositoryService.suspendProcessDefinitionById(processDefinitionId);
        return PageResult.ok();
    }

}
