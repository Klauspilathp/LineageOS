package com.gnol.springboot.client.daos.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.PageData;
import com.gnol.springboot.client.dtos.tree.SelectTree;
import com.gnol.springboot.client.dtos.tree.ZTree;

/**
 * @Title: ExtSysOrgDao
 * @Package: com.gnol.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: gnol 系统组织机构扩展 Dao
 */
@Repository(value = "extSysOrgDao")
public interface ExtSysOrgDao {

    /**
     * @Title: listZTreeFormTreeFrameByParentId
     * @author: 吴佳隆
     * @data: 2020年4月7日 下午2:55:38
     * @Description: 根据父级组织机构查询所有子级组织机构列表，目标是 TreeFrame
     * @param parentId      父级组织机构编号
     * @return List<ZTree>
     */
    List<ZTree> listZTreeFormTreeFrameByParentId(Long parentId);

    /**
     * @Title: listPDBy
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午6:07:23
     * @Description: 根据条件查询组织机构列表
     * @param pd
     * @return List<PageData>
     */
    List<PageData> listPDBy(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月9日 上午8:34:06
     * @Description: 根据组织机构编号软删除组织机构
     * @param pd
     * @return int
     */
    int updateStatus(PageData pd);

    /**
     * @Title: hasExist
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午6:29:22
     * @Description: 判断数据库中组织机构代码是否已经存在并且不是当前组织机构
     * @param pd
     * @return int
     */
    int hasExist(PageData pd);

    /**
     * @Title: hasSon
     * @author: 吴佳隆
     * @data: 2020年4月15日 上午8:41:26
     * @Description: 根据父节点编号查询是否有子组织机构
     * @param pd
     * @return int
     */
    int hasSon(PageData pd);

    /**
     * @Title: listSelectTreeBy
     * @author: 吴佳隆
     * @data: 2020年4月21日 下午6:43:24
     * @Description: SelectTree 下拉树
     * @param pd
     * @return List<SelectTree>
     */
    List<SelectTree> listSelectTreeBy(PageData pd);

    /**
     * @Title: getOrgInfoByUserId
     * @author: 吴佳隆
     * @data: 2020年4月27日 上午9:00:35
     * @Description: 根据用户编号查询该用户组织机构信息
     * @param userId
     * @return PageData
     */
    PageData getOrgInfoByUserId(Long userId);

    /**
     * @Title: getOrgInfoByOrgId
     * @author: 吴佳隆
     * @data: 2020年4月27日 上午9:00:35
     * @Description: 根据组织机构编号查询组织机构信息
     * @param orgId
     * @return PageData
     */
    PageData getOrgInfoByOrgId(Long orgId);

}