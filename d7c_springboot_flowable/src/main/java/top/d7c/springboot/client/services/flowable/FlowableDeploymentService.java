package top.d7c.springboot.client.services.flowable;

import top.d7c.plugins.core.PageResult;

/**
 * @Title: FlowableDeploymentService
 * @Package: top.d7c.springboot.client.services.flowable
 * @author: 吴佳隆
 * @date: 2021年4月25日 下午4:18:11
 * @Description: flowable 流程部署服务接口
 */
public interface FlowableDeploymentService {

    /**
     * @Title: listDeployment
     * @author: 吴佳隆
     * @data: 2021年5月8日 下午3:08:57
     * @Description: 根据流程部署 key 查询流程部署列表
     * @param key
     * @return PageResult
     */
    PageResult listDeployment(String key);

}
