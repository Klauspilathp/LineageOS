package com.gnol.springboot.client.daos.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.PageData;

/**
 * @Title: ExtSysImgDao
 * @Package: com.gnol.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: gnol 系统_图片管理扩展 Dao
 */
@Repository(value = "extSysImgDao")
public interface ExtSysImgDao {

    /**
     * @Title: listByImgType
     * @author: 吴佳隆
     * @data: 2020年4月28日 下午5:07:41
     * @Description: 根据图片类型查询图片列表
     * @param pd
     * @return List<PageData>
     */
    List<PageData> listByImgType(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月28日 下午7:25:37
     * @Description: 根据图片管理编号软删除图片
     * @param pd
     * @return int
     */
    int updateStatus(PageData pd);

}