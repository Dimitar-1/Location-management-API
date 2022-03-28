package com.mycompany.offeringapi.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusinessException extends Exception {

    private List<ErrorModel> errorModelList;

    public BusinessException(List<ErrorModel> errorModelList) {
        this.errorModelList = errorModelList;
    }
}
