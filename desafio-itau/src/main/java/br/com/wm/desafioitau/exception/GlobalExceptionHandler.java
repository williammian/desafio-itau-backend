package br.com.wm.desafioitau.exception;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidacaoException ex) {
        ErrorResponse errorResponse = ErrorResponse.validationError(ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = Objects.requireNonNull(ex.getBindingResult().getFieldError());
        ErrorResponse errorResponse = ErrorResponse.invalidArgumentsError(fieldError);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }
    
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(HttpMessageNotReadableException ex) {
    	logger.error("Bad request: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.badRequestError(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NoResourceFoundException ex) {
    	logger.error("Resource not found: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.notFoundError(ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception ex) {
    	logger.error("Internal server error: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.internalServerError(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
	
}
