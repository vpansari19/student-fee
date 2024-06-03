package com.skiply.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(InvalidDataException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> invalidData(InvalidDataException ex) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setErrorDes(ex.getMessage());
        customErrorResponse.setStatus("400");
        customErrorResponse.setError("BAD REQUEST");
        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
