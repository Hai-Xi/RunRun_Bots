package com.test.runrunbots;


import com.test.runrunbots.controller.DeliveryMethodNotExistException;
import com.test.runrunbots.controller.UserAlreadyExistException;
import com.test.runrunbots.model.dto.unifiedGlobalResponse.ApiResponse;
import com.test.runrunbots.model.dto.error.RunrunbotsErrorResponse;
import com.test.runrunbots.controller.OrderIdNotExistException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
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
                             .body(ApiResponse.error(runrunbotsErrorResponse));

    }

    @ExceptionHandler(OrderIdNotExistException.class)
    public final ResponseEntity<ApiResponse<RunrunbotsErrorResponse>> handleDefaultException(OrderIdNotExistException e) {
        RunrunbotsErrorResponse runrunbotsErrorResponse = new RunrunbotsErrorResponse(
                "OrderIdNotExistException error.",
                e.getClass().getName(),
                e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(runrunbotsErrorResponse));

    }

    /**
     *
     * 在 Spring MVC 处理 HTTP 请求时，请求体解析（可能触发 HttpMessageNotReadableException）必然先于验证（@Valid）执行。
     * 这是因为请求必须先被成功解析成 Java 对象，才能对该对象执行后续的验证。
     * 因此，HttpMessageNotReadableException 的检查先于 @Valid 的校验执行。
     * @Valid 是针对 已经成功反序列化的 Java 对象 的数据校验。如果对象生成失败，就无法进入 @Valid 校验阶段。
     *
     *
     * HttpMessageNotReadableException：针对请求格式和类型错误, ，可以提示用户检查数据格式和类型
     * MethodArgumentNotValidException：针对验证约束违反, ，可以返回具体的字段错误提示
     *
     *
     * @param ex
     * @return
     */
    // 处理请求体解析失败异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<RunrunbotsErrorResponse>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // 自定义响应
        RunrunbotsErrorResponse runrunbotsErrorResponse = new RunrunbotsErrorResponse(
                "Request Body Parsing Failed Exception, HttpMessageNotReadableException error.",
                ex.getClass().getName(),
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(runrunbotsErrorResponse));
    }

    @ExceptionHandler(DeliveryMethodNotExistException.class)
    public final ResponseEntity<ApiResponse<RunrunbotsErrorResponse>> handleDefaultException(DeliveryMethodNotExistException e) {
        RunrunbotsErrorResponse runrunbotsErrorResponse = new RunrunbotsErrorResponse(
                "DeliveryMethodNotExistException error.",
                e.getClass().getName(),
                e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(runrunbotsErrorResponse));

    }

    /**
     *
     * 请求处理流程
     * 执行顺序：
     *
     * - 1. HTTP 请求接收：请求到达 DispatcherServlet
     * - 2. Content-Type 识别：根据请求头识别内容类型
     * - 3. 请求体解析（可能抛出 HttpMessageNotReadableException）
     * -    - 对应 HttpMessageConverter 处理请求体反序列化
     * -    - 如果 JSON 格式有误或字段类型不匹配，此步骤就会失败
     * - 4. 对象实例化完成
     * - 5. 参数验证（@Valid 注解处理）
     * -    - 可能抛出 MethodArgumentNotValidException
     * - 6. 控制器方法执行
     * - 7. 返回结果处理
     * 因此，HttpMessageNotReadableException 的检查先于 @Valid 的校验执行。
     *
     * @param ex
     * @return
     */
    // 处理字段验证错误
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<RunrunbotsErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        RunrunbotsErrorResponse runrunbotsErrorResponse = new RunrunbotsErrorResponse(
                "MethodArgumentNotValidException error.",
                ex.getClass().getName(),
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(runrunbotsErrorResponse));
    }

    /**
     * 这个异常处理器会捕获 Feign 客户端抛出的异常，并返回格式化的错误响应。
     * @param e
     * @return
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignException(FeignException e) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", e.status());
        errorDetails.put("error", "Feign Client Error");
        errorDetails.put("message", e.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.valueOf(e.status()));
    }

}
