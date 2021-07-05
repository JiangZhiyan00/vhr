package com.jiangzhiyan.vhr.controller.employee.basic;

import com.jiangzhiyan.vhr.model.Employee;
import com.jiangzhiyan.vhr.query.EmployeeQuery;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.service.employee.basic.EmployeeBasicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

/**
 * @author JiangZhiyan
 */
@RestController
@Api("员工资料-基本资料")
@RequestMapping("/employee/basic")
public class EmployeeBasicController {

    @Autowired
    private EmployeeBasicService employeeBasicService;

    @ApiOperation("分页条件查询所有员工基本信息")
    @GetMapping("/")
    public ResponseBean queryAndPaged(EmployeeQuery query){
        return employeeBasicService.queryAndPaged(query);
    }

    @ApiOperation("查询所有政治面貌,民族,职位,职称,部门,工号")
    @GetMapping("/allOptions")
    public ResponseBean selectAllOptions(){
        return employeeBasicService.selectAllOptions();
    }

    @ApiOperation("获取工号")
    @GetMapping("/getWorkId")
    public ResponseBean getWorkId(){
        return employeeBasicService.getWorkId();
    }

    @ApiOperation("获取所有部门信息")
    @GetMapping("/allDeps")
    public ResponseBean getAllDeps(){
        return employeeBasicService.getAllDeps();
    }

    @ApiOperation("添加员工")
    @PostMapping("/")
    public ResponseBean add(@RequestBody Employee employee){
        employeeBasicService.insertSelective(employee);
        return ResponseBean.success("员工添加成功");
    }

    @ApiOperation("批量删除员工信息")
    @DeleteMapping("/")
    public ResponseBean deleteBatch(Integer[] ids){
        employeeBasicService.deleteBatch(ids);
        return ResponseBean.success("成功删除"+ids.length+"名员工信息");
    }

    @ApiOperation("更新员工信息")
    @PutMapping("/")
    public ResponseBean update(@RequestBody Employee employee){
        return employeeBasicService.update(employee);
    }

    @ApiOperation("导出员工信息数据为excel")
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData() throws IOException {
        return employeeBasicService.selectAllEmps();
    }

    @ApiOperation("导入数据")
    @PostMapping("/import")
    public ResponseBean importData(MultipartFile file) throws IOException, ParseException {
        employeeBasicService.excelToEmployeesData(file);
        return ResponseBean.success("数据导入成功");
    }
}
