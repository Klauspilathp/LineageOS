package com.gnol.springboot.common.pojos;

import java.io.Serializable;
import java.util.Date;

import com.gnol.springboot.common.enums.sys.StatusEnum;
import com.gnol.springboot.common.pojos.sys.SysSession;

/**
 * @Title: BasePojo
 * @Package: com.gnol.springboot.common.pojos
 * @author: 吴佳隆
 * @date: 2019年5月21日 下午6:32:26
 * @Description: 基础 pojo，所有 pojo 都应该继承此类
 */
public abstract class BasePojo implements Serializable {
    private static final long serialVersionUID = -9172625740346288294L;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 添加人
     */
    private Long addUser;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人
     */
    private Long modifyUser;
    /**
     * 数据状态，0：删除，1：正常，2：冻结
     */
    private Integer status;
    /**
     * 数据当前版本
     */
    private Integer checkValue;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Long getAddUser() {
        return addUser;
    }

    public void setAddUser(Long addUser) {
        this.addUser = addUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(Long modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @Title: getStatus_Text
     * @author: 吴佳隆
     * @data: 2019年6月21日 下午6:14:53
     * @Description: 获取枚举类型的值
     * @return String
     */
    public String getStatus_Text() {
        return StatusEnum.getValue(this.status);
    }

    /**
     * @Title: setStatus_Enum
     * @author: 吴佳隆
     * @data: 2019年6月21日 下午6:15:25
     * @Description: 设置枚举值
     * @param status
     */
    public void setStatus_Enum(StatusEnum status) {
        this.status = status.getKey();
    }

    public Integer getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(Integer checkValue) {
        this.checkValue = checkValue;
    }

    /**
     * @Title: insertPre
     * @author: 吴佳隆
     * @data: 2019年6月16日 下午3:28:34
     * @Description: 插入前调用
     */
    public void insertPre(SysSession session) {
        this.setAddTime(new Date());
        this.setAddUser(session.getUserId());
        this.setStatus(StatusEnum.NORMAL.getKey());
    }

    /**
     * @Title: del
     * @author: 吴佳隆
     * @data: 2019年12月11日 上午9:03:55
     * @Description: 软删除数据
     * @param session
     */
    public void del(SysSession session) {
        this.setModifyTime(new Date());
        this.setModifyUser(session.getUserId());
        this.setStatus(StatusEnum.DELETE.getKey());
    }

    /**
     * @Title: updatePre
     * @author: 吴佳隆
     * @data: 2019年6月16日 下午3:34:30
     * @Description: 更新 pojo 前修改参数
     */
    public void updatePre(SysSession session) {
        this.setModifyTime(new Date());
        this.setModifyUser(session.getUserId());
    }

}