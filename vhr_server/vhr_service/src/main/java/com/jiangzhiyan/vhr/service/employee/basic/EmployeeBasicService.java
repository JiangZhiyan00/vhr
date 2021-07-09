package com.jiangzhiyan.vhr.service.employee.basic;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiangzhiyan.vhr.base.BaseQuery;
import com.jiangzhiyan.vhr.base.BaseService;
import com.jiangzhiyan.vhr.enums.EngageForm;
import com.jiangzhiyan.vhr.enums.TiptopDegree;
import com.jiangzhiyan.vhr.enums.WedLock;
import com.jiangzhiyan.vhr.mapper.*;
import com.jiangzhiyan.vhr.model.Employee;
import com.jiangzhiyan.vhr.query.EmployeeQuery;
import com.jiangzhiyan.vhr.responseData.ResponseBean;
import com.jiangzhiyan.vhr.utils.*;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.ws.Response;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeBasicService extends BaseService<Employee,Integer> {

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private PoliticsStatusMapper politicsStatusMapper;

    @Resource
    private NationMapper nationMapper;

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private JobLevelMapper jobLevelMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 添加新员工,添加完成后发送邮件到新员工邮箱
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSelective(Employee employee) {
        super.insertSelective(employee);
        Employee addEmp = employeeMapper.getEmployeeById(employee.getId());
        rabbitTemplate.convertAndSend("vhr.mail.welcome",addEmp);
    }

    public ResponseBean selectAllOptions() {
        Map<String,Object> map = new HashMap<>(5);
        map.put("politics",politicsStatusMapper.selectAll());
        map.put("nations",nationMapper.selectAll());
        map.put("positions",positionMapper.selectAll());
        map.put("jobLevels",jobLevelMapper.selectEnabled());
        map.put("departments",departmentMapper.selectAll());
        return ResponseBean.success(map);
    }

    public ResponseBean getWorkId() {
        Integer existMaxWorkId = employeeMapper.selectMaxWorkId();
        //将数字转换为共8位的字符串,不足前方补0
        return ResponseBean.success((Object) String.format("%08d",existMaxWorkId+1));
    }

    public ResponseBean getAllDeps() {
        return ResponseBean.success(departmentMapper.selectAllDepartmentsWithChildren(-1));
    }

    public ResponseBean update(Employee employee) {
        checkParams(employee);
        initParams(employee);
        AssertExceptionUtil.isTrue(employeeMapper.updateByPrimaryKeySelective(employee) != 1);
        return ResponseBean.success("员工信息更新成功");
    }

    /**
     * 导出数据为excel
     */
    public ResponseEntity<byte[]> selectAllEmps() throws IOException {
        List<Employee> employees = employeeMapper.selectAll();
        return PoiUtil.employeesDataToExcel(employees);
    }

    /**
     * 将excel文件数据解析为List集合并批量插入员工表中
     */
    @Transactional(rollbackFor = Exception.class)
    public void excelToEmployeesData(MultipartFile file) throws IOException, ParseException {
        List<Employee> employeeList = PoiUtil.excelToEmployeesData(file, politicsStatusMapper.selectAll(),
                nationMapper.selectAll(),positionMapper.selectAll(),jobLevelMapper.selectEnabled(),
                departmentMapper.selectAll());
        AssertExceptionUtil.isTrue(employeeMapper.insertBatch(employeeList) != employeeList.size());
    }

    /**
     * 分页查询所有员工及其薪资信息
     */
    public ResponseBean selectEmpsWithSalary(EmployeeQuery query) {
        PageHelper.startPage(query.getPageNum(),query.getPageSize());
        List<Employee> employees = employeeMapper.selectEmpsWithSalary(query);
        PageInfo<Employee> pageInfo = new PageInfo<>(employees);
        Map<String ,Object> map = new HashMap<>(2);
        map.put("total",pageInfo.getTotal());
        map.put("emps",pageInfo.getList());
        return ResponseBean.success(map);
    }

    @Override
    protected void checkParams(Employee employee) {
        AssertExceptionUtil.isTrue(employee == null,invalidRequest);
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getName()),"姓名不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getGender()),"性别不能为空");
        boolean flag = "男".equals(employee.getGender()) || "女".equals(employee.getGender());
        AssertExceptionUtil.isTrue(!flag,"性别有误");
        AssertExceptionUtil.isTrue(employee.getPoliticId() == null,"政治面貌不能为空");
        AssertExceptionUtil.isTrue(employee.getNationId() == null,"民族不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getNativePlace()),"籍贯不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getEmail()),"邮箱不能为空");
        AssertExceptionUtil.isTrue(!EmailUtil.isValidEmail(employee.getEmail()),"邮箱格式不正确");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getAddress()),"联系地址不能为空");
        AssertExceptionUtil.isTrue(employee.getPosId() == null,"职位不能为空");
        AssertExceptionUtil.isTrue(employee.getJobLevelId() == null,"职称不能为空");
        AssertExceptionUtil.isTrue(employee.getDepartmentId() == null,"所属部门不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getPhone()),"手机号不能为空");
        AssertExceptionUtil.isTrue(!PhoneUtil.isValidMobile(employee.getPhone()),"手机号格式不正确");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getWorkId()),"工号不能为空");
        Employee existEmployee = employeeMapper.selectByWorkId(employee.getWorkId());
        if (employee.getId() == null){
            AssertExceptionUtil.isTrue(existEmployee != null, "已存在此工号的员工");
        }else {
            AssertExceptionUtil.isTrue(existEmployee != null && !existEmployee.getId().equals(employee.getId()),
                    "已存在此工号的员工");
        }
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getTiptopDegree()),"学历不能为空");
        AssertExceptionUtil.isTrue(!EnumUtils.isValidEnum(TiptopDegree.class,employee.getTiptopDegree()),
                "该学历不存在");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getSchool()),"毕业院校不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getSpecialty()),"专业不能为空");
        AssertExceptionUtil.isTrue(employee.getBeginDate() == null,"入职日期不能为空");
        AssertExceptionUtil.isTrue(employee.getConversionTime() == null,"转正日期不能为空");
        AssertExceptionUtil.isTrue(employee.getBeginDate().compareTo(employee.getConversionTime())>0,
                "转正日期不能早于入职日期");
        AssertExceptionUtil.isTrue(employee.getBeginContract() == null,"合同起始日期不能为空");
        AssertExceptionUtil.isTrue(employee.getEndContract() == null,"合同终止日期不能为空");
        AssertExceptionUtil.isTrue(employee.getBeginContract().compareTo(employee.getEndContract())>0,
                "合同终止日期不能早于合同起始日期");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getIdCard()),"身份证号不能为空");
        AssertExceptionUtil.isTrue(!IdCardUtil.isValidIdCard(employee.getIdCard()),"身份证号输入有误");
        Employee existEmp = employeeMapper.selectByIdCard(employee.getIdCard());
        if (employee.getId() == null){
            AssertExceptionUtil.isTrue(existEmp != null,"该员工已存在");
        }else {
            AssertExceptionUtil.isTrue(existEmp != null && !existEmp.getId().equals(employee.getId()),
                    "该员工已存在");
        }
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getEngageForm()),"聘用形式不能为空");
        AssertExceptionUtil.isTrue(!EnumUtils.isValidEnum(EngageForm.class,employee.getEngageForm()),
                "聘用形式不能为空");
        AssertExceptionUtil.isTrue(StringUtils.isBlank(employee.getWedlock()),"婚姻状况不能为空");
        AssertExceptionUtil.isTrue(!EnumUtils.isValidEnum(WedLock.class,employee.getWedlock()),"该婚姻状况不存在");
    }

    @Override
    protected void initParams(Employee employee) {
        long n = 1000L * 60L * 60L * 24L * 365L;
        double years = (double) (employee.getEndContract().getTime()-employee.getBeginContract().getTime())/n;
        years = (double) Math.round(years*100)/100;
        employee.setContractTerm(years);
    }
}
