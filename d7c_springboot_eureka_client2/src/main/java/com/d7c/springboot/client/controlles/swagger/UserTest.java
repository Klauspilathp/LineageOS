package com.d7c.springboot.client.controlles.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Title: UserTest
 * @Package: com.d7c.springboot.client.controlles.swagger
 * @author: 吴佳隆
 * @date: 2019年7月29日 下午7:05:57
 * @Description: swagger 测试实体
 */
@ApiModel(value = "用户信息")
public class UserTest {
    @ApiModelProperty(required = true, value = "用户主键")
    private Long userId;
    @ApiModelProperty(required = true, value = "用户昵称")
    private String username;
    @ApiModelProperty(required = true, value = "用户状态。0启用，1禁用")
    private Integer userStatus;

    public UserTest(long userId, String username, Integer userStatus) {
        super();
        this.userId = userId;
        this.username = username;
        this.userStatus = userStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

}