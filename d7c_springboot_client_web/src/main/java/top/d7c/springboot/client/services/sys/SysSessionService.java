package top.d7c.springboot.client.services.sys;

import top.d7c.plugins.core.context.BaseService;
import top.d7c.springboot.common.dos.sys.SysSession;

/**
 * @Title: SysSessionService
 * @Package: top.d7c.springboot.client.services.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 08:56:45
 * @Description: d7c 系统_用户详情权限临时表，用户在登录，下线等情况下触发插入、删除、更新操作，然后以此表关联业务表查询指定范围内的 Service
 */
public interface SysSessionService extends BaseService<SysSession, Long> {

    /**
     * @Title: insertReplace
     * @author: 吴佳隆
     * @data: 2019年7月3日 下午1:33:19
     * @Description: 插入更新数据
     * @param sysSession
     * @return int
     */
    int insertReplace(SysSession sysSession);

    /**
     * @Title: updateUserAddress
     * @author: 吴佳隆
     * @data: 2019年7月4日 下午3:09:54
     * @Description: 更新用户地址
     * @param session
     * @return SysSession
     */
    SysSession updateUserAddress(SysSession session);

    /**
     * @Title: getMenuQXBySessionId
     * @author: 吴佳隆
     * @data: 2020年5月14日 上午9:10:33
     * @Description: 根据会话编号获取菜单权限
     * @param sessionId
     * @return String
     */
    String getMenuQXBySessionId(String sessionId);

    /**
     * @Title: getUserIdBySessionId
     * @author: 吴佳隆
     * @data: 2020年5月14日 上午9:38:30
     * @Description: 根据会话编号获取用户编号
     * @param sessionId 会话编号
     * @return Long
     */
    Long getUserIdBySessionId(String sessionId);

}