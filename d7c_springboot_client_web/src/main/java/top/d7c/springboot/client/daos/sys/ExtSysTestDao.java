package top.d7c.springboot.client.daos.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;

/**
 * @Title: ExtSysTestDao
 * @Package: top.d7c.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:42:01
 * @Description: SysTest扩展 Dao
 */
@Repository(value = "extSysTestDao")
public interface ExtSysTestDao {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年07月04日 12:42:01
     * @Description: 根据条件分页查询列表
     * @param page
     * @return List<PageData>
     */
    List<PageData> listPDPage(Page<PageData> page);

}