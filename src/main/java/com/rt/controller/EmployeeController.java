package com.rt.controller;


import com.rt.entity.EmployeeEntity;
import com.rt.service.IEmployeeMgmtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController

public class EmployeeController {
    @Autowired
    private IEmployeeMgmtService empService;
    @GetMapping("/getAllEmployee")

    public ResponseEntity<?> showAllEmployee(){
        log.info("Employee:showAllEmployee executed");
        List<EmployeeEntity> employeeEntities = empService.fetchAllEmployee();
        log.info("Employee::showAllEmployee end");
        return new ResponseEntity<List<EmployeeEntity>>(employeeEntities, HttpStatus.OK);
    }

    @PostMapping("/register-employee")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeEntity emp){
        emp.setEmpno(null);
        String s = empService.registerEmployee(emp);
        Map<String,String> map=new HashMap<>();
        map.put("msg",s);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/Delete/{empno}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int empno){
        String s = empService.deleteEmployee(empno);
        Map<String, String> response = new HashMap<>();
        response.put("msg", s);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/GetEmpByName/{ename}")
    public ResponseEntity<?> findEmployeeByName(@PathVariable String ename){
        EmployeeEntity emp = empService.getEmployeeByName(ename);

        return new ResponseEntity<EmployeeEntity>(emp,HttpStatus.OK);
    }


    @GetMapping("/GetEmpById/{empno}")
    public ResponseEntity<?> findEmployeeById(@PathVariable int empno){
        EmployeeEntity emp = empService.getEmployeeById(empno);

        return new ResponseEntity<EmployeeEntity>(emp,HttpStatus.OK);
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@RequestBody  EmployeeEntity emp){
        String s = empService.updateEmployee(emp);
        Map<String, String> response = new HashMap<>();
        response.put("msg", s);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/upload-excel")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        log.info("EmployeeController:upload excel executed");
        if (file.isEmpty()) {
            return new ResponseEntity<String>("Dont Upload empty excel",HttpStatus.BAD_REQUEST);
        }

        try {
            empService.saveExcelData(file);
            return new ResponseEntity<String>("Excel data saved successfully.",HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error processing file: " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
