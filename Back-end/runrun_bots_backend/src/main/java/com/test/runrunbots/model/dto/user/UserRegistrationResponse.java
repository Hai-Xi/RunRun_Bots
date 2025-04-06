package com.test.runrunbots.model.dto.user;

import com.test.runrunbots.model.UserRole;
import lombok.Data;

@Data  
public class UserRegistrationResponse {
    private String username;
    private String email;  
    private String phone;  
//    private String password;
    private UserRole role;
}