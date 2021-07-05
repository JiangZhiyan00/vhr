package com.jiangzhiyan.vhr.controller.system.basic;

import com.jiangzhiyan.vhr.model.Department;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.system.basic.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JiangZhiyan
 */
@RestController
@RequestMapping("/sys/basic/dept")
@Api("系统管理-基础信息设置-部门管理")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("查询所有部门信息,按父子级排序")
    @GetMapping("/")
    public ResponseBean selectAllDepartments(){
        return departmentService.selectAllDepartments();
    }

    @ApiOperation("添加部门")
    @PostMapping("/")
    public ResponseBean addDepartment(@RequestBody Department department){
        departmentService.addDepartment(department);
        return ResponseBean.success("部门添加成功",department);
    }

    @ApiOperation("删除部门")
    @DeleteMapping("/{id}")
    public ResponseBean deleteDepartment(@PathVariable Integer id){
        departmentService.deleteDepartment(id);
        return ResponseBean.success("部门删除成功");
    }
}
