package top.d7c.springboot.client.services.sys;

import java.util.List;

import top.d7c.plugins.core.Page;
import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.context.BaseService;
import top.d7c.springboot.client.dtos.tree.SelectTree;
import top.d7c.springboot.client.dtos.tree.ZTree;
import top.d7c.springboot.common.dos.sys.SysRole;

/**
 * @Title: SysRoleService
 * @Package: top.d7c.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:57
 * @Description: d7c 系统角色表 Service
 */
public interface SysRoleService extends BaseService<SysRole, Long> {

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
     * @Title: listZTreeByParentId_SYNC
     * @author: 吴佳隆
     * @data: 2020年4月21日 上午8:59:39
     * @Description: 根据父级角色编号查询子级角色列表树，ZTree3.5 同步实现方式
     * @param pd
     * @return List<ZTree>
     */
    List<ZTree> listZTreeByParentId_SYNC(PageData pd);

    /**
     * @Title: listTopSelectTreeBy
     * @author: 吴佳隆
     * @data: 2020年4月21日 下午6:44:28
     * @Description: 查询顶级角色列表
     * @return List<SelectTree>
     */
    List<SelectTree> listTopSelectTreeBy();

    /**
     * @Title: maxRoleId
     * @author: 吴佳隆
     * @data: 2020年4月19日 下午6:08:42
     * @Description: 获取当前用户最大角色编号
     * @param pd
     * @return Long
     */
    Long maxRoleId(PageData pd);

    /**
     * @Title: listPDPage
     * @author: 吴佳隆
     * @data: 2020年4月10日 下午6:07:23
     * @Description: 根据条件分页查询角色列表
     * @param page
     * @return PageResult
     */
    PageResult listPDPage(Page<PageData> page);

    /**
     * @Title: insertRole
     * @author: 吴佳隆
     * @data: 2020年4月16日 下午5:10:40
     * @Description: 新增角色
     * @param pd
     * @return PageResult
     */
    PageResult insertRole(PageData pd);

    /**
     * @Title: updateRole
     * @author: 吴佳隆
     * @data: 2020年4月16日 下午5:26:52
     * @Description: 修改角色
     * @param pd
     * @return PageResult
     */
    PageResult updateRole(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月16日 下午5:32:05
     * @Description: 软删除角色
     * @param pd
     * @return PageResult
     */
    PageResult updateStatus(PageData pd);

    /**
     * @Title: saveVerify
     * @author: 吴佳隆
     * @data: 2020年4月18日 上午10:10:07
     * @Description: 角色保存权限验证
     * @param pd
     * @return PageResult
     */
    PageResult saveVerify(PageData pd);

}