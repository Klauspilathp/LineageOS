package top.d7c.springboot.common.services.sys.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import top.d7c.plugins.core.StringUtil;
import top.d7c.plugins.core.context.CacheService;
import top.d7c.springboot.common.services.sys.AbstractIdService;

/**
 * @Title: CacheIdServiceImpl
 * @Package: top.d7c.springboot.common.services.sys.impl
 * @author: 吴佳隆
 * @date: 2020年4月3日 上午11:10:40
 * @Description: 基于缓存实现的 ID 生成服务实现，建议使用 redis 的缓存实现
 */
@Service(value = "cacheIdServiceImpl")
public class CacheIdServiceImpl extends AbstractIdService {
    /**
     * 缓存接口，所有缓存实现都继承自此接口
     */
    private CacheService cacheService;

    public CacheIdServiceImpl(@Qualifier("redisServiceImpl") CacheService cacheService) {
        super();
        this.cacheService = cacheService;
    }

    @Override
    public long getLong(String idKey) {
        if (StringUtil.isBlank(idKey)) {
            idKey = "default_cache_id_key";
        }
        return cacheService.incr(idKey);
    }

    @Override
    public long getLong(String idKey, int perNum) {
        return this.getLong(idKey);
    }

    @Override
    public String getString(String prefix) {
        return prefix + this.getLong("default_cache_id_key");
    }

}