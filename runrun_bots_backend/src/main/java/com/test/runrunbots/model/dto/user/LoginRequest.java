package com.test.runrunbots.model.dto.user;

import lombok.Data;

@Data  
public class LoginRequest {  
    private String email;  
    private String password;  
}  