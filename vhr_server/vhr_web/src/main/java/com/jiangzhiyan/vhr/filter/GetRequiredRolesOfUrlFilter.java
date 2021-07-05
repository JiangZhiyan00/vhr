package com.jiangzhiyan.vhr.filter;

import com.jiangzhiyan.vhr.model.Menu;
import com.jiangzhiyan.vhr.model.Role;
import com.jiangzhiyan.vhr.service.MenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 过滤器:获取当前请求访问的url所需要的角色
 */
@Component
public class GetRequiredRolesOfUrlFilter implements FilterInvocationSecurityMetadataSource {

    @Resource
    private MenuService menuService;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取当前请求的url
        String url = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menus = menuService.getAllMenusWithRoles();
        for (Menu menu : menus) {
            //顺序不能反,因为数据库中的url为通配符格式:/url/**
            if (antPathMatcher.match(menu.getUrl(), url)) {
                List<Role> roles = menu.getRoles();
                String[] roleNames = new String[roles.size()];
                for (int i = 0; i < roleNames.length; i++) {
                    roleNames[i] = roles.get(i).getName();
                }
                //如果匹配上了,则返回这个url对应所需要的角色名
                return SecurityConfig.createList(roleNames);
            }
        }
        //如果没有匹配上,则代表访问的地址不在需要进行权限管理的范围内,认为登录即有权访问
        //(有可能是一些普通的接口地址,或者是项目中不存在的地址),
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 这个方法的作用是:指示此SecurityMetadataSource实现类能否为已登录用户提供需要返回的资源
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
