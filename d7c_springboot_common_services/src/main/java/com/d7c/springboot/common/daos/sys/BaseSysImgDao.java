package com.d7c.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.context.BaseDao;
import com.d7c.springboot.common.dos.sys.SysImg;

/**
 * @Title: BaseSysImgDao
 * @Package: com.d7c.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:30
 * @Description: d7c 系统_图片管理基础 Dao
 */
@Repository(value = "baseSysImgDao")
public interface BaseSysImgDao extends BaseDao<SysImg, Long> {

}