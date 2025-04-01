package com.test.runrunbots.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserResponse {

    private Long id;                // 用户ID  
    private String username;        // 用户名  
    private String email;           // 邮箱  
    private String phoneNumber;     // 手机号  

}  