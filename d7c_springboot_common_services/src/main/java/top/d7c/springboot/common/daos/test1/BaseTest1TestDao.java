package top.d7c.springboot.common.daos.test1;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.context.BaseDao;
import top.d7c.springboot.common.dos.test1.Test1Test;

/**
 * @Title: BaseTest1TestDao
 * @Package: top.d7c.springboot.common.daos.test1
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:52:38
 * @Description: Test1Test基础 Dao
 */
@Repository(value = "baseTest1TestDao")
public interface BaseTest1TestDao extends BaseDao<Test1Test, Long> {

}