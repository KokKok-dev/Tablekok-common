package com.tablekok.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tablekok.dto.ApiResponse;
import com.tablekok.exception.ErrorResponse.ErrorField;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 비즈니스 예외
	 * @param appException
	 * @return
	 */
	@ExceptionHandler(AppException.class)
	public ResponseEntity<ApiResponse<Void>> handleBusinessException(AppException appException) {

		ErrorCode errorCode = appException.getErrorCode();

		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ApiResponse.<Void>builder()
				.status("FAIL")
				.code(errorCode.getCode())
				.message(appException.getMessage())
				.errors(Collections.emptyList())
				.build());
	}

	/**
	 * @Valid 유효성 검증 실패
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationException(MethodArgumentNotValidException ex) {
		List<ErrorField> errors = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(f -> new ErrorResponse.ErrorField(f.getField(), f.getDefaultMessage()))
			.toList();

		ErrorCode validationCode = new ErrorCode() {
			@Override
			public String getCode() {
				return "INVALID_INPUT";
			}

			@Override
			public String getMessage() {
				return "입력값이 유효하지 않습니다.";
			}

			@Override
			public HttpStatus getStatus() {
				return HttpStatus.BAD_REQUEST;
			}
		};

		ErrorResponse errorResponse = ErrorResponse.of(validationCode, errors);

		return ResponseEntity
			.status(validationCode.getStatus())
			.body(ApiResponse.<ErrorResponse>builder()
				.status("FAIL")
				.code(validationCode.getCode())
				.message(validationCode.getMessage())
				.data(errorResponse)
				.errors(Collections.emptyList())
				.build());
	}

	/**
	 * 그 외 모든 예외
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<ErrorResponse>> handleGeneralException(Exception e) {
		ErrorCode internal = new ErrorCode() {
			@Override
			public String getCode() {
				return "INTERNAL_SERVER_ERROR";
			}

			@Override
			public String getMessage() {
				return "서버 내부 오류가 발생했습니다.";
			}

			@Override
			public HttpStatus getStatus() {
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
		};

		ErrorResponse errorResponse = ErrorResponse.from(internal);

		return ResponseEntity
			.status(internal.getStatus())
			.body(ApiResponse.<ErrorResponse>builder()
				.status("FAIL")
				.code(internal.getCode())
				.message(internal.getMessage())
				.data(errorResponse)
				.errors(Collections.emptyList())
				.build());
	}

}
