package com.test.runrun_bots_backend.model.dto.user;

import lombok.Data;

@Data  
public class UserRegistrationRequest {  
    private String name;  
    private String email;  
    private String phone;  
    private String password;  
}  