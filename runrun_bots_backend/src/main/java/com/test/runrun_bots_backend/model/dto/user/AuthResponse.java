package com.test.runrun_bots_backend.model.dto.user;

import lombok.Data;

@Data  
public class AuthResponse {  
    private String token;  
    private UserDTO user;  
}  