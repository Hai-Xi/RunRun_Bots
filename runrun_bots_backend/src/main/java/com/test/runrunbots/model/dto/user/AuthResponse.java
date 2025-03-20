package com.test.runrunbots.model.dto.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {  
    private String token;  
    private String user;
}