package com.d7c.springboot.client.services.activiti.impl;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.services.activiti.ActivitiDeploymentService;

/**
 * @Title: ActivitiDeploymentServiceImpl
 * @Package: com.d7c.springboot.client.services.activiti.impl
 * @author: 吴佳隆
 * @date: 2021年3月31日 上午10:45:41
 * @Description: activiti 流程部署服务实现
 */
@Service(value = "activitiDeploymentServiceImpl")
public class ActivitiDeploymentServiceImpl implements ActivitiDeploymentService {
    /**
     * 提供对流程定义和部署存储库的访问服务
     */
    @Autowired
    private RepositoryService repositoryService;

    @Override
    public List<PageData> listDeployment() {
        /**
         * Activiti 采用懒加载查询数据，所以要遍历的取出放到 map 中返回出去，
         * 否则会报 Could not write JSON: lazy loading outside command context; 错误。
         */
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();

        List<PageData> pds = new ArrayList<PageData>();
        for (Deployment deployment : deployments) {
            pds.add(PageData.build().set("id", deployment.getId()).set("name", deployment.getName())
                    .set("deploymentTime", deployment.getDeploymentTime()).set("category", deployment.getCategory())
                    .set("key", deployment.getKey()).set("tenantId", deployment.getTenantId())
                    .set("projectReleaseVersion", deployment.getProjectReleaseVersion()));
        }
        return pds;
    }

    @Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public PageResult deleteDeployment(String deploymentId) {
        if (StringUtil.isBlank(deploymentId)) {
            return PageResult.error("deploymentId 不能为空！");
        }

        repositoryService.deleteDeployment(deploymentId, true); // 级联删除
        return PageResult.ok(deploymentId + "流程部署已删除！");
    }

}