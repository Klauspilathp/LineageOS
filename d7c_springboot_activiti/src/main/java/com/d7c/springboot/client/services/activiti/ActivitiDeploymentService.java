package com.d7c.springboot.client.services.activiti;

import java.util.List;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;

/**
 * @Title: ActivitiDeploymentService
 * @Package: com.d7c.springboot.client.services.activiti
 * @author: 吴佳隆
 * @date: 2021年3月31日 上午10:45:21
 * @Description: activiti 流程部署服务接口
 */
public interface ActivitiDeploymentService {

    /**
     * @Title: listDeployment
     * @author: 吴佳隆
     * @data: 2021年3月31日 上午10:49:42
     * @Description: 查询流程部署列表。
     * @return List<PageData>
     */
    List<PageData> listDeployment();

    /**
     * @Title: deleteDeployment
     * @author: 吴佳隆
     * @data: 2021年3月31日 上午10:49:39
     * @Description: 删除给定的部署和对流程实例、历史流程实例和作业的级联删除。
     * @param deploymentId  部署id，不能为空。
     * @return PageResult
     */
    PageResult deleteDeployment(String deploymentId);

}