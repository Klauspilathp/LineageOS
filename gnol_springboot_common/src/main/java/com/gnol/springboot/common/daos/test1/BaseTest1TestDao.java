package com.gnol.springboot.common.daos.test1;

import org.springframework.stereotype.Repository;

import com.gnol.springboot.common.pojos.test1.Test1Test;
import com.gnol.plugins.core.context.BaseDao;

/**
 * @Title: BaseTest1TestDao
 * @Package: com.gnol.springboot.common.daos.test1
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:52:38
 * @Description: Test1Test基础 Dao
 */
@Repository(value = "baseTest1TestDao")
public interface BaseTest1TestDao extends BaseDao<Test1Test, Long> {

}