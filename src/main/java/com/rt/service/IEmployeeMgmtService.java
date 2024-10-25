package com.rt.service;


import com.rt.entity.EmployeeEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEmployeeMgmtService {
    public List<EmployeeEntity> fetchAllEmployee();
    public String registerEmployee(EmployeeEntity emp);
    public EmployeeEntity getEmployeeById(int empno);
    public EmployeeEntity getEmployeeByName(String ename);
    public String updateEmployee(EmployeeEntity emp);
    public String deleteEmployee(int empno);
    public void saveExcelData(MultipartFile file) throws IOException;

}
