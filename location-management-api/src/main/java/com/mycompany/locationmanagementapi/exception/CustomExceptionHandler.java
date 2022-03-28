package com.mycompany.locationmanagementapi.exception;

import com.mycompany.locationmanagementapi.constant.ErrorType;
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
//        HttpStatus httpStatus = getHttpStatus(be);
        ResponseEntity<List<ErrorModel>> responseEntity = new ResponseEntity<>(be.getErrorList(), HttpStatus.BAD_REQUEST);

        log.debug("Business Exception in handleBusinessException");
        be.getErrorList().forEach(em -> log.debug("Code: " + em.getCode() + " *** Message: " + em.getMessage()));

        return responseEntity;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<List<ErrorModel>> handleUserNotFoundException(UserNotFoundException notFoundException) {
        log.debug("Exception in handleUserNotFoundException");
        return new ResponseEntity<>(notFoundException.getErrorModelList(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<ErrorModel>> handleAllException(Exception e) {
        List<ErrorModel> errorModels = new ArrayList<>();

        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode(ErrorType.UNKNOWN_SERVER_ERROR.toString());
        errorModel.setMessage("Unknown server error");
        log.debug("Inside handleAllException - With code: " + errorModel.getCode() + " *** Message: " + errorModel.getMessage());

        errorModels.add(errorModel);
        log.error("Error Inside handleAllException, cause: " + e.getMessage());
        return new ResponseEntity<>(errorModels, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpStatus getHttpStatus(BusinessException be) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        for (ErrorModel errorModel : be.getErrorList()) {
            String customStatusCode = errorModel.getCode().toLowerCase();
            switch (customStatusCode) {
                case "already_exist":
                    httpStatus = HttpStatus.CONFLICT;
                    break;
                case "auth_invalid_credentials":
                    httpStatus = HttpStatus.UNAUTHORIZED;
                    break;
                case "not_empty":
                    httpStatus = HttpStatus.NO_CONTENT;
                    break;
                case "unknown_server_error":
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    break;
                default:
                    httpStatus = HttpStatus.BAD_REQUEST;
                    break;
            }
        }
        return httpStatus;
    }
}
