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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * User Registration
     * @param request
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody UserRegistrationRequest request) {
        // User Registration Logic
        log.info("----------------Registering----------------------");
        log.info(request.toString());
        User registered = authenticationService.register(request.getUsername(), request.getEmail(), request.getPhone(), request.getPassword(), request.getRole());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(registered));
    }

    @PostMapping("/login")  
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        // User Login Logic
        // 调用业务逻辑层的 客户登录功能
        AuthResponse authResponse = authenticationService.login( request );
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(authResponse));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<User>> getCurrentUser(@AuthenticationPrincipal User user) {
        log.info("@AuthenticationPrincipal user: {}", user);
        //  优先使用 DTO：
        //  避免直接返回实体类，使用 DTO 控制序列化的字段。
        return ResponseEntity.ok(ApiResponse.success(user));
    }

}  