package com.d7c.springboot.common.dos;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.d7c.plugins.core.StringUtil;
import com.d7c.plugins.core.context.SpringContextHolder;
import com.d7c.plugins.core.exception.D7cRuntimeException;
import com.d7c.plugins.tools.date.TimeConstant;
import com.d7c.springboot.common.daos.sys.BaseSysUserDao;
import com.d7c.springboot.common.enums.sys.UserTypeEnum;

/**
 * @Title: D7cSession
 * @Package: com.d7c.springboot.common.dos
 * @author: 吴佳隆
 * @date: 2020年4月5日 上午9:32:34
 * @Description: 系统 SESSION 基类
 */
public abstract class D7cSession implements Serializable {
    private static final long serialVersionUID = 7860716866132026281L;
    /**
     * 会话编号
     */
    protected String sessionId;
    /**
     * 访客主键，即登录用户的主键
     */
    protected long visitorId;
    /**
     * 访客名称
     */
    protected String visitorName;
    /**
     * 用户类别，用户获取登录用户对象
     */
    protected int userType;
    /**
     * 请求来源，用于控制 SESSION 失效时间
     */
    protected String source;
    /**
     * 会话启动时间
     */
    protected Date startTimestamp;
    /**
     * 返回会话在到期之前可能保持空闲的时间(以毫秒为单位)
     */
    protected long timeout;
    /**
     * 最近一次访问时间
     */
    protected Date lastAccessTime;
    /**
     * 会话过期时间
     */
    protected Date expireTimestamp;

    public D7cSession() {
        this.startTimestamp = new Date();
        this.timeout = TimeConstant.HOUR_MILLIS;
        this.lastAccessTime = this.startTimestamp;
        this.expireTimestamp = new Date(this.startTimestamp.getTime() + this.timeout);
    }

    public D7cSession(String sessionId, long visitorId, String visitorName, int userType, String source,
            long timeout) {
        if (visitorId <= 0) {
            throw new D7cRuntimeException("SESSION 创建失败！");
        }
        if (StringUtil.isBlank(sessionId)) {
            this.sessionId = UUID.randomUUID().toString();
        } else {
            this.sessionId = sessionId;
        }
        this.userType = userType;
        if (getUserTypeEnum() == null) {
            throw new D7cRuntimeException("SESSION 创建失败，用户类型不存在！");
        }
        this.visitorId = visitorId;
        this.visitorName = visitorName;
        this.source = source;
        this.startTimestamp = new Date();
        if (timeout <= 0) {
            this.timeout = TimeConstant.HOUR_MILLIS;
        } else {
            this.timeout = timeout;
        }
        this.lastAccessTime = this.startTimestamp;
        this.expireTimestamp = new Date(this.startTimestamp.getTime() + this.timeout);
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getVisitorId() {
        return visitorId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public int getUserType() {
        return userType;
    }

    public UserTypeEnum getUserTypeEnum() {
        return UserTypeEnum.forKey(this.userType);
    }

    public String getSource() {
        return source;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getExpireTimestamp() {
        return expireTimestamp;
    }

    public void setExpireTimestamp(Date expireTimestamp) {
        this.expireTimestamp = expireTimestamp;
    }

    /**
     * @Title: getVisitor
     * @author: 吴佳隆
     * @data: 2020年4月5日 上午10:59:11
     * @Description: 获取访问者对象
     * @return Object
     */
    public Object getVisitor() {
        Object visitor = null;
        switch (this.getUserTypeEnum()) {
            case SYSTEM_USER:
            case TERRITORY_USER:
            case ORG_USER:
                BaseSysUserDao sysUserDao = SpringContextHolder.getBean("baseSysUserDao", BaseSysUserDao.class);
                visitor = sysUserDao.getByKey(visitorId);
                break;
            case MEMBER_USER:

                break;
            case WEIXIN_USER:
            case WEIXIN_MINI_USER:
            case ALI_USER:
            case ALI_MINI_USER:

                break;
            default:
                break;
        }
        return visitor;
    }

    /**
     * @Title: touch
     * @author: 吴佳隆
     * @data: 2020年4月5日 上午11:00:50
     * @Description: 更新会话最后一次访问时间
     */
    public void touch() {
        this.lastAccessTime = new Date();
        this.expireTimestamp = new Date(this.lastAccessTime.getTime() + this.timeout);
    }

    /**
     * @Title: isValid
     * @author: 吴佳隆
     * @data: 2020年4月5日 上午11:06:31
     * @Description: 会话是否有效
     * @return boolean  有效返回 true
     */
    public boolean isValid() {
        return expireTimestamp != null && expireTimestamp.before(new Date());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
        result = prime * result + (int) (visitorId ^ (visitorId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof D7cSession) {
            D7cSession other = (D7cSession) obj;
            Serializable thisId = getSessionId();
            Serializable otherId = other.getSessionId();
            return thisId != null && otherId != null && thisId.equals(otherId);
        }
        return false;
    }

}