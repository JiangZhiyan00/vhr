package com.jiangzhiyan.vhr.service;

import com.jiangzhiyan.vhr.mapper.MenuMapper;
import com.jiangzhiyan.vhr.model.Hr;
import com.jiangzhiyan.vhr.model.Menu;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    public List<Menu> loadMenusByHrId() {
        //通过SpringSecurity上下文获取登录用户信息,不需要接收前台传的数据,更加安全
        Hr hr = (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return menuMapper.loadMenusByHrId(hr.getId());
    }

    public List<Menu> getAllMenusWithRoles() {
        return menuMapper.getAllMenusWithRoles();
    }

    /**
     * 获取所有菜单,并分级
     */
    public List<Menu> queryAllMenusWithChildren() {
        return menuMapper.queryAllMenusWithChildren();
    }
}
