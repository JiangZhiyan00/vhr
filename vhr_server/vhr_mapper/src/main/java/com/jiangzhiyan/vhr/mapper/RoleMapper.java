package com.jiangzhiyan.vhr.mapper;

import com.jiangzhiyan.vhr.base.BaseMapper;
import com.jiangzhiyan.vhr.model.Role;

public interface RoleMapper extends BaseMapper<Role, Integer> {

    Role selectByNameZh(String nameZh);
}