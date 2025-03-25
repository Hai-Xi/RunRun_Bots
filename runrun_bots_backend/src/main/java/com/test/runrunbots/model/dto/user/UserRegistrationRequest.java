package com.test.runrunbots.model.dto.user;

import com.test.runrunbots.model.UserRole;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data  
public class UserRegistrationRequest {  
    private String username;

    @Email(message = "邮箱格式不正确")
    private String email;
    private String phone;  
    private String password;  
    private UserRole role;
}