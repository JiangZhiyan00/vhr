package com.jiangzhiyan.vhr.mapper;

import com.jiangzhiyan.vhr.base.BaseMapper;
import com.jiangzhiyan.vhr.model.HrRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HrRoleMapper extends BaseMapper<HrRole,Integer> {

    Integer insertHrAndRoles(@Param("hrId") Integer hrId,@Param("roleIds") Integer[] roleIds);

    Integer deleteByHrId(Integer hrId);

    List<Integer> getRoleIdsByHrId(Integer hrId);
}