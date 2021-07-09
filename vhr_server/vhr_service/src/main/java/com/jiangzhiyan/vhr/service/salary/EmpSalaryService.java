package com.jiangzhiyan.vhr.service.salary;

import com.jiangzhiyan.vhr.mapper.EmpSalaryMapper;
import com.jiangzhiyan.vhr.model.EmpSalary;
import com.jiangzhiyan.vhr.utils.AssertExceptionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author JiangZhiyan
 */
@Service
public class EmpSalaryService {

    @Resource
    private EmpSalaryMapper empSalaryMapper;

    @Transactional(rollbackFor = Exception.class)
    public void update(Integer empId, Integer salaryId) {
        AssertExceptionUtil.isTrue(empId == null, "无效的请求");
        EmpSalary empSalary = empSalaryMapper.selectByEmpId(empId);
        if (empSalary != null) {
            if (salaryId == null){
                AssertExceptionUtil.isTrue(empSalaryMapper.deleteByEmpId(empId) != 1);
            }else {
                empSalary.setSid(salaryId);
                AssertExceptionUtil.isTrue(empSalaryMapper.updateByPrimaryKeySelective(empSalary) != 1);
            }
        }else {
            if (salaryId != null){
                AssertExceptionUtil.isTrue(empSalaryMapper.insertSelective(new EmpSalary(empId, salaryId)) != 1);
            }
        }
    }
}
