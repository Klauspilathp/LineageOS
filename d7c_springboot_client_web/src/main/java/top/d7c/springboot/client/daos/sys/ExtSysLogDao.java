package top.d7c.springboot.client.daos.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;

/**
 * @Title: ExtSysLogDao
 * @Package: top.d7c.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: d7c 系统日志扩展 Dao
 */
@Repository(value = "extSysLogDao")
public interface ExtSysLogDao {

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午6:07:23
     * @Description: 根据条件分页查询日志列表
     * @param page
     * @return List<PageData>
     */
    List<PageData> listPDPage(Page<PageData> page);

}