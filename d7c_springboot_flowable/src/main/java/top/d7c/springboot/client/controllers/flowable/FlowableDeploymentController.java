package top.d7c.springboot.client.controllers.flowable;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.d7c.springboot.client.controllers.WebBaseController;
import top.d7c.springboot.client.services.flowable.FlowableDeploymentService;

/**
 * @Title: FlowableDeploymentController
 * @Package: top.d7c.springboot.client.controllers.flowable
 * @author: 吴佳隆
 * @date: 2021年4月25日 下午4:16:11
 * @Description: flowable 流程部署操作控制类
 */
@RestController
@RequestMapping(value = "/flowable/deployment")
public class FlowableDeploymentController extends WebBaseController {
    /**
     * flowable 流程部署服务
     */
    @Resource(name = "flowableDeploymentServiceImpl")
    private FlowableDeploymentService flowableDeploymentService;

}
