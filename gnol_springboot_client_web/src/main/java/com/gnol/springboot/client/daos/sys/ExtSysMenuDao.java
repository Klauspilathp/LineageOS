package com.gnol.springboot.client.daos.sys;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.gnol.plugins.core.PageData;
import com.gnol.springboot.client.dtos.tree.MenuTree;
import com.gnol.springboot.client.dtos.tree.ZTree;

/**
 * @Title: ExtSysMenuDao
 * @Package: com.gnol.springboot.client.daos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:22
 * @Description: gnol 系统菜单表扩展 Dao
 */
@Repository(value = "extSysMenuDao")
public interface ExtSysMenuDao {

    /**
     * @Title: listPermissionsByRoleId
     * @author: 吴佳隆
     * @data: 2019年7月1日 下午4:01:08
     * @Description: 根据角色编号查询权限列表
     * @param roleId
     * @return Set<SimpleGrantedAuthority>
     */
    Set<SimpleGrantedAuthority> listPermissionsByRoleId(Long roleId);

    /**
     * @Title: listPermissionsBySessionId
     * @author: 吴佳隆
     * @data: 2019年7月1日 下午4:01:08
     * @Description: 根据sessionId 查询权限列表
     * @param sessionId
     * @return Set<String>
     */
    Set<String> listPermissionsBySessionId(String sessionId);

    /**
     * @Title: listMenuTreeByParentId
     * @author: 吴佳隆
     * @data: 2019年7月1日 下午5:35:15
     * @Description: 根据父编号查询子菜单树
     * @param parentId          父级菜单编号
     * @return List<MenuTree>
     */
    List<MenuTree> listMenuTreeByParentId(Integer parentId);

    /**
     * @Title: listMenuTreeByRoleId
     * @author: 吴佳隆
     * @data: 2020年7月17日 下午7:47:16
     * @Description: 根据角色编号查询菜单树
     * @param roleId            角色编号
     * @return List<MenuTree>
     */
    List<MenuTree> listMenuTreeByRoleId(Long roleId);

    /**
     * @Title: listZTreeFormTreeFrameByParentId
     * @author: 吴佳隆
     * @data: 2020年4月7日 下午2:55:38
     * @Description: 根据父级菜单编号查询所有直接子菜单列表，目标是 TreeFrame
     * @param parentId      父级菜单编号
     * @return List<ZTree>
     */
    List<ZTree> listZTreeFormTreeFrameByParentId(Integer parentId);

    /**
     * @Title: listMenuByParentId
     * @author: 吴佳隆
     * @data: 2020年4月9日 下午3:10:41
     * @Description: 根据父菜单编号查询所有子菜单
     * @param parentId
     * @return List<PageData>
     */
    List<PageData> listMenuByParentId(Integer parentId);

    /**
     * @Title: updateIcon
     * @author: 吴佳隆
     * @data: 2020年4月9日 上午8:15:35
     * @Description: 修改菜单图标
     * @param pd
     * @return int
     */
    int updateIcon(PageData pd);

    /**
     * @Title: hasSon
     * @author: 吴佳隆
     * @data: 2020年4月9日 上午8:33:51
     * @Description: 根据父级菜单编号查询其下是否有子菜单
     * @param parentId  父级菜单编号
     * @return int
     */
    int hasSon(Integer parentId);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月9日 上午8:34:06
     * @Description: 根据菜单编号软删除菜单
     * @param pd
     * @return int
     */
    int updateStatus(PageData pd);

}