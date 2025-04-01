package com.test.runrunbots.model.dto.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginAuthResponse {
    private String token;  
    private String user;
}