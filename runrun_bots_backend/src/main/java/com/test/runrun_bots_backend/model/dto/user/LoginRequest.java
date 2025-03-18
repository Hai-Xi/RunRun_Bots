package com.test.runrun_bots_backend.model.dto.user;

import lombok.Data;

@Data  
public class LoginRequest {  
    private String email;  
    private String password;  
}  