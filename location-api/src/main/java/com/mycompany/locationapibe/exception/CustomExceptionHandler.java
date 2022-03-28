package com.mycompany.locationapibe.exception;

import com.mycompany.locationapibe.constant.ErrorType;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<List<ErrorModel>> handleBusinessException(BusinessException be) {
        log.warn("Inside in handleBusinessException");
        ResponseEntity<List<ErrorModel>> responseEntity = new ResponseEntity<>(be.getErrorModelList(), HttpStatus.BAD_REQUEST);

        log.debug("Business Exception in handleBusinessException");
        be.getErrorModelList().forEach(em -> log.debug("Code: " + em.getCode() + " *** Message: " + em.getMessage()));
        return responseEntity;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorModel>> handleAllExceptions(Exception e) {
        List<ErrorModel> errorModels = new ArrayList<>();

        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode(ErrorType.UNKNOWN_SERVER_ERROR.toString());
        errorModel.setMessage("Unknown server error");
        log.debug("Inside handleAllException - With code: " + errorModel.getCode() + " *** Message: " + errorModel.getMessage());

        errorModels.add(errorModel);
        log.error("Error Inside handleAllException, cause: " + e.getMessage());
        return new ResponseEntity<>(errorModels, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
