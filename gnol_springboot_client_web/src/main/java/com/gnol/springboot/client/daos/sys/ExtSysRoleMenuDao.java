package com.gnol.springboot.client.daos.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.PageData;
import com.gnol.springboot.client.dtos.tree.ZTree;

/**
 * @Title: ExtSysRoleMenuDao
 * @Package: com.gnol.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: gnol 系统角色菜单表扩展 Dao
 */
@Repository(value = "extSysRoleMenuDao")
public interface ExtSysRoleMenuDao {

    /**
     * @Title: listAuthByRoleId
     * @author: 吴佳隆
     * @data: 2020年4月16日 下午7:50:20
     * @Description: 根据角色编号查询角色权限，查询出全部菜单树，有权限的选中
     * @param pd
     * @return List<ZTree>
     */
    List<ZTree> listAuthByRoleId(PageData pd);

}