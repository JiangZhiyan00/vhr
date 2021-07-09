package com.jiangzhiyan.vhr.controller.salary;

import com.jiangzhiyan.vhr.model.Salary;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.salary.SalarySobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JiangZhiyan
 */
@RestController
@Api("薪资管理-工资账套管理")
@RequestMapping("/salary/sob")
public class SalarySobController {

    @Autowired
    private SalarySobService salarySobService;

    @ApiOperation("查询所有薪资账套")
    @GetMapping("/")
    public ResponseBean selectAll(){
        return salarySobService.selectAll();
    }

    @ApiOperation("添加薪资账套")
    @PostMapping("/")
    public ResponseBean add(@RequestBody Salary salary){
        salarySobService.insertSelective(salary);
        return ResponseBean.success("薪资账套添加成功");
    }

    @ApiOperation("删除薪资账套")
    @DeleteMapping("/")
    public ResponseBean deleteBatch(Integer[] ids){
        salarySobService.deleteBatch(ids);
        return ResponseBean.success("薪资账套删除成功");
    }

    @ApiOperation("更新薪资账套")
    @PutMapping("/")
    public ResponseBean update(@RequestBody Salary salary){
        salarySobService.updateByPrimaryKeySelective(salary);
        return ResponseBean.success("薪资账套更新成功");
    }
}
