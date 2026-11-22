package com.gnol.springboot.common.feigns.auth;

import org.springframework.cloud.openfeign.FeignClient;

import com.gnol.springboot.common.constant.ServerConstant;
import com.gnol.springboot.common.mappings.auth.AuthMapping;

/**
 * @Title: AuthFeignClient
 * @Package: com.gnol.springboot.common.feigns.auth
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午2:12:11
 * @Description: 认证 Feign 客户端
 */
@FeignClient(name = ServerConstant.AUTH)
public interface AuthFeignClient extends AuthMapping {

}