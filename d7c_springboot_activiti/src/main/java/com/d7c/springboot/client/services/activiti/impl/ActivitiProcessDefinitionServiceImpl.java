package com.d7c.springboot.client.services.activiti.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.springboot.client.services.activiti.ActivitiProcessDefinitionService;

/**
 * @Title: ActivitiProcessDefinitionServiceImpl
 * @Package: com.d7c.springboot.client.services.activiti.impl
 * @author: 吴佳隆
 * @date: 2021年3月31日 上午10:36:03
 * @Description: activiti 流程定义操作服务实现
 */
@Service(value = "activitiProcessDefinitionServiceImpl")
public class ActivitiProcessDefinitionServiceImpl implements ActivitiProcessDefinitionService {
    /**
     * 操作流程实例服务，访问时用户需要拥有 ACTIVITI_USER 角色的权限。
     */
    @Autowired
    private ProcessRuntime processRuntime;
    /**
     * 提供对流程定义和部署存储库的访问服务
     */
    @Autowired
    private RepositoryService repositoryService;

    @Override
    public PageData getProcessDefinition(String processDefinitionKey) {
        // 流程定义对象
        org.activiti.engine.repository.ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).singleResult();

        /*if (processDefinition == null) {
            return PageResult.error("流程定义不存在！");
        }
        // 流程部署 ID
        String deploymentId = processDefinition.getDeploymentId();
        // 获取 bpmn 文件名称
        String diagramResourceName = processDefinition.getDiagramResourceName();
        // 获取 png 文件名称
        String resourceName = processDefinition.getResourceName();
        // bpmn 文件输入流
        InputStream diagramInputStream = repositoryService.getResourceAsStream(deploymentId, diagramResourceName);
        // png 文件输入流
        InputStream pngInputStream = repositoryService.getResourceAsStream(deploymentId, resourceName);*/

        return PageData.build().set("id", processDefinition.getId()).set("category", processDefinition.getCategory())
                .set("name", processDefinition.getName()).set("key", processDefinition.getKey())
                .set("description", processDefinition.getDescription()).set("version", processDefinition.getVersion())
                .set("resourceName", processDefinition.getResourceName())
                .set("deploymentId", processDefinition.getDeploymentId())
                .set("diagramResourceName", processDefinition.getDiagramResourceName())
                .set("suspended", processDefinition.isSuspended()).set("tenantId", processDefinition.getTenantId());
    }

    @Override
    public List<PageData> listProcessDefinitionByProcessDefinitionKey(String processDefinitionKey) {
        List<org.activiti.engine.repository.ProcessDefinition> processDefinitions = repositoryService
                .createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey)
                .orderByProcessDefinitionVersion().desc().list();

        List<PageData> pds = new ArrayList<PageData>();
        for (org.activiti.engine.repository.ProcessDefinition processDefinition : processDefinitions) {
            pds.add(PageData.build().set("id", processDefinition.getId())
                    .set("category", processDefinition.getCategory()).set("name", processDefinition.getName())
                    .set("key", processDefinition.getKey()).set("description", processDefinition.getDescription())
                    .set("version", processDefinition.getVersion())
                    .set("resourceName", processDefinition.getResourceName())
                    .set("deploymentId", processDefinition.getDeploymentId())
                    .set("diagramResourceName", processDefinition.getDiagramResourceName())
                    .set("suspended", processDefinition.isSuspended())
                    .set("tenantId", processDefinition.getTenantId()));
        }
        return pds;
    }

    @Override
    public PageResult listProcessDefinition(Page<PageData> page) {
        org.activiti.api.runtime.shared.query.Page<org.activiti.api.process.model.ProcessDefinition> processDefinitions = processRuntime
                .processDefinitions(Pageable.of(page.getCurrentResult(), page.getPageSize()));
        page.setTotalResult(processDefinitions.getTotalItems());

        List<PageData> pds = new ArrayList<PageData>();
        for (org.activiti.api.process.model.ProcessDefinition processDefinition : processDefinitions.getContent()) {
            pds.add(PageData.build().set("id", processDefinition.getId()).set("name", processDefinition.getName())
                    .set("key", processDefinition.getKey()).set("description", processDefinition.getDescription())
                    .set("version", processDefinition.getVersion()).set("formKey", processDefinition.getFormKey()));
        }
        return PageResult.ok(pds).setPage(page);
    }

}