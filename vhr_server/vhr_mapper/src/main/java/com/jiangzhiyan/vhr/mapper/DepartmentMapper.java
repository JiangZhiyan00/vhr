package com.jiangzhiyan.vhr.mapper;

import com.jiangzhiyan.vhr.base.BaseMapper;
import com.jiangzhiyan.vhr.model.Department;

import java.util.List;

public interface DepartmentMapper extends BaseMapper<Department, Integer> {

    List<Department> selectAllDepartmentsWithChildren(Integer parentId);

    void addDepartment(Department department);

    List<Department> selectByParentId(Integer parentId);

    void deleteDepartment(Department department);
}