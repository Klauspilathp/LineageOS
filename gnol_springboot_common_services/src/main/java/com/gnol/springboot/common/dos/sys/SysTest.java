package com.gnol.springboot.common.dos.sys;

import com.gnol.springboot.common.dos.BasePojo;

/**
 * @Title: SysTest
 * @Package: com.gnol.springboot.common.dos.sys
 * @author: 吴佳隆
 * @date: 2020年07月04日 12:42:01
 * @Description: SysTest pojo
 */
public class SysTest extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class __Names {
    	/**
         * 当前实体对应的数据库表名
         */
    	public final String TABLE_NAME = "sys_test";
        public final String id = "id";
        public final String name = "name";
    }

}