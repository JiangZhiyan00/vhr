package com.jiangzhiyan.vhr.mapper;

import com.jiangzhiyan.vhr.base.BaseMapper;
import com.jiangzhiyan.vhr.model.MenuRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuRoleMapper extends BaseMapper<MenuRole, Integer> {

    List<Integer> getMenuIdsByRoleId(@Param("roleId") Integer roleId);

    Integer deleteByRoleId(Integer roleId);

    Integer insertMenuAndRoles(@Param("roleId") Integer roleId,
                               @Param("menuIds") Integer[] menuIds);
}