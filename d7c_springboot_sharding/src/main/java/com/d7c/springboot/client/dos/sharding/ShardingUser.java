package com.d7c.springboot.client.dos.sharding;

import com.d7c.springboot.common.dos.BasePojo;

/**
 * @Title: ShardingUser
 * @Package: com.d7c.springboot.client.dos.sharding
 * @author: 吴佳隆
 * @date: 2020年08月03日 14:27:46
 * @Description: ShardingUser pojo
 */
public class ShardingUser extends BasePojo {
    private static final long serialVersionUID = 1L;
    public static __Names M = new __Names();
    private Long userId;
    private String userName;
    private Integer sex;

    public ShardingUser(Long userId, String userName, Integer sex) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.sex = sex;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public static class __Names {
        /**
         * 当前实体对应的数据库表名
         */
        public final String TABLE_NAME = "sharding_user";
        public final String userId = "userId";
        public final String userName = "userName";
        public final String sex = "sex";
    }

}