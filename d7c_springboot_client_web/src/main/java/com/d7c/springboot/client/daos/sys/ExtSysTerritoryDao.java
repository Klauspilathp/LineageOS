package com.d7c.springboot.client.daos.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.springboot.client.dtos.tree.ZTree;

/**
 * @Title: ExtSysTerritoryDao
 * @Package: com.d7c.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: d7c 系统地域扩展 Dao
 */
@Repository(value = "extSysTerritoryDao")
public interface ExtSysTerritoryDao {

    /**
     * @Title: listZTreeFormTreeFrameByParentId
     * @author: 吴佳隆
     * @data: 2020年4月7日 下午2:55:38
     * @Description: 根据父级地域查询所有子级地域列表，目标是 TreeFrame
     * @param parentId      父级地域编号
     * @return List<ZTree>
     */
    List<ZTree> listZTreeFormTreeFrameByParentId(Long parentId);

    /**
     * @Title: listZTreeByParentId
     * @author: 吴佳隆
     * @data: 2020年4月21日 下午3:04:37
     * @Description: 根据父级地域查询所有子级地域列表
     * @param parentId
     * @return List<ZTree>
     */
    List<ZTree> listZTreeByParentId(Long parentId);

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午6:07:23
     * @Description: 根据条件分页查询地域列表
     * @param page
     * @return List<PageData>
     */
    List<PageData> listPDPage(Page<PageData> page);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月9日 上午8:34:06
     * @Description: 根据地域编号软删除地域
     * @param pd
     * @return int
     */
    int updateStatus(PageData pd);

    /**
     * @Title: hasExist
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午6:29:22
     * @Description: 判断数据库中地域代码是否已经存在并且不是当前地域
     * @param pd
     * @return int
     */
    int hasExist(PageData pd);

    /**
     * @Title: hasSon
     * @author: 吴佳隆
     * @data: 2020年4月15日 上午8:41:26
     * @Description: 根据父节点查询是否有子节点
     * @param pd
     * @return int
     */
    int hasSon(PageData pd);

    /**
     * @Title: getTerritoryNameCNById
     * @author: 吴佳隆
     * @data: 2020年4月16日 上午8:13:03
     * @Description: 根据主键获取地域中文名称
     * @param territoryId   地域主键
     * @return String
     */
    String getTerritoryNameCNById(Long territoryId);

}