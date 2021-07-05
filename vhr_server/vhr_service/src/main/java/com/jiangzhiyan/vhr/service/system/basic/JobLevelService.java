package com.jiangzhiyan.vhr.service.system.basic;

import com.jiangzhiyan.vhr.base.BaseService;
import com.jiangzhiyan.vhr.enums.TitleLevel;
import com.jiangzhiyan.vhr.mapper.JobLevelMapper;
import com.jiangzhiyan.vhr.model.JobLevel;
import com.jiangzhiyan.vhr.utils.AssertExceptionUtil;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author JiangZhiyan
 */
@Service
public class JobLevelService extends BaseService<JobLevel,Integer> {

    @Resource
    private JobLevelMapper jobLevelMapper;

    @Override
    protected void checkParams(JobLevel model) {
        AssertExceptionUtil.isTrue(model == null,invalidRequest);
        AssertExceptionUtil.isTrue(StringUtils.isBlank(model.getName()),"添加的职称名称不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(model.getTitleLevel()),"请选择一项职称等级");
        AssertExceptionUtil.isTrue(!EnumUtils.isValidEnum(TitleLevel.class,model.getTitleLevel()),"该职称等级不存在");
        JobLevel existJobLevel = jobLevelMapper.selectByName(model.getName());
        if (model.getId() == null){
            AssertExceptionUtil.isTrue(existJobLevel != null,"添加的职称已存在");
        }else {
            AssertExceptionUtil.isTrue(existJobLevel != null && !model.getId().equals(existJobLevel.getId()),
                    "更改的职称名称已存在");
        }
    }

    @Override
    protected void initParams(JobLevel model) {
        if (model.getId() == null){
            model.setEnabled(true);
            model.setCreateDate(new Date());
        }
    }
}
