package com.d7c.springboot.client.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

/**
 * @Title: CustomAccessDecisionManager
 * @Package: com.d7c.springboot.client.config
 * @author: 吴佳隆
 * @date: 2020年7月10日 下午5:54:46
 * @Description: 自定义权限决策处理，一般以上同意或弃权则可以访问，参考 org.springframework.security.access.vote.AffirmativeBased
 * AffirmativeBased – 任何一个 AccessDecisionVoter 返回同意则允许访问
 * ConsensusBased – 同意投票多于拒绝投票（忽略弃权回答）则允许访问
 * UnanimousBased – 每个投票者选择弃权或同意则允许访问
 */
public class CustomAccessDecisionManager extends AbstractAccessDecisionManager {

    protected CustomAccessDecisionManager(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
        super(decisionVoters);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        int deny = 0;
        for (AccessDecisionVoter voter : getDecisionVoters()) {
            int result = voter.vote(authentication, object, configAttributes);
            if (logger.isDebugEnabled()) {
                logger.debug("Voter: " + voter + ", returned: " + result);
            }
            switch (result) {
                case AccessDecisionVoter.ACCESS_GRANTED:
                    return;
                case AccessDecisionVoter.ACCESS_DENIED:
                    deny++;
                    break;
                default:
                    break;
            }
        }
        if (getDecisionVoters().size() > deny * 2) {
            throw new AccessDeniedException(
                    messages.getMessage("CustomAccessDecisionManager.accessDenied", "Access is denied"));
        }
    }

}