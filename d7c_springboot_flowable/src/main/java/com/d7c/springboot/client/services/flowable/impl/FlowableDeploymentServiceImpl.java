package com.d7c.springboot.client.services.flowable.impl;

import java.util.ArrayList;
import java.util.List;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;
import com.d7c.plugins.core.StringUtil;
import com.d7c.springboot.client.services.flowable.FlowableDeploymentService;

/**
 * @Title: FlowableDeploymentServiceImpl
 * @Package: com.d7c.springboot.client.services.flowable.impl
 * @author: 吴佳隆
 * @date: 2021年4月25日 下午4:18:33
 * @Description: flowable 流程部署服务实现
 */
@Service(value = "flowableDeploymentServiceImpl")
public class FlowableDeploymentServiceImpl implements FlowableDeploymentService {
    /**
     * 提供对流程定义和部署存储库的访问服务
     */
    @Autowired
    private RepositoryService repositoryService;

    @Override
    public PageResult listDeployment(String key) {
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();

        if (StringUtil.isNotBlank(key)) {
            deploymentQuery.deploymentKey(key);
        }

        List<Deployment> deployments = deploymentQuery.orderByDeploymenTime().desc().list();

        List<PageData> pds = new ArrayList<PageData>();
        deployments.stream().forEach(deployment -> {
            pds.add(PageData.build().set("deploymentId", deployment.getId()) // 流程部署 ID
                    .set("deploymentName", deployment.getName()) // 流程名称
                    .set("deploymentTime", deployment.getDeploymentTime()) // 流程部署时间
                    .set("key", deployment.getKey()));
        });

        return PageResult.ok(pds);
    }

}
