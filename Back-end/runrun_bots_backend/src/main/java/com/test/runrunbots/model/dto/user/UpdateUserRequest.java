package com.test.runrunbots.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO（数据传输对象） 来限定前端传入可更新的字段
 */
@Data
public class UpdateUserRequest {

    @NotEmpty(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;  

    @Email(message = "Invalid email format")
    private String email;  

}  