package com.gnol.springboot.common.services.impl;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.gnol.plugins.core.StringUtil;
import com.gnol.springboot.common.dos.sys.SysId;
import com.gnol.springboot.common.services.AbstractIdService;
import com.gnol.springboot.common.services.SysIdService;

/**
 * @Title: DbIdServiceImpl
 * @Package: com.gnol.springboot.common.services.impl
 * @author: 吴佳隆
 * @date: 2020年4月3日 上午11:19:02
 * @Description: 基于数据库实现的 ID 生成服务实现
 */
@Service(value = "dbIdServiceImpl")
@Primary
public class DbIdServiceImpl extends AbstractIdService {
    /**
     * gnol系统_主键操作 Service 实现
     */
    @Resource(name = "sysIdServiceImpl")
    private SysIdService sysIdService;
    /**
     * 存储系统_主键操作实体的容器
     */
    private static ConcurrentHashMap<String, SysId> CONTAINER = null;

    public DbIdServiceImpl() {
        if (CONTAINER == null) {
            synchronized (this) {
                if (CONTAINER == null) {
                    CONTAINER = new ConcurrentHashMap<String, SysId>();
                }
            }
        }
    }

    @Override
    public long getLong(String idKey) {
        return this.getLong(idKey, 0);
    }

    @Override
    public long getLong(String idKey, int perNum) {
        if (StringUtil.isBlank(idKey)) {
            idKey = "default_db_id_key";
        }
        return this.advancedFetch(idKey, perNum);
    }

    /**
     * @Title: advancedFetch
     * @author: 吴佳隆
     * @data: 2020年4月3日 下午7:35:52
     * @Description: 根据 idKey 预先取出一个 long 类型数值
     * @param idKey     id 表操作的唯一主键，字符串类型，建议设置成表名
     * @param perNum    每次获取个数
     * @return SysId    返回的 long 类型数值
     */
    private synchronized Long advancedFetch(String idKey, int perNum) {
        SysId sysId = CONTAINER.get(idKey);
        if (sysId == null || sysId.getEndValue().compareTo(sysId.getCurrValue()) == -1) {
            sysId = this.sysIdService.updatePersistSysId(idKey, perNum);
            CONTAINER.put(idKey, sysId);
        }
        Long currValue = sysId.getCurrValue();
        sysId.setCurrValue(currValue + 1);
        return currValue;
    }

    @Override
    public String getString(String prefix) {
        // return MakeOrderNum.makeOrderNum(prefix);
        // return TSS.getTimeStampSequence(prefix);
        return prefix + this.getLong("default_db_id_key");
    }

}