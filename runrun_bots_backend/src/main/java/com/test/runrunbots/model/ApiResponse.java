package com.test.runrunbots.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 统一响应格式
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;           // 状态码
    private String message;     // 消息
    private T data;            // 数据
    private LocalDateTime timestamp = LocalDateTime.now();  // 时间戳

    public ApiResponse(int i, String success, T data) {
    }

    // 成功静态方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    // 错误静态方法
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    // 构造方法、getter和setter
}
