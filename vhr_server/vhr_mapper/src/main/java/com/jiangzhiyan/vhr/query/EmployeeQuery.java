package com.jiangzhiyan.vhr.query;

import com.jiangzhiyan.vhr.base.BaseQuery;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EmployeeQuery extends BaseQuery {

    private String name;
    private String phone;
    private Integer politicId;
    private Integer nationId;
    private Integer posId;
    private Integer jobLevelId;
    private String engageForm;
    private Integer departmentId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDateMin;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDateMax;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getPoliticId() {
        return politicId;
    }

    public void setPoliticId(Integer politicId) {
        this.politicId = politicId;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public Integer getPosId() {
        return posId;
    }

    public void setPosId(Integer posId) {
        this.posId = posId;
    }

    public Integer getJobLevelId() {
        return jobLevelId;
    }

    public void setJobLevelId(Integer jobLevelId) {
        this.jobLevelId = jobLevelId;
    }

    public String getEngageForm() {
        return engageForm;
    }

    public void setEngageForm(String engageForm) {
        this.engageForm = engageForm;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Date getBeginDateMin() {
        return beginDateMin;
    }

    public void setBeginDateMin(Date beginDateMin) {
        this.beginDateMin = beginDateMin;
    }

    public Date getBeginDateMax() {
        return beginDateMax;
    }

    public void setBeginDateMax(Date beginDateMax) {
        this.beginDateMax = beginDateMax;
    }
}
