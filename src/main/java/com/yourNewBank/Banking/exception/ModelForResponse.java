package com.yourNewBank.Banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class ModelForResponse {
    private String code;
    private String message;

}
