package com.d7c.springboot.client.services.evection;

import com.d7c.plugins.core.PageData;
import com.d7c.plugins.core.PageResult;

/**
 * @Title: EvectionFlowService
 * @Package: com.d7c.springboot.client.services.evection
 * @author: 吴佳隆
 * @date: 2021年1月15日 上午7:48:34
 * @Description: 出差流程服务接口
 */
public interface EvectionFlowService {

    /**
     * @Title: applyEvection
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午3:13:06
     * @Description: 申请出差。
     * @param pd
     * @return PageResult
     */
    PageResult applyEvection(PageData pd);

    /**
     * @Title: pmApprove
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午4:34:15
     * @Description: 项目经理审批
     * @param pd
     * @return PageResult
     */
    PageResult pmApprove(PageData pd);

    /**
     * @Title: gmApprove
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午4:34:15
     * @Description: 总项目经理审批
     * @param pd
     * @return PageResult
     */
    PageResult gmApprove(PageData pd);

    /**
     * @Title: personnelReport
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午4:36:55
     * @Description: 人事报备
     * @param pd
     * @return PageResult
     */
    PageResult personnelReport(PageData pd);

    /**
     * @Title: endEvection
     * @author: 吴佳隆
     * @data: 2021年3月31日 下午4:37:28
     * @Description: 结束出差
     * @param pd
     * @return PageResult
     */
    PageResult endEvection(PageData pd);

}