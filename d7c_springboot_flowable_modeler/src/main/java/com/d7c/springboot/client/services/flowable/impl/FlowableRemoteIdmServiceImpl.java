package com.d7c.springboot.client.services.flowable.impl;

import java.util.ArrayList;
import java.util.List;

import org.flowable.ui.common.model.RemoteGroup;
import org.flowable.ui.common.model.RemoteToken;
import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.service.idm.RemoteIdmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: FlowableRemoteIdmServiceImpl
 * @Package: com.d7c.springboot.client.services.flowable.impl
 * @author: 吴佳隆
 * @date: 2021年4月30日 上午11:58:57
 * @Description: flowable idm 用户服务。
 */
@Service
public class FlowableRemoteIdmServiceImpl implements RemoteIdmService {
    @Autowired
    private FlowableSecurityUserImpl flowableSecurityUserImpl;

    @Override
    public RemoteUser authenticateUser(String username, String password) {
        return flowableSecurityUserImpl.getRemoteUser();
    }

    @Override
    public RemoteToken getToken(String tokenValue) {
        return null;
    }

    @Override
    public RemoteUser getUser(String userId) {
        return flowableSecurityUserImpl.getRemoteUser();
    }

    @Override
    public List<RemoteUser> findUsersByNameFilter(String filter) {
        return new ArrayList<>();
    }

    @Override
    public List<RemoteUser> findUsersByGroup(String groupId) {
        return new ArrayList<>();
    }

    @Override
    public RemoteGroup getGroup(String groupId) {
        return new RemoteGroup();
    }

    @Override
    public List<RemoteGroup> findGroupsByNameFilter(String filter) {
        return new ArrayList<>();
    }

}
