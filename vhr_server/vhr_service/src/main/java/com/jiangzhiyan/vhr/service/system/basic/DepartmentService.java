package com.jiangzhiyan.vhr.service.system.basic;

import com.jiangzhiyan.vhr.base.BaseService;
import com.jiangzhiyan.vhr.mapper.DepartmentMapper;
import com.jiangzhiyan.vhr.model.Department;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.utils.AssertExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService extends BaseService<Department,Integer> {

    @Resource
    private DepartmentMapper departmentMapper;

    public ResponseBean selectAllDepartments(){
        return ResponseBean.success(departmentMapper.selectAllDepartmentsWithChildren(-1));
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDepartment(Department department) {
        checkParams(department);
        initParams(department);
        departmentMapper.addDepartment(department);
        AssertExceptionUtil.isTrue(department.getResult() != 1);
        department.setChildren(new ArrayList<>(0));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDepartment(Integer id) {
        AssertExceptionUtil.isTrue(id == null,invalidRequest);
        AssertExceptionUtil.isTrue(departmentMapper.selectByPrimaryKey(id) == null,"待删除的部门不存在");
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        AssertExceptionUtil.isTrue(department.getResult() == -2,"此部门有下级部门,删除失败");
        AssertExceptionUtil.isTrue(department.getResult() == -1,"此部门中仍有员工,删除失败");
        AssertExceptionUtil.isTrue(department.getResult() != 1);
    }

    @Override
    protected void checkParams(Department department) {
        AssertExceptionUtil.isTrue(department == null,invalidRequest);
        AssertExceptionUtil.isTrue(StringUtils.isBlank(department.getName()),"添加的部门名称不能为空");
        AssertExceptionUtil.isTrue(department.getParentId() == null,"添加的部门必须有上级部门");
        Department pDepartment = departmentMapper.selectByPrimaryKey(department.getParentId());
        AssertExceptionUtil.isTrue(pDepartment == null, "待添加部门的上级部门不存在");
        List<Department> existDepartments = departmentMapper.selectByParentId(department.getParentId());
        if (existDepartments != null && existDepartments.size() > 0){
            for (Department existDept : existDepartments){
                AssertExceptionUtil.isTrue(department.getName().equals(existDept.getName()),
                        pDepartment.getName()+"下已存在部门["+department.getName()+"]");
            }
        }
    }

    @Override
    protected void initParams(Department department) {
        department.setEnabled(true);
    }
}
