package com.d7c.springboot.client.controllers.activiti;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.plugins.core.PageResult;
import com.d7c.springboot.client.controllers.WebBaseController;
import com.d7c.springboot.client.services.activiti.ActivitiDeploymentService;

/**
 * @Title: ActivitiDeploymentController
 * @Package: com.d7c.springboot.client.controllers.activiti
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:52:44
 * @Description: activiti 流程部署操作控制类
 */
@RestController
@RequestMapping(value = "/activiti/deployment")
public class ActivitiDeploymentController extends WebBaseController {
    /**
     * activiti 流程部署服务
     */
    @Resource(name = "activitiDeploymentServiceImpl")
    private ActivitiDeploymentService activitiDeploymentService;

    /**
     * @Title: listDeployment
     * @author: 吴佳隆
     * @data: 2021年1月18日 下午2:17:13
     * @Description: 查询流程部署列表
     * @return PageResult
     */
    @GetMapping(value = "/listDeployment")
    public PageResult listDeployment() {
        return PageResult.ok(activitiDeploymentService.listDeployment());
    }

    /**
     * @Title: deleteDeployment
     * @author: 吴佳隆
     * @data: 2021年1月18日 上午9:51:32
     * @Description: 删除给定的部署和对流程实例、历史流程实例和作业的级联删除。
     * @param deploymentId  部署id，不能为空。
     * @return PageResult
     */
    @GetMapping(value = "/deleteDeployment")
    public PageResult deleteDeployment(@RequestParam("deploymentId") String deploymentId) {
        return activitiDeploymentService.deleteDeployment(deploymentId);
    }

}