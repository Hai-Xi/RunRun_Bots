package com.test.runrunbots;


import com.test.runrunbots.controller.UserAlreadyExistException;
import com.test.runrunbots.model.dto.unifiedGlobalResponse.ApiResponse;
import com.test.runrunbots.model.dto.error.RunrunbotsErrorResponse;
import com.test.runrunbots.model.dto.order.OrderIdNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {


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
//        runrunbotsErrorResponse.message("UserAlreadyExistException error.");
//        runrunbotsErrorResponse.error(e.getClass().getName());
//        runrunbotsErrorResponse.details(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.success(runrunbotsErrorResponse));

    }
}
