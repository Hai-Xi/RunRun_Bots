package com.test.runrun_bots_backend.controller;

import com.test.runrun_bots_backend.model.dto.user.AuthResponse;
import com.test.runrun_bots_backend.model.dto.user.LoginRequest;
import com.test.runrun_bots_backend.model.dto.user.UserDTO;
import com.test.runrun_bots_backend.model.dto.user.UserRegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {  
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRegistrationRequest request) {
        // 用户注册逻辑  
        return null;
    }

    @PostMapping("/login")  
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // 用户登录逻辑  
        return null;
    }
}  