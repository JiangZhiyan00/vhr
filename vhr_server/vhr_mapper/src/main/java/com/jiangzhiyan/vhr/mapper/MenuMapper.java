package com.jiangzhiyan.vhr.mapper;

import com.jiangzhiyan.vhr.base.BaseMapper;
import com.jiangzhiyan.vhr.model.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu, Integer> {

    List<Menu> loadMenusByHrId(Integer hrId);

    List<Menu> getAllMenusWithRoles();

    List<Menu> queryAllMenusWithChildren();
}