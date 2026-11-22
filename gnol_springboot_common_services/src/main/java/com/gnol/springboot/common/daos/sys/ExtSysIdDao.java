package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.springboot.common.pojos.sys.SysId;

/**
 * @Title: ExtSysIdDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2020年04月03日 12:15:02
 * @Description: gnol系统_主键操作扩展 Dao
 */
@Repository(value = "extSysIdDao")
public interface ExtSysIdDao {

    /**
     * @Title: getByKeyForUpdate
     * @author: 吴佳隆
     * @data: 2020年4月4日 上午10:12:15
     * @Description: 根据主键查询 pojo 并悲观加锁
     * @param idKey
     * @return SysId
     */
    SysId getByKeyForUpdate(String idKey);

}