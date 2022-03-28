package com.mycompany.offeringapi.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorModel {

    private String code;
    private String message;
}
