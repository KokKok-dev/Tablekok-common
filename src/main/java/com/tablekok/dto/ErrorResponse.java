package com.tablekok.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final HttpStatus status;
    private final String code;
    private final String message;
    private final LocalDateTime timestamp;
    private final List<FieldError> errors;

    @Getter
    @AllArgsConstructor
    public static class FieldError {

        private String field;
        private String rejectedValue;
        private String reason;

        public static List<FieldError> of(BindingResult bindingResult) {
            return bindingResult.getFieldErrors()
                .stream()
                .map(error -> {
                    error.getRejectedValue();
                    return new FieldError(error.getField(),
                        error.getRejectedValue().toString(),
                        error.getDefaultMessage());
                })
                .collect(Collectors.toList());
        }
    }
}

