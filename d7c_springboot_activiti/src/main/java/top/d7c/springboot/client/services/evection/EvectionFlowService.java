package top.d7c.springboot.client.services.evection;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;

/**
 * @Title: EvectionFlowService
 * @Package: top.d7c.springboot.client.services.evection
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

    /**
     * @Title: getFlowState
     * @author: 吴佳隆
     * @data: 2021年4月1日 上午8:37:53
     * @Description: 获取当前流程状态
     * @param pd
     * @return PageResult
     */
    PageResult getFlowState(PageData pd);

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2021年4月1日 上午8:43:50
     * @Description: 根据条件分页查询出差列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

    /**
     * @Title: listEvectionHistoryFlow
     * @author: 吴佳隆
     * @data: 2021年4月1日 上午8:50:30
     * @Description: 获取出差历史流程列表
     * @param pd
     * @return PageResult
     */
    PageResult listEvectionHistoryFlow(PageData pd);

}