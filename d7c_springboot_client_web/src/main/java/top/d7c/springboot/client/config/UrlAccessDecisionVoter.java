package top.d7c.springboot.client.config;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Title: UrlAccessDecisionVoter
 * @Package: top.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月6日 下午5:59:16
 * @Description: url 权限路由处理，参考 org.springframework.security.access.vote.RoleVoter
 */
public class UrlAccessDecisionVoter implements AccessDecisionVoter<Object> {

    @Override
    public boolean supports(ConfigAttribute attribute) {
        if (null == attribute.getAttribute()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * @param authentication    认证信息
     * @param object            要访问的目标对象
     * @param attributes        访问目标对象所需的角色集合
     * @return
     *      ACCESS_GRANTED – 同意
     *      ACCESS_DENIED – 拒绝
     *      ACCESS_ABSTAIN – 弃权
     */
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return ACCESS_DENIED;
        }
        /**
         * 认证用户所拥有的角色集合
         */
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        /**
         * 默认为弃权，表示只要有一个角色对应上就可以访问，如果改为拒绝表示必须全部角色都包含才能访问。
         */
        int result = ACCESS_ABSTAIN;
        // 遍历目标方法所需的角色集合
        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;
                // 遍历比较认证用户所拥有的角色集合
                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return ACCESS_GRANTED;
                    }
                }
            }
        }
        return result;
    }

}