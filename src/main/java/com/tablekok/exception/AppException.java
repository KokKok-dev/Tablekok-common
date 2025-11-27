package com.tablekok.exception;

public class AppException extends RuntimeException {

    private final ErrorCode getErrorCode;

    public AppException(ErrorCode getErrorCode) {
        super(getErrorCode.getMessage());
        this.getErrorCode = getErrorCode;
    }

    public ErrorCode getErrorCode() {
        return getErrorCode;
    }

}
