package com.gnol.springboot.client.daos.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.Page;
import com.gnol.plugins.core.PageData;
import com.gnol.springboot.client.dtos.tree.SelectTree;
import com.gnol.springboot.client.dtos.tree.ZTree;

/**
 * @Title: ExtSysRoleDao
 * @Package: com.gnol.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: gnol 系统角色表扩展 Dao
 */
@Repository(value = "extSysRoleDao")
public interface ExtSysRoleDao {

    /**
     * @Title: getAllParentRoleName
     * @author: 吴佳隆
     * @data: 2019年7月2日 下午8:45:58
     * @Description: 根据角色编号查询当前角色及全部上级角色名称
     * @param roleId
     * @return PageData
     */
    PageData getAllParentRoleName(Long roleId);

    /**
     * @Title: listZTreeByParentId
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午5:27:13
     * @Description: zTree 角色树
     * @param parentId
     * @return List<ZTree>
     */
    List<ZTree> listZTreeByParentId(Long parentId);

    /**
     * @Title: listSelectTreeByParentId
     * @author: 吴佳隆
     * @data: 2020年4月21日 下午6:43:24
     * @Description: SelectTree 下拉树
     * @param parentId
     * @return List<SelectTree>
     */
    List<SelectTree> listSelectTreeByParentId(Long parentId);

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午6:07:23
     * @Description: 根据条件分页查询角色列表
     * @param page
     * @return List<PageData>
     */
    List<PageData> listPDPage(Page<PageData> page);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月9日 上午8:34:06
     * @Description: 根据角色编号软删除角色
     * @param pd
     * @return int
     */
    int updateStatus(PageData pd);

    /**
     * @Title: getRoleInfoByUserId
     * @author: 吴佳隆
     * @data: 2020年4月26日 下午6:45:51
     * @Description: 根据用户编号查询该用户角色信息
     * @param userId
     * @return PageData
     */
    PageData getRoleInfoByUserId(Long userId);

}