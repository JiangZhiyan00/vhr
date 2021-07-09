package com.jiangzhiyan.vhr.service.salary;

import com.jiangzhiyan.vhr.base.BaseService;
import com.jiangzhiyan.vhr.mapper.SalaryMapper;
import com.jiangzhiyan.vhr.model.Salary;
import com.jiangzhiyan.vhr.utils.AssertExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author JiangZhiyan
 */
@Service
public class SalarySobService extends BaseService<Salary, Integer> {

    @Resource
    private SalaryMapper salaryMapper;

    @Override
    protected void checkParams(Salary salary) {
        AssertExceptionUtil.isTrue(salary == null, invalidRequest);
        AssertExceptionUtil.isTrue(StringUtils.isBlank(salary.getName()),"账套名称不能为空");
        AssertExceptionUtil.isTrue(salary.getBasicSalary() == null,"基本工资不能为空");
        AssertExceptionUtil.isTrue(salary.getTrafficSalary() == null,"交通补助不能为空");
        AssertExceptionUtil.isTrue(salary.getLunchSalary() == null,"午餐补助不能为空");
        AssertExceptionUtil.isTrue(salary.getBonus() == null,"奖金不能为空");
        AssertExceptionUtil.isTrue(salary.getPensionBase() == null,"养老金基数不能为空");
        AssertExceptionUtil.isTrue(salary.getPensionPer() == null,"养老金比率不能为空");
        AssertExceptionUtil.isTrue(salary.getMedicalBase() == null,"医疗保险基数不能为空");
        AssertExceptionUtil.isTrue(salary.getMedicalPer() == null,"医疗保险比率不能为空");
        AssertExceptionUtil.isTrue(salary.getAccumulationFundBase() == null,"公积金基数不能为空");
        AssertExceptionUtil.isTrue(salary.getAccumulationFundPer() == null,"公积金比率不能为空");
    }

    @Override
    protected void initParams(Salary salary) {
        if (salary.getId() == null){
            salary.setCreateDate(new Date());
        }
        salary.setAllSalary((int) (salary.getBasicSalary()+salary.getTrafficSalary()+
                        salary.getLunchSalary()+salary.getBonus()-salary.getPensionBase()*salary.getPensionPer()-
                        salary.getAccumulationFundBase()*salary.getAccumulationFundPer()-salary.getMedicalBase()*salary.getMedicalPer()));
    }
}
