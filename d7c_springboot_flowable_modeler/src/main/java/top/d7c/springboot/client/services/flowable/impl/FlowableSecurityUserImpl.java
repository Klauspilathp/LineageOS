package top.d7c.springboot.client.services.flowable.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.flowable.ui.common.model.RemoteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Title: FlowableSecurityUserImpl
 * @Package: top.d7c.springboot.client.services.flowable.impl
 * @author: 吴佳隆
 * @date: 2021年4月30日 下午2:52:58
 * @Description: flowable 授权用户服务。
 */
@Service
public class FlowableSecurityUserImpl {
    public static Set<String> FLOWABLE_MODELER_ROLES = new HashSet<String>() {
        private static final long serialVersionUID = -8073020337048668474L;
        {
            // add("access-admin");
            add("access-idm");
            add("access-modeler");
            add("access-rest-api");
            // add("access-task");
        }
    };
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<GrantedAuthority> getGrantedAuthoritys() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        // 配置 flowable-modeler 权限
        FLOWABLE_MODELER_ROLES.parallelStream().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
    }

    public RemoteUser getRemoteUser() {
        RemoteUser user = new RemoteUser();
        user.setId("admin");
        user.setFirstName("吴");
        user.setLastName("佳隆");
        user.setDisplayName("Flowable Modeler");
        user.setEmail("wjl5760610@126.com");
        user.setPassword(passwordEncoder.encode("000000"));
        user.setPrivileges(new ArrayList<String>(FLOWABLE_MODELER_ROLES));
        return user;
    }

}
