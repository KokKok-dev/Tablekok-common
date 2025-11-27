package com.tablekok.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tablekok.exception.ErrorCode;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 Json응답에서 제외, 성공한 요청에는 errors가 필요 없으니 JSON에서 아예 빠지도록 설정
public class ApiResponse<T> {

    private String status; // "SUCCESS" 또는 "FAIL"
    private String code;   // S200, U001 등
    private String message;
    private T data;        // 성공 시 응답 데이터
    private List<ErrorResponse.FieldError> errors; // 실패 시 필드 오류 목록

    // 201, 204, 203
    public static <T> ApiResponse<T> success(String message, T data, HttpStatus httpStatus) {
        return ApiResponse.<T>builder()
            .status(httpStatus.getReasonPhrase())
            .code(String.valueOf(httpStatus.value()))
            .message(message)
            .data(data)
            .build();
    }

    public static ApiResponse<Void> success(String message, HttpStatus httpStatus) {
        return success(message, null, httpStatus);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return ApiResponse.<T>builder()
            .status(errorCode.getStatus().toString())
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .errors(Collections.emptyList())
            .build();
    }

    public static ApiResponse<Void> error(ErrorCode errorCode, BindingResult bindingResult) {
        return ApiResponse.<Void>builder()
            .status("FAIL")
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .errors(ErrorResponse.FieldError.of(bindingResult))
            .build();
    }

    public static ApiResponse<Void> error(ErrorCode errorCode, String message) {
        return ApiResponse.<Void>builder()
            .status("FAIL")
            .code(errorCode.getCode())
            .message(message)
            .errors(Collections.emptyList())
            .build();
    }

    public static ApiResponse<Void> error(String errorCode, String message) {
        return ApiResponse.<Void>builder()
            .status("FAIL")
            .code(errorCode)
            .message(message)
            .errors(Collections.emptyList())
            .build();
    }
}
