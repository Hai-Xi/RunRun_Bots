package com.test.runrunbots.model.dto.user;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *
 * DTO（数据传输对象） 限定前端收到的字段
 *
 * 返回值最好使用自定义的 UpdateUserResponse dto 对象，而不是直接返回用户完整的实体类 User;
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {

    private Long id;                // 用户ID  
    private String username;        // 用户名  
    private String email;           // 邮箱  
    private String phoneNumber;     // 手机号
    private LocalDateTime updatedAt;

}  