package com.mycompany.locationmanagementapi.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {
    private List<ErrorModel> errorModelList;

    public UserNotFoundException(List<ErrorModel> errorModelList) {
        this.errorModelList = errorModelList;
    }
}
