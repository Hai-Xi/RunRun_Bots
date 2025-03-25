package com.test.runrunbots;


import com.test.runrunbots.controller.DeliveryMethodNotExistException;
import com.test.runrunbots.controller.UserAlreadyExistException;
import com.test.runrunbots.model.dto.unifiedGlobalResponse.ApiResponse;
import com.test.runrunbots.model.dto.error.RunrunbotsErrorResponse;
import com.test.runrunbots.controller.OrderIdNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<ApiResponse<RunrunbotsErrorResponse>> handleDefaultException(UserAlreadyExistException e) {
        RunrunbotsErrorResponse runrunbotsErrorResponse = new RunrunbotsErrorResponse(
                "UserAlreadyExistException error.",
                e.getClass().getName(),
                e.getMessage());
//        runrunbotsErrorResponse.message("UserAlreadyExistException error.");
//        runrunbotsErrorResponse.error(e.getClass().getName());
//        runrunbotsErrorResponse.details(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(ApiResponse.success(runrunbotsErrorResponse));

    }

    @ExceptionHandler(OrderIdNotExistException.class)
    public final ResponseEntity<ApiResponse<RunrunbotsErrorResponse>> handleDefaultException(OrderIdNotExistException e) {
        RunrunbotsErrorResponse runrunbotsErrorResponse = new RunrunbotsErrorResponse(
                "OrderIdNotExistException error.",
                e.getClass().getName(),
                e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.success(runrunbotsErrorResponse));

    }

    @ExceptionHandler(DeliveryMethodNotExistException.class)
    public final ResponseEntity<ApiResponse<RunrunbotsErrorResponse>> handleDefaultException(DeliveryMethodNotExistException e) {
        RunrunbotsErrorResponse runrunbotsErrorResponse = new RunrunbotsErrorResponse(
                "DeliveryMethodNotExistException error.",
                e.getClass().getName(),
                e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.success(runrunbotsErrorResponse));

    }

}
