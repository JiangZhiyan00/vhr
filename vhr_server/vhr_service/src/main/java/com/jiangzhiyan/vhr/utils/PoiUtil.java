package com.jiangzhiyan.vhr.utils;

import com.jiangzhiyan.vhr.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据转换为excel,解析excel为数据的工具类
 * @author JiangZhiyan
 */
public class PoiUtil {

    /**
     * 将员工信息数据导出为excel
     */
    public static ResponseEntity<byte[]> employeesDataToExcel(List<Employee> employees) throws IOException {
        //1.创建excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建文档摘要
        workbook.createInformationProperties();
        //3.获取并配置文档信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //--设置文档类别
        docInfo.setCategory("员工信息");
        //--设置文档管理员
        docInfo.setManager("JiangZhiYan");
        //--设置公司
        docInfo.setCompany("com.jiangzhiyan.vhr");
        //4.获取并配置文档摘要信息
        SummaryInformation summaryInfo = workbook.getSummaryInformation();
        //--设置标题
        summaryInfo.setTitle("员工信息表");
        //--设置作者
        summaryInfo.setAuthor("JiangZhiYan");
        //--设置备注信息
        summaryInfo.setComments("数据来自:vhr");
        //创建标题行样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //5.创建表单
        HSSFSheet sheet = workbook.createSheet("员工信息汇总");
        //设置列宽度
        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 5 * 256);
        sheet.setColumnWidth(3, 12 * 256);
        sheet.setColumnWidth(4, 21 * 256);
        sheet.setColumnWidth(5, 10 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 12 * 256);
        sheet.setColumnWidth(8, 12 * 256);
        sheet.setColumnWidth(9, 20 * 256);
        sheet.setColumnWidth(10, 14 * 256);
        sheet.setColumnWidth(11, 25 * 256);
        sheet.setColumnWidth(12, 15 * 256);
        sheet.setColumnWidth(13, 15 * 256);
        sheet.setColumnWidth(14, 12 * 256);
        sheet.setColumnWidth(15, 10 * 256);
        sheet.setColumnWidth(16, 6 * 256);
        sheet.setColumnWidth(17, 20 * 256);
        sheet.setColumnWidth(18, 18 * 256);
        sheet.setColumnWidth(19, 12 * 256);
        sheet.setColumnWidth(20, 10 * 256);
        sheet.setColumnWidth(21, 13 * 256);
        sheet.setColumnWidth(22, 12 * 256);
        sheet.setColumnWidth(23, 10 * 256);
        sheet.setColumnWidth(24, 12 * 256);
        sheet.setColumnWidth(25, 12 * 256);
        sheet.setColumnWidth(26, 8 * 256);
        //创建标题行
        HSSFRow row0 = sheet.createRow(0);
        //创建第一列
        HSSFCell cell0 = row0.createCell(0);
        //第一列的名称
        cell0.setCellValue("员工姓名");
        cell0.setCellStyle(headerStyle);
        HSSFCell cell1 = row0.createCell(1);
        HSSFCell cell2 = row0.createCell(2);
        HSSFCell cell3 = row0.createCell(3);
        HSSFCell cell4 = row0.createCell(4);
        HSSFCell cell5 = row0.createCell(5);
        HSSFCell cell6 = row0.createCell(6);
        HSSFCell cell7 = row0.createCell(7);
        HSSFCell cell8 = row0.createCell(8);
        HSSFCell cell9 = row0.createCell(9);
        HSSFCell cell10 = row0.createCell(10);
        HSSFCell cell11 = row0.createCell(11);
        HSSFCell cell12 = row0.createCell(12);
        HSSFCell cell13 = row0.createCell(13);
        HSSFCell cell14 = row0.createCell(14);
        HSSFCell cell15 = row0.createCell(15);
        HSSFCell cell16 = row0.createCell(16);
        HSSFCell cell17 = row0.createCell(17);
        HSSFCell cell18 = row0.createCell(18);
        HSSFCell cell19 = row0.createCell(19);
        HSSFCell cell20 = row0.createCell(20);
        HSSFCell cell21 = row0.createCell(21);
        HSSFCell cell22 = row0.createCell(22);
        HSSFCell cell23 = row0.createCell(23);
        HSSFCell cell24 = row0.createCell(24);
        HSSFCell cell25 = row0.createCell(25);
        HSSFCell cell26 = row0.createCell(26);
        cell1.setCellStyle(headerStyle);
        cell2.setCellStyle(headerStyle);
        cell3.setCellStyle(headerStyle);
        cell4.setCellStyle(headerStyle);
        cell5.setCellStyle(headerStyle);
        cell6.setCellStyle(headerStyle);
        cell7.setCellStyle(headerStyle);
        cell8.setCellStyle(headerStyle);
        cell9.setCellStyle(headerStyle);
        cell10.setCellStyle(headerStyle);
        cell11.setCellStyle(headerStyle);
        cell12.setCellStyle(headerStyle);
        cell13.setCellStyle(headerStyle);
        cell14.setCellStyle(headerStyle);
        cell15.setCellStyle(headerStyle);
        cell16.setCellStyle(headerStyle);
        cell17.setCellStyle(headerStyle);
        cell18.setCellStyle(headerStyle);
        cell19.setCellStyle(headerStyle);
        cell20.setCellStyle(headerStyle);
        cell21.setCellStyle(headerStyle);
        cell22.setCellStyle(headerStyle);
        cell23.setCellStyle(headerStyle);
        cell24.setCellStyle(headerStyle);
        cell25.setCellStyle(headerStyle);
        cell26.setCellStyle(headerStyle);
        cell1.setCellValue("工号");
        cell2.setCellValue("性别");
        cell3.setCellValue("出生日期");
        cell4.setCellValue("身份证号");
        cell5.setCellValue("婚姻状况");
        cell6.setCellValue("民族");
        cell7.setCellValue("籍贯");
        cell8.setCellValue("政治面貌");
        cell9.setCellValue("邮箱");
        cell10.setCellValue("手机号");
        cell11.setCellValue("家庭住址");
        cell12.setCellValue("所属部门");
        cell13.setCellValue("职称");
        cell14.setCellValue("职位");
        cell15.setCellValue("聘用形式");
        cell16.setCellValue("学历");
        cell17.setCellValue("专业");
        cell18.setCellValue("毕业院校");
        cell19.setCellValue("入职日期");
        cell20.setCellValue("在职状态");
        cell21.setCellValue("合同期限(年)");
        cell22.setCellValue("转正日期");
        cell23.setCellValue("离职日期");
        cell24.setCellValue("合同起始日期");
        cell25.setCellValue("合同终止日期");
        cell26.setCellValue("工龄(年)");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(emp.getName());
            row.createCell(1).setCellValue(emp.getWorkId());
            row.createCell(2).setCellValue(emp.getGender());
            row.createCell(3).setCellValue(sdf.format(emp.getBirthday()));
            row.createCell(4).setCellValue(emp.getIdCard());
            row.createCell(5).setCellValue(emp.getWedlock());
            row.createCell(6).setCellValue(emp.getNation());
            row.createCell(7).setCellValue(emp.getNativePlace());
            row.createCell(8).setCellValue(emp.getPolitic());
            row.createCell(9).setCellValue(emp.getEmail());
            row.createCell(10).setCellValue(emp.getPhone());
            row.createCell(11).setCellValue(emp.getAddress());
            row.createCell(12).setCellValue(emp.getDepartment());
            row.createCell(13).setCellValue(emp.getJobLevel());
            row.createCell(14).setCellValue(emp.getPosition());
            row.createCell(15).setCellValue(emp.getEngageForm());
            row.createCell(16).setCellValue(emp.getTiptopDegree());
            row.createCell(17).setCellValue(emp.getSpecialty());
            row.createCell(18).setCellValue(emp.getSchool());
            row.createCell(19).setCellValue(sdf.format(emp.getBeginDate()));
            row.createCell(20).setCellValue(emp.getWorkState());
            row.createCell(21).setCellValue(emp.getContractTerm());
            row.createCell(22).setCellValue(sdf.format(emp.getConversionTime()));
            row.createCell(23).setCellValue(
                    emp.getNotWorkDate() == null ? "" : sdf.format(emp.getNotWorkDate()));
            row.createCell(24).setCellValue(sdf.format(emp.getBeginContract()));
            row.createCell(25).setCellValue(sdf.format(emp.getEndContract()));
            row.createCell(26).setCellValue(emp.getWorkAge() == null ? "" : String.valueOf(emp.getWorkAge()));
        }
        //文件下载设置
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment",
                new String("员工信息汇总表.xls".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        workbook.write(out);
        return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.CREATED);
    }

    /**
     * 将excel文件数据解析为List<Employee>集合
     *
     * @param file        上传的excel文件
     * @param politics    所有的政治面貌
     * @param nations     所有的民族
     * @param positions   所有的职位
     * @param jobLevels   所有的职称
     * @param departments 所有的部门
     * @return List<Employee>集合
     */
    public static List<Employee> excelToEmployeesData(MultipartFile file, List<PoliticsStatus> politics,
                                                      List<Nation> nations, List<Position> positions,
                                                      List<JobLevel> jobLevels, List<Department> departments) throws IOException, ParseException {
        List<Employee> employees = new ArrayList<>();
        Employee employee = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //1.由文件输入流创建一个workbook对象
        HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
        //2.获取workbook中表单的数量
        int numberOfSheets = workbook.getNumberOfSheets();
        //3.遍历每个sheet表单
        for (int i = 0; i < numberOfSheets; i++) {
            //获取表单
            HSSFSheet sheet = workbook.getSheetAt(i);
            //获取表单行数
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            int cellNums = 0;
            //遍历每一行
            for (int j = 0; j < physicalNumberOfRows; j++) {
                //获取行
                HSSFRow row = sheet.getRow(j);
                //空行跳过
                if (row == null) {
                    continue;
                }
                if (j == 0){
                    //获取列数
                    cellNums = row.getPhysicalNumberOfCells();
                    continue;
                }
                //遍历列给员工对象赋值
                employee = new Employee();
                for (int k = 0; k < cellNums; k++) {
                    HSSFCell cell = row.getCell(k);
                    if (cell != null) {
                        //如果列数据为字符串
                        if (CellType.STRING.equals(cell.getCellType())) {
                            String stringCellValue = cell.getStringCellValue();
                            if (k == 0) {
                                employee.setName(stringCellValue);
                            } else if (k == 1) {
                                employee.setWorkId(stringCellValue);
                            } else if (k == 2) {
                                employee.setGender(stringCellValue);
                            } else if (k == 3) {
                                employee.setBirthday(sdf.parse(stringCellValue));
                            } else if (k == 4) {
                                employee.setIdCard(stringCellValue);
                            } else if (k == 5) {
                                employee.setWedlock(stringCellValue);
                            } else if (k == 6) {
                                Integer nationId = null;
                                for (Nation nation : nations) {
                                    if (stringCellValue.equals(nation.getName())) {
                                        nationId = nation.getId();
                                        break;
                                    }
                                }
                                employee.setNationId(nationId);
                            } else if (k == 7) {
                                employee.setNativePlace(stringCellValue);
                            } else if (k == 8) {
                                Integer politicId = null;
                                for (PoliticsStatus politic : politics) {
                                    if (stringCellValue.equals(politic.getName())) {
                                        politicId = politic.getId();
                                        break;
                                    }
                                }
                                employee.setPoliticId(politicId);
                            } else if (k == 9) {
                                employee.setEmail(stringCellValue);
                            } else if (k == 10) {
                                employee.setPhone(stringCellValue);
                            } else if (k == 11) {
                                employee.setAddress(stringCellValue);
                            } else if (k == 12) {
                                Integer depId = null;
                                for (Department dep : departments) {
                                    if (stringCellValue.equals(dep.getName())) {
                                        depId = dep.getId();
                                        break;
                                    }
                                }
                                employee.setDepartmentId(depId);
                            } else if (k == 13) {
                                Integer jobLevelId = null;
                                for (JobLevel jobLevel : jobLevels) {
                                    if (stringCellValue.equals(jobLevel.getName())) {
                                        jobLevelId = jobLevel.getId();
                                        break;
                                    }
                                }
                                employee.setJobLevelId(jobLevelId);
                            } else if (k == 14) {
                                Integer positionId = null;
                                for (Position position : positions) {
                                    if (stringCellValue.equals(position.getName())) {
                                        positionId = position.getId();
                                        break;
                                    }
                                }
                                employee.setPosId(positionId);
                            } else if (k == 15) {
                                employee.setEngageForm(stringCellValue);
                            } else if (k == 16) {
                                employee.setTiptopDegree(stringCellValue);
                            } else if (k == 17) {
                                employee.setSpecialty(stringCellValue);
                            } else if (k == 18) {
                                employee.setSchool(stringCellValue);
                            } else if (k == 19) {
                                employee.setBeginDate(sdf.parse(stringCellValue));
                            } else if (k == 20) {
                                employee.setWorkState(stringCellValue);
                            } else if (k == 22) {
                                employee.setConversionTime(sdf.parse(stringCellValue));
                            } else if (k == 23) {
                                employee.setNotWorkDate(StringUtils.isBlank(stringCellValue) ? null : sdf.parse(stringCellValue));
                            } else if (k == 24) {
                                employee.setBeginContract(sdf.parse(stringCellValue));
                            } else if (k == 25) {
                                employee.setEndContract(sdf.parse(stringCellValue));
                            }
                        } else if (CellType.NUMERIC.equals(cell.getCellType())) {
                            double numericCellValue = cell.getNumericCellValue();
                            if (k == 21) {
                                employee.setContractTerm(numericCellValue);
                            } else if (k == 26) {
                                employee.setWorkAge((int) numericCellValue == 0 ? null : (int) numericCellValue);
                            }
                        }
                    }

                }
                employees.add(employee);
            }
        }
        return employees;
    }
}
