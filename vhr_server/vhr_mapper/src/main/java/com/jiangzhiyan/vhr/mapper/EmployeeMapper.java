package com.jiangzhiyan.vhr.mapper;

import com.jiangzhiyan.vhr.base.BaseMapper;
import com.jiangzhiyan.vhr.model.Employee;

public interface EmployeeMapper extends BaseMapper<Employee, Integer> {

    Employee selectByIdCard(String idCard);

    Employee selectByWorkId(String workId);

    Integer selectMaxWorkId();

    Employee getEmployeeById(Integer id);
}