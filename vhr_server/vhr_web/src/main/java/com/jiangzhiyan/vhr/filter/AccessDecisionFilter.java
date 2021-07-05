package com.jiangzhiyan.vhr.filter;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 访问决策过滤器:判断用户是否有权访问某资源
 */
@Component
public class AccessDecisionFilter implements AccessDecisionManager {

    /**
     * 用户访问验证权限
     * @param authentication 已验证通过的用户信息
     * @param configAttributes GetRequiredRolesOfUrlFilter过滤器返回的访问所需的角色列表
     * @throws AccessDeniedException 无权限
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //获取当前登录用户拥有的角色信息
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute configAttribute : configAttributes){
            //获取访问需要的角色
            String requiredRole = configAttribute.getAttribute();
            //如果需要的角色是ROLE_LOGIN(登录即可访问)
            if ("ROLE_LOGIN".equals(requiredRole)){
                //如果是匿名用户(即未登录)
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录,请先登录");
                }else {
                    return;
                }
            //如果是需要某个角色才能访问
            }else {
                //如果是匿名用户(即未登录)
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录,请先登录");
                }else {
                    //遍历当前用户拥有的权限
                    for (GrantedAuthority authority : authorities){
                        if (requiredRole.equals(authority.getAuthority())){
                            return;
                        }
                    }
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
