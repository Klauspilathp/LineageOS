package top.d7c.springboot.client.services.sys;

import java.util.List;

import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.context.BaseService;
import top.d7c.springboot.client.dtos.tree.ZTree;
import top.d7c.springboot.common.dos.sys.SysRoleMenu;

/**
 * @Title: SysRoleMenuService
 * @Package: top.d7c.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:49
 * @Description: d7c 系统角色菜单表 Service
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenu, Long> {

    /**
     * @Title: listAuthByRoleId
     * @author: 吴佳隆
     * @data: 2020年4月16日 下午7:50:20
     * @Description: 根据角色编号查询角色权限，查询出全部菜单树，有权限的选中，采用 Ztree2.6 同步实现方式
     * @param pd
     * @return List<ZTree>
     */
    List<ZTree> listAuthByRoleId(PageData pd);

    /**
     * @Title: saveAuth
     * @author: 吴佳隆
     * @data: 2020年4月17日 下午7:50:54
     * @Description: 保存权限
     * @param pd
     * @return PageResult
     */
    PageResult saveAuth(PageData pd);

}