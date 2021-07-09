package com.jiangzhiyan.vhr.controller.salary;

import com.jiangzhiyan.vhr.query.EmployeeQuery;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.employee.basic.EmployeeBasicService;
import com.jiangzhiyan.vhr.service.salary.EmpSalaryService;
import com.jiangzhiyan.vhr.service.salary.SalarySobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author JiangZhiyan
 */
@RestController
@RequestMapping("/salary/sobcfg")
@Api("薪资管理-员工账套设置")
public class SalarySobConfigController {

    @Autowired
    private EmployeeBasicService employeeBasicService;

    @Autowired
    private SalarySobService salarySobService;

    @Autowired
    private EmpSalaryService empSalaryService;

    @ApiOperation("分页查询所有员工的账套信息")
    @GetMapping("/")
    public ResponseBean selectEmpsWithSalary(EmployeeQuery query){
        return employeeBasicService.selectEmpsWithSalary(query);
    }

    @ApiOperation("查询所有薪资账套信息")
    @GetMapping("/allSalaries")
    public ResponseBean getAllSalaries(){
        return salarySobService.selectAll();
    }

    @ApiOperation("更改指定员工的薪资账套信息")
    @PutMapping("/")
    public ResponseBean update(Integer empId,@RequestParam(required = false) Integer salaryId){
        empSalaryService.update(empId,salaryId);
        return ResponseBean.success("员工薪资账套修改成功");
    }
}
