package top.d7c.springboot.client.services.evection.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.springboot.client.services.evection.EvectionFlowService;

/**
 * @Title: EvectionFlowServiceImpl
 * @Package: top.d7c.springboot.client.services.evection.impl
 * @author: 吴佳隆
 * @date: 2021年4月25日 下午3:19:40
 * @Description: 出差流程服务实现
 */
@Service(value = "evectionFlowServiceImpl")
public class EvectionFlowServiceImpl implements EvectionFlowService {
    private static final Logger logger = LoggerFactory.getLogger(EvectionFlowServiceImpl.class);

    @Override
    public PageResult applyEvection(PageData pd) {
        logger.debug("");
        return null;
    }

    @Override
    public PageResult pmApprove(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult gmApprove(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult personnelReport(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult endEvection(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult getFlowState(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult listPDPage(Page<PageData> page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageResult listEvectionHistoryFlow(PageData pd) {
        // TODO Auto-generated method stub
        return null;
    }

}
