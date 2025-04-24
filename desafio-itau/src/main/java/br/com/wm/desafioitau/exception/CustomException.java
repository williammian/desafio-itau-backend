package br.com.wm.desafioitau.exception;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomException {

	@ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(ValidacaoException ex) {
        ErrorResponse errorResponse = ErrorResponse.internalError(ex);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = Objects.requireNonNull(ex.getBindingResult().getFieldError());
        ErrorResponse errorResponse = ErrorResponse.invalidArgumentsError(fieldError);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }
	
}
