package com.gnol.springboot.common.services.sys;

import com.gnol.plugins.core.context.IdService;
import com.gnol.plugins.tools.idfactory.IdFactory;

/**
 * @Title: AbstractIdService
 * @Package: com.gnol.springboot.common.services.sys
 * @author: 吴佳隆
 * @date: 2020年4月3日 上午11:01:11
 * @Description: ID 生成服务抽象类
 */
public abstract class AbstractIdService implements IdService {

    @Override
    public long getLong() {
        return IdFactory.nextLong();
    }

    @Override
    public String getString() {
        return IdFactory.nextStr();
    }

}