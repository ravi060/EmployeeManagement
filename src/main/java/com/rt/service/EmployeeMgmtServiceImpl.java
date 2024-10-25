package com.rt.service;


import com.rt.config.AppConfigProperties;
import com.rt.constant.EmployeeMgmtConstant;
import com.rt.entity.EmployeeEntity;
import com.rt.exception.EmployeeNotFoundException;
import com.rt.repository.IEmployeeRepository;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeMgmtServiceImpl implements IEmployeeMgmtService{
    @Autowired
    private IEmployeeRepository empRepo;

    private Map<String,String> message;

    @Autowired
    public EmployeeMgmtServiceImpl (AppConfigProperties prop) {
        message = prop.getMessages();
    }


    @Override
    public List<EmployeeEntity> fetchAllEmployee() {
        log.info("EmployeeService:fetchAllEmployee executed");
        List<EmployeeEntity> list=empRepo.findAll();
        log.info("EmployeeService:FetchAllEmployee ended");
        return list;
    }

    @Override
    public String registerEmployee(EmployeeEntity emp){
        EmployeeEntity save = empRepo.save(emp);
        return save.getEmpno()!=null?message.get(EmployeeMgmtConstant.SAVE_SUCCESS)
                :message.get(EmployeeMgmtConstant.SAVE_FAILURE);

    }

    @Override
    public EmployeeEntity getEmployeeById(int empno) {
        return empRepo.findById(empno).orElseThrow(()->new EmployeeNotFoundException(message.get(EmployeeMgmtConstant.FIND_BY_ID_FAILURE)));
    }

    @Override
    public EmployeeEntity getEmployeeByName(String ename) {
        return empRepo.findByEname(ename).orElseThrow(()->new EmployeeNotFoundException(message.get(EmployeeMgmtConstant.FIND_BY_NAME_FAILURE)));

    }

    @Override
    public String updateEmployee(EmployeeEntity emp) {
        Optional<EmployeeEntity> opt=empRepo.findById(emp.getEmpno());
        if(opt.isPresent()){
            EmployeeEntity save = empRepo.save(emp);
            return save.getEmpno()+" Employee is updated";
        }
        else
            return "Employee is not Available for Updataion for this Id: "+emp.getEmpno();
    }

    @Override
    public String deleteEmployee(int empno) {
        Optional<EmployeeEntity> emp = empRepo.findById(empno);
        if(emp.isPresent()){
            empRepo.deleteById(empno);
            return "Employee is deleted Sucessfully";
        }
        else
             throw new EmployeeNotFoundException(message.get(EmployeeMgmtConstant.DELETE_FAILURE));

    }

    public void saveExcelData(MultipartFile file) throws IOException {
        List<EmployeeEntity> employees = new ArrayList<>();

        // Using Apache POI to read the Excel file
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is on the first sheet

            // Skip the header row by starting from row index 1
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    EmployeeEntity employee = new EmployeeEntity();
                    employee.setEname(row.getCell(0).getStringCellValue());
                    employee.setJob(row.getCell(1).getStringCellValue());
                    employee.setSal(row.getCell(2).getNumericCellValue());
                    employee.setDeptno((int) row.getCell(3).getNumericCellValue());
                    employee.setStatus("active");

                    employees.add(employee);
                }
            }
        }

        // Save all employee records to the database
        empRepo.saveAll(employees);
    }
    }





