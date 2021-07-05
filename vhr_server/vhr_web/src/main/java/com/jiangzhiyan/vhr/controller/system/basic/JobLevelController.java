package com.jiangzhiyan.vhr.controller.system.basic;

import com.jiangzhiyan.vhr.model.JobLevel;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.system.basic.JobLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JiangZhiyan
 */
@RestController
@Api("系统管理-基础信息设置-职称管理")
@RequestMapping("/system/basic/jobLevel")
public class JobLevelController{

    @Autowired
    private JobLevelService jobLevelService;

    @ApiOperation("查询所有职称信息")
    @GetMapping("/")
    public ResponseBean selectAll(){
        return jobLevelService.selectAll();
    }

    @ApiOperation("添加职称信息")
    @PostMapping("/")
    public ResponseBean add(@RequestBody JobLevel jobLevel){
        jobLevelService.insertSelective(jobLevel);
        return ResponseBean.success("职称添加成功");
    }

    @ApiOperation("更新职称信息")
    @PutMapping("/")
    public ResponseBean update(@RequestBody JobLevel jobLevel){
        jobLevelService.updateByPrimaryKeySelective(jobLevel);
        return ResponseBean.success("职称更新成功");
    }

    @ApiOperation("删除职称信息")
    @DeleteMapping("/")
    public ResponseBean delete(Integer[] ids){
        jobLevelService.deleteBatch(ids);
        return ResponseBean.success("职称删除成功");
    }
}
