package br.com.wm.desafioitau.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	
	private int status;
    private String message;

    public static ErrorResponse internalError(RuntimeException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(ex.getMessage())
                .build();
    }

    public static ErrorResponse invalidArgumentsError(FieldError fieldError) {
        return ErrorResponse.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(fieldError.getDefaultMessage())
                .build();
    }
	
}
