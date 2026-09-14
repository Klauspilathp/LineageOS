package com.gnol.springboot.common.daos.sys;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.context.BaseDao;
import com.gnol.springboot.common.pojos.sys.SysImg;

/**
 * @Title: BaseSysImgDao
 * @Package: com.gnol.springboot.common.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:30
 * @Description: gnol 系统_图片管理基础 Dao
 */
@Repository(value = "baseSysImgDao")
public interface BaseSysImgDao extends BaseDao<SysImg, Long> {

}