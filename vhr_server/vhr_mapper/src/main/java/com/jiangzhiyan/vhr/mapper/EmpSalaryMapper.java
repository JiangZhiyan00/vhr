package com.jiangzhiyan.vhr.mapper;

import com.jiangzhiyan.vhr.base.BaseMapper;
import com.jiangzhiyan.vhr.model.EmpSalary;

import java.util.List;

public interface EmpSalaryMapper extends BaseMapper<EmpSalary, Integer> {

    EmpSalary selectByEmpId(Integer empId);

    int deleteByEmpId(Integer empId);
}