package com.jiangzhiyan.vhr.mapper;

import com.jiangzhiyan.vhr.base.BaseMapper;
import com.jiangzhiyan.vhr.model.JobLevel;

import java.util.List;

public interface JobLevelMapper extends BaseMapper<JobLevel, Integer> {

    List<JobLevel> selectEnabled();
}