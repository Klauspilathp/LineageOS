package com.d7c.springboot.client.daos.test1;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;

/**
 * @Title: ExtTest1TestDao
 * @Package: com.d7c.springboot.client.daos.test1
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:52:38
 * @Description: Test1Test扩展 Dao
 */
@Repository(value = "extTest1TestDao")
public interface ExtTest1TestDao {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年07月04日 12:52:38
     * @Description: 根据条件分页查询列表
     * @param page
     * @return List<PageData>
     */
    List<PageData> listPDPage(Page<PageData> page);

}