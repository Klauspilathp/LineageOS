package com.d7c.springboot.auth.feigns;

import org.springframework.cloud.openfeign.FeignClient;

import com.d7c.springboot.auth.mappings.AuthMapping;
import com.d7c.springboot.common.constant.ServerConstant;

/**
 * @Title: AuthFeignClient
 * @Package: com.d7c.springboot.auth.feigns
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午2:12:11
 * @Description: 认证 Feign 客户端
 */
@FeignClient(name = ServerConstant.AUTH)
public interface AuthFeignClient extends AuthMapping {

}