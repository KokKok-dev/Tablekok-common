package com.tablekok.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

public record ErrorResponse (

    String code,
    String message,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<ErrorField> errors

){

    public static ErrorResponse from(AppException exception) {
        return new ErrorResponse(exception.getErrorCode().getCode(), exception.getMessage(), null);
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static ErrorResponse of(ErrorCode errorCode, List<ErrorField> errors) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), errors);
    }

    public record ErrorField(String code, String message) {}
}
