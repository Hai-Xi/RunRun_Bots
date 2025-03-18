package com.test.runrunbots.model.dto.user;

import lombok.Data;

@Data  
public class AuthResponse {  
    private String token;  
    private UserDTO user;

    public AuthResponse(String token, String email) {
    }
}  