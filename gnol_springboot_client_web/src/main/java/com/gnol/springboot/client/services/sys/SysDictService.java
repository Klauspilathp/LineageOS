package com.gnol.springboot.client.services.sys;

import java.util.List;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.plugins.core.PageResult;
import com.gnol.plugins.core.context.BaseService;
import com.gnol.springboot.client.bos.tree.ZTree;
import com.gnol.springboot.common.dos.sys.SysDict;

/**
 * @Title: SysDictService
 * @Package: com.gnol.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年06月17日 19:40:37
 * @Description: gnol 系统字典 Service
 */
public interface SysDictService extends BaseService<SysDict, Long> {

    /**
     * @Title: listZTreeFormTreeFrameByParentId_ASYNC
     * @author: 吴佳隆
     * @data: 2020年4月18日 下午5:55:59
     * @Description: 根据父级字典查询它的子级字典列表树，目标是 TreeFrame，ZTree3.6 异步实现方式
     * @param pd            查询条件
     * @return List<ZTree>
     */
    List<ZTree> listZTreeFormTreeFrameByParentId_ASYNC(PageData pd);

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年4月18日 下午6:06:23
     * @Description: 分页查询字典列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

    /**
     * @Title: insertDict
     * @author: 吴佳隆
     * @data: 2020年4月19日 上午11:45:17
     * @Description: 新增字典
     * @param pd
     * @return PageResult
     */
    PageResult insertDict(PageData pd);

    /**
     * @Title: updateDict
     * @author: 吴佳隆
     * @data: 2020年4月19日 上午11:45:22
     * @Description: 修改字典
     * @param pd
     * @return PageResult
     */
    PageResult updateDict(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月19日 上午11:45:27
     * @Description: 软删除字典
     * @param pd
     * @return PageResult
     */
    PageResult updateStatus(PageData pd);

    /**
     * @Title: hasExist
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午6:27:06
     * @Description: 判断数据库中 dictType、dictKey 是否已经存在并且不是当前字典
     * @param pd
     * @return PageResult
     */
    PageResult hasExist(PageData pd);

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