package top.d7c.springboot.client.controllers.flowable;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.d7c.springboot.client.controllers.WebBaseController;
import top.d7c.springboot.client.services.flowable.FlowableHistoryService;

/**
 * @Title: FlowableHistoryController
 * @Package: top.d7c.springboot.client.controllers.flowable
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午5:57:18
 * @Description: flowable 流程历史记录的操作控制类
 */
@RestController
@RequestMapping(value = "/flowable/history")
public class FlowableHistoryController extends WebBaseController {
    /**
     * flowable 流程历史记录的操作服务
     */
    @Resource(name = "flowableHistoryServiceImpl")
    private FlowableHistoryService flowableHistoryService;

}
