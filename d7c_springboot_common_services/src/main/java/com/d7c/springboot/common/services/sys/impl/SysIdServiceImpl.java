package com.d7c.springboot.common.services.sys.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.context.AbstractBaseService;
import com.d7c.springboot.common.daos.sys.BaseSysIdDao;
import com.d7c.springboot.common.daos.sys.ExtSysIdDao;
import com.d7c.springboot.common.dos.sys.SysId;
import com.d7c.springboot.common.services.sys.SysIdService;

/**
 * @Title: SysIdServiceImpl
 * @Package: com.d7c.springboot.common.services.sys.impl
 * @author: 吴佳隆
 * @date: 2020年04月03日 12:15:02
 * @Description: d7c系统_主键操作 Service 实现
 */
@Service(value = "sysIdServiceImpl")
public class SysIdServiceImpl extends AbstractBaseService<BaseSysIdDao, SysId, String> implements SysIdService {
    /**
     * d7c系统_主键操作 扩展 Dao
     */
    @Resource(name = "extSysIdDao")
    private ExtSysIdDao sysIdDao;
    /**
     * 默认每次预取出的主键数量
     */
    private static final int FEFAULT_PRE_NUM = 100;

    @Override
    public SysId getByKeyForUpdate(String idKey) {
        if (StringUtil.isBlank(idKey)) {
            return null;
        }
        return sysIdDao.getByKeyForUpdate(idKey);
    }

    @Override
    public SysId updatePersistSysId(String idKey, int perNum) {
        SysId sysId = sysIdDao.getByKeyForUpdate(idKey);
        if (sysId == null) { // 以前没有进行过持久化操作
            sysId = new SysId(idKey, 1L, perNum > 0 ? perNum : FEFAULT_PRE_NUM);
            this.insertRollBack(sysId);
            // 对数据进行精简
            sysId.setRemark(null);
            sysId.setAddTime(null);
        } else { // 以前已经获取过，重新获取
            Long endValue = sysId.getEndValue();
            sysId.setCurrValue(endValue + 1);
            sysId.setEndValue(endValue + sysId.getPerNum());
            this.updateRollBack(sysId);
        }
        return sysId;
    }

}