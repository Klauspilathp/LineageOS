package com.d7c.springboot.client.daos.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.d7c.plugins.core.Page;
import com.d7c.plugins.core.PageData;
import com.d7c.springboot.client.dtos.tree.ZTree;

/**
 * @Title: ExtSysDictDao
 * @Package: com.d7c.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月17日 19:40:37
 * @Description: d7c 系统字典扩展 Dao
 */
@Repository(value = "extSysDictDao")
public interface ExtSysDictDao {

    /**
     * @Title: listZTreeFormTreeFrameByParentId
     * @author: 吴佳隆
     * @data: 2020年4月18日 下午6:09:17
     * @Description: 根据父级字典查询它的子级字典列表，目标是 TreeFrame
     * @param pd            查询条件
     * @return List<ZTree>
     */
    List<ZTree> listZTreeFormTreeFrameByParentId(PageData pd);

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年4月18日 下午6:08:06
     * @Description: 根据条件分页查询字典列表
     * @param page
     * @return List<PageData>
     */
    List<PageData> listPDPage(Page<PageData> page);

    /**
     * @Title: hasExist
     * @author: 吴佳隆
     * @data: 2020年4月19日 下午12:06:02
     * @Description: 判断数据库中 dictType、dictKey 是否已经存在并且不是当前字典
     * @param pd
     * @return int
     */
    int hasExist(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月19日 下午12:10:51
     * @Description: 根据字典编号软删除字典
     * @param pd
     * @return int
     */
    int updateStatus(PageData pd);

    /**
     * @Title: hasSon
     * @author: 吴佳隆
     * @data: 2020年4月19日 下午12:10:56
     * @Description: 根据父节点查询是否有子节点
     * @param pd
     * @return int
     */
    int hasSon(PageData pd);

    /**
     * @Title: listDictByDictType
     * @author: 吴佳隆
     * @data: 2020年4月24日 下午5:39:25
     * @Description: 根据字典类型查询字典列表
     * @param pd
     * @return List<PageData>
     */
    List<PageData> listDictByDictType(PageData pd);

}