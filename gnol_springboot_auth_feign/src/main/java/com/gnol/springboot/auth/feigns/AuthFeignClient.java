package com.gnol.springboot.auth.feigns;

import org.springframework.cloud.openfeign.FeignClient;

import com.gnol.springboot.auth.mappings.AuthMapping;
import com.gnol.springboot.common.constant.ServerConstant;

/**
 * @Title: AuthFeignClient
 * @Package: com.gnol.springboot.auth.feigns
 * @author: 吴佳隆
 * @date: 2020年6月28日 下午2:12:11
 * @Description: 认证 Feign 客户端
 */
@FeignClient(name = ServerConstant.AUTH)
public interface AuthFeignClient extends AuthMapping {

}