package br.com.wm.desafioitau.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	
	private int status;
    private String message;
    private Instant timestamp;
    private String field;
    private String errorCode;

    public static ErrorResponse validationError(RuntimeException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(ex.getMessage())
                .errorCode("VALIDATION_ERROR")
                .timestamp(Instant.now())
                .build();
    }

    public static ErrorResponse invalidArgumentsError(FieldError fieldError) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(fieldError.getDefaultMessage())
                .field(fieldError.getField())
                .errorCode("INVALID_ARGUMENT")
                .timestamp(Instant.now())
                .build();
    }
	
}
