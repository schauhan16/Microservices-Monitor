package com.shailendra.exception.handler;

import com.shailendra.exception.InvalidServiceException;
import com.shailendra.exception.model.ApiError;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * @author Shailendra Chauhan
 * <p>
 * For custom exception responses
 */
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle invalid service response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(InvalidServiceException.class)
    protected ResponseEntity<Object> handleInvalidService(InvalidServiceException exception) {
        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
        apiError.setMessage(exception.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
