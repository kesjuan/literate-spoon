package com.yourNewBank.Banking.handler;

import com.yourNewBank.Banking.exception.ResourceNotFoundException;
import com.yourNewBank.dto.ErrorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


//    @Autowired
//    private MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        errorDetail.setMessage(rnfe.getLocalizedMessage());
        return new ResponseEntity<>(errorDetail,null,HttpStatus.NOT_FOUND);
    }
}
