package top.d7c.springboot.client.services.sys;

import java.util.List;

import top.d7c.plugins.core.PageData;
import top.d7c.plugins.core.PageResult;
import top.d7c.plugins.core.context.BaseService;
import top.d7c.springboot.client.dtos.tree.SelectTree;
import top.d7c.springboot.client.dtos.tree.ZTree;
import top.d7c.springboot.common.dos.sys.SysOrg;

/**
 * @Title: SysOrgService
 * @Package: top.d7c.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:57:49
 * @Description: d7c 系统组织机构 Service
 */
public interface SysOrgService extends BaseService<SysOrg, Long> {

    /**
     * @Title: listZTreeFormTreeFrameByParentId_SYNC
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午12:48:53
     * @Description: 根据父级组织机构编号查询子级组织机构列表树，目标是 TreeFrame，ZTree2.6 同步实现方式
     * @param pd
     * @return List<ZTree>
     */
    List<ZTree> listZTreeFormTreeFrameByParentId_SYNC(PageData pd);

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
     * @Title: insertOrg
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午4:16:02
     * @Description: 新增组织机构
     * @param pd
     * @return PageResult
     */
    PageResult insertOrg(PageData pd);

    /**
     * @Title: updateOrg
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午4:33:43
     * @Description: 修改组织机构
     * @param pd
     * @return PageResult
     */
    PageResult updateOrg(PageData pd);

    /**
     * @Title: saveVerify
     * @author: 吴佳隆
     * @data: 2020年4月18日 上午10:10:07
     * @Description: 组织机构保存权限验证
     * @param pd
     * @return PageResult
     */
    PageResult saveVerify(PageData pd);

    /**
     * @Title: updateStatus
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午4:33:53
     * @Description: 软删除组织机构
     * @param pd
     * @return PageResult
     */
    PageResult updateStatus(PageData pd);

    /**
     * @Title: hasExist
     * @author: 吴佳隆
     * @data: 2020年4月14日 下午6:27:06
     * @Description: 判断数据库中组织机构代码是否已经存在并且不是当前组织机构
     * @param pd
     * @return PageResult
     */
    PageResult hasExist(PageData pd);

    /**
     * @Title: maxOrgId
     * @author: 吴佳隆
     * @data: 2020年4月19日 下午6:08:42
     * @Description: 获取当前用户最大的组织机构编号
     * @param pd
     * @return Long
     */
    Long maxOrgId(PageData pd);

    /**
     * @Title: listSelectTreeByParentId
     * @author: 吴佳隆
     * @data: 2020年4月22日 下午2:43:37
     * @Description: 查询组织机构列表
     * @param pd
     * @return List<SelectTree>
     */
    List<SelectTree> listSelectTreeByParentId(PageData pd);

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