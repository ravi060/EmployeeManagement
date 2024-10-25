package com.rt.handler;

import com.rt.dto.ApiResponse;
import com.rt.exception.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class EmployeeServiceExceptionHandler {
    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponse> handleEmployeeNotFoundException(EmployeeNotFoundException exMsg) {
        log.error("Exception Occured : " + exMsg);
        ApiResponse apiResponse=ApiResponse.builder()
                .errorMessage(exMsg.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<ApiResponse>  handleException(Exception exMsg){
        log.error("Exception Occured: " +exMsg);
        ApiResponse apiResponse=ApiResponse.builder()
                .errorMessage(exMsg.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
