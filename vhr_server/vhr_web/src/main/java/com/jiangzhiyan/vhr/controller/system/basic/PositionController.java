package com.jiangzhiyan.vhr.controller.system.basic;

import com.jiangzhiyan.vhr.model.Position;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.system.basic.PositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JiangZhiyan
 */
@RestController
@Api("系统管理-基础信息设置-职位管理")
@RequestMapping("/system/basic/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @ApiOperation("查询所有职位信息")
    @GetMapping("/")
    public ResponseBean selectAllPositions(){
        return positionService.selectAll();
    }

    @ApiOperation("添加职位信息")
    @PostMapping("/")
    public ResponseBean addPosition(@RequestBody Position position){
        positionService.insertSelective(position);
        return ResponseBean.success("职位添加成功");
    }

    @ApiOperation("更新职位信息")
    @PutMapping("/")
    public ResponseBean updatePosition(@RequestBody Position position){
        positionService.updateByPrimaryKeySelective(position);
        return ResponseBean.success("职位更新成功");
    }

    @ApiOperation("删除职位信息")
    @DeleteMapping("/")
    public ResponseBean deletePositions(Integer[] ids){
        positionService.deleteBatch(ids);
        return ResponseBean.success("职位删除成功");
    }
}
