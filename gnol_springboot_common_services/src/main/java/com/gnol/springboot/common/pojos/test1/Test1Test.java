package com.gnol.springboot.common.pojos.test1;

import com.gnol.springboot.common.pojos.BasePojo;

/**
 * @Title: Test1Test
 * @Package: com.gnol.springboot.common.pojos.test1
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:52:38
 * @Description: Test1Test pojo
 */
public class Test1Test extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    private Integer tid;
    private String text;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static class __Names {
    	/**
         * 当前实体对应的数据库表名
         */
    	public final String TABLE_NAME = "test1_test";
        public final String tid = "tid";
        public final String text = "text";
    }

}