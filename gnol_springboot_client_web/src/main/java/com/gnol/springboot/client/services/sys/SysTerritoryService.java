package com.gnol.springboot.client.services.sys;

import java.util.List;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.BaseService;
import com.gnol.springboot.client.dtos.tree.ZTree;
import com.gnol.springboot.common.dos.sys.SysTerritory;

/**
 * @Title: SysTerritoryService
 * @Package: com.gnol.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年06月13日 21:52:30
 * @Description: gnol 系统地域 Service
 */
public interface SysTerritoryService extends BaseService<SysTerritory, Long> {

    /**
     * @Title: listZTreeFormTreeFrameByParentId_ASYNC
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午12:48:53
     * @Description: 根据父级地域查询子级地域列表树，目标是 TreeFrame，ZTree3.6 异步实现方式
     * @param parentId      父级地域编号
     * @return List<ZTree>
     */
    List<ZTree> listZTreeFormTreeFrameByParentId_ASYNC(Long parentId);

    /**
     * @Title: listZTreeByParentId_ASYNC
     * @author: 吴佳隆
     * @data: 2020年4月21日 下午3:03:36
     * @Description: 根据父级地域查询子级地域列表树，ZTree3.6 异步实现方式
     * @param parentId
     * @return List<ZTree>
     */
    List<ZTree> listZTreeByParentId_ASYNC(Long parentId);

    /**
     * @Title: listZTreeFormTreeFrameByParentId_SYNC
     * @author: 吴佳隆
     * @data: 2020年4月21日 上午10:13:47
     * @Description: 根据父级地域编号查询子级地域列表树，目标是 TreeFrame，ZTree2.6 同步实现方式
     * @param parentId      父级地域编号
     * @return List<ZTree>
     */
    List<ZTree> listZTreeFormTreeFrameByParentId_SYNC(Long parentId);

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午6:07:23
     * @Description: 根据条件分页查询地域列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

    /**
     * @Title: insertTerritory
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午4:16:02
     * @Description: 新增地域
     * @param pd
     * @return PageResult
     */
    PageResult insertTerritory(PageData pd);

    /**
     * @Title: updateTerritory
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午4:33:43
     * @Description: 修改
     * @param pd
     * @return PageResult
     */
    PageResult updateTerritory(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午4:33:53
     * @Description: 软删除地域
     * @param pd
     * @return PageResult
     */
    PageResult updateStatus(PageData pd);

    /**
     * @Title: hasExist
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午6:27:06
     * @Description: 判断数据库中地域代码是否已经存在并且不是当前地域
     * @param pd
     * @return PageResult
     */
    PageResult hasExist(PageData pd);

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