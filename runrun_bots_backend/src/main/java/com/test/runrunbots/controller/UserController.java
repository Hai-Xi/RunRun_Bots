package com.test.runrunbots.controller;

import com.test.runrunbots.model.ApiResponse;
import com.test.runrunbots.model.User;
import com.test.runrunbots.model.dto.user.AuthResponse;
import com.test.runrunbots.model.dto.user.LoginRequest;
import com.test.runrunbots.model.dto.user.UserRegistrationRequest;
import com.test.runrunbots.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody UserRegistrationRequest request) {
        // 用户注册逻辑
        log.info("----------------Registering----------------------");
        log.info(request.toString());
        User registered = authenticationService.register(request.getUsername(), request.getEmail(), request.getPhone(), request.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(registered));
    }

    @PostMapping("/login")  
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // 用户登录逻辑  
        return null;
    }
}  