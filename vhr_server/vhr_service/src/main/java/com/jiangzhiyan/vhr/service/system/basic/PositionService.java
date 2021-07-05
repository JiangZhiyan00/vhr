package com.jiangzhiyan.vhr.service.system.basic;

import com.jiangzhiyan.vhr.base.BaseService;
import com.jiangzhiyan.vhr.mapper.PositionMapper;
import com.jiangzhiyan.vhr.model.Position;
import com.jiangzhiyan.vhr.utils.AssertExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author JiangZhiyan
 */
@Service
public class PositionService extends BaseService<Position,Integer> {

    @Resource
    private PositionMapper positionMapper;

    @Override
    protected void checkParams(Position model) {
        AssertExceptionUtil.isTrue(model == null,invalidRequest);
        AssertExceptionUtil.isTrue(StringUtils.isBlank(model.getName()),"添加的职位名称不能为空");
        Position existPosition = positionMapper.selectByName(model.getName());
        if (model.getId() == null){
            AssertExceptionUtil.isTrue(existPosition != null,"添加的职位已存在");
        }else {
            AssertExceptionUtil.isTrue(existPosition != null && !model.getId().equals(existPosition.getId()),
                    "更改的职位名称已存在");
        }
    }

    @Override
    protected void initParams(Position model) {
        if (model.getId() == null){
            model.setEnabled(true);
            model.setCreateDate(new Date());
        }
    }
}
