package com.gnol.springboot.common.pojos.sys;

import com.gnol.springboot.common.pojos.BasePojo;

/**
 * @Title: SysImg
 * @Package: com.gnol.springboot.common.pojos.sys
 * @author: 吴佳隆
 * @date: 2019年06月18日 09:13:40
 * @Description: gnol 系统_图片管理 pojo
 */
public class SysImg extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    /**
     * 主键
     */
    private Long imgId;
    /**
     * 图片名
     */
    private String imgName;
    /**
     * 图片路径
     */
    private String imgPath;
    /**
     * 图片类型
     */
    private String imgType;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;

    public Long getImgId() {
        return imgId;
    }

    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static class __Names {
        /**
         * 当前实体对应的数据库表名
         */
        public final String TABLE_NAME = "sys_img";
        /**
         * 主键
         */
        public final String imgId = "imgId";
        /**
         * 图片名
         */
        public final String imgName = "imgName";
        /**
         * 图片路径
         */
        public final String imgPath = "imgPath";
        /**
         * 图片类型
         */
        public final String imgType = "imgType";
        /**
         * 排序
         */
        public final String sort = "sort";
        /**
         * 备注
         */
        public final String remark = "remark";
        /**
         * 添加时间
         */
        public final String addTime = "addTime";
        /**
         * 添加人
         */
        public final String addUser = "addUser";
        /**
         * 修改时间
         */
        public final String modifyTime = "modifyTime";
        /**
         * 修改人
         */
        public final String modifyUser = "modifyUser";
        /**
         * 数据状态，0：删除，1：正常，2：冻结
         */
        public final String status = "status";
    }

}