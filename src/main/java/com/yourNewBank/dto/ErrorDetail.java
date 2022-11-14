package com.yourNewBank.dto;

public class ErrorDetail {
    private String code;
    private String message;

    public ErrorDetail() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorDetail{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
