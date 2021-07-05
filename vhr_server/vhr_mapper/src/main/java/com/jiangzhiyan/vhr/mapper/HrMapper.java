package com.jiangzhiyan.vhr.mapper;

import com.jiangzhiyan.vhr.base.BaseMapper;
import com.jiangzhiyan.vhr.model.Hr;
import com.jiangzhiyan.vhr.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HrMapper extends BaseMapper<Hr, Integer> {

    Hr loadUserByUsername(String username);

    List<Role> selectRolesByHrId(Integer id);

    List<Hr> selectHrsByKeywordExceptCurrent(@Param("currentId") Integer currentId,
                                            @Param("keyword")String keyword);
}