package com.d7c.springboot.client.controllers.flowable;

import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.model.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.d7c.springboot.client.services.flowable.impl.FlowableSecurityUserImpl;

/**
 * @Title: RemoteAccountController
 * @Package: com.d7c.springboot.client.controllers.flowable
 * @author: 吴佳隆
 * @date: 2021年4月30日 上午11:39:05
 * @Description: flowable 用户服务。
 */
@RestController
public class RemoteAccountController {
    @Autowired
    private FlowableSecurityUserImpl flowableSecurityUserImpl;

    @GetMapping("app/rest/account")
    public UserRepresentation getAccount() {
        RemoteUser appUser = flowableSecurityUserImpl.getRemoteUser();
        UserRepresentation userRepresentation = new UserRepresentation(appUser);
        userRepresentation.setPrivileges(appUser.getPrivileges());
        return userRepresentation;
    }

}
