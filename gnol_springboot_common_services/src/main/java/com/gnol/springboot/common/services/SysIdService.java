package com.gnol.springboot.common.services;

import com.gnol.plugins.core.context.BaseService;
import com.gnol.springboot.common.dos.sys.SysId;

/**
 * @Title: SysIdService
 * @Package: com.gnol.springboot.common.services
 * @author: 吴佳隆
 * @date: 2020年04月03日 12:15:02
 * @Description: gnol系统_主键操作 Service
 */
public interface SysIdService extends BaseService<SysId, String> {

    /**
     * @Title: getByKeyForUpdate
     * @author: 吴佳隆
     * @data: 2020年4月4日 上午10:12:15
     * @Description: 根据主键查询 pojo 并悲观加锁
     * @param idKey
     * @return SysId
     */
    SysId getByKeyForUpdate(String idKey);

    /**
     * @Title: updatePersistSysId
     * @author: 吴佳隆
     * @data: 2020年4月4日 上午9:38:04
     * @Description: 持久化一个 com.gnol.springboot.common.pojos.sys.SysId 对象
     * @param idKey     id 表操作的唯一主键，字符串类型，建议设置成表名
     * @param perNum    每次获取个数
     * @return SysId    从数据库获取到的 com.gnol.springboot.common.pojos.sys.SysId 对象
     */
    SysId updatePersistSysId(String idKey, int perNum);

}