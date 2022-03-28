package com.mycompany.offeringapi.exception;

import com.mycompany.offeringapi.constant.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {


    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<List<ErrorModel>> handleBusinessException(BusinessException be) {
        ResponseEntity<List<ErrorModel>> responseEntity = new ResponseEntity<>(be.getErrorModelList(), HttpStatus.NOT_FOUND);

        log.error("Business Exception in handleBusinessException");
        be.getErrorModelList()
                .forEach(errorModel -> log.debug("Code: " + errorModel.getCode() + " Message: " + errorModel.getMessage()));

        return responseEntity;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<List<ErrorModel>> handleAllException(Exception e) {
        List<ErrorModel> errorModels = new ArrayList<>();

        ErrorModel errorModel = ErrorModel.builder()
                .code(ErrorType.UNKNOWN_SERVER_ERROR.toString())
                .message("Unknown server error")
                .build();
        log.debug("Inside handleAllException - With code: " + errorModel.getCode() + " *** Message: " + errorModel.getMessage());

        errorModels.add(errorModel);
        log.error("Error Inside handleAllException, cause: " + e.getMessage());

        return new ResponseEntity<>(errorModels, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

