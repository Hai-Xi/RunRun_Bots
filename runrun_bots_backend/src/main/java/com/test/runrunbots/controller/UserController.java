package com.test.runrunbots.controller;

import com.alibaba.fastjson2.JSON;
import com.test.runrunbots.model.dto.unifiedGlobalResponse.ApiResponse;
import com.test.runrunbots.model.User;
import com.test.runrunbots.model.dto.user.*;
import com.test.runrunbots.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
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
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> register(@Valid @RequestBody UserRegistrationRequest request) {
        // User Registration Logic
        log.info("----------------Registering----------------------");
        log.info(request.toString());
        User registered = authenticationService.register(request.getUsername(), request.getEmail(), request.getPhone(), request.getPassword(), request.getRole());

        // 将实体类转换为 userRegistrationResponse 响应对象
        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
        userRegistrationResponse.setUsername(request.getUsername());
        userRegistrationResponse.setEmail(registered.getEmail());
        userRegistrationResponse.setPhone(registered.getPhone());
        userRegistrationResponse.setRole(registered.getRole());
        log.info("userRegistrationResponse{}", JSON.toJSONString(registered));
        log.info("userRegistrationResponse{}", JSON.toJSONString(userRegistrationResponse));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userRegistrationResponse));
    }

    @PostMapping("/login")  
    public ResponseEntity<ApiResponse<LoginAuthResponse>> login(@RequestBody LoginRequest request) {
        // User Login Logic
        // 调用业务逻辑层的 客户登录功能
        LoginAuthResponse authResponse = authenticationService.login( request );
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(authResponse));
    }

    /**
     *
     * @AuthenticationPrincipal 为 null 的常见原因：
     *
     * 用户未登录。
     * 控制器未受保护。( 请添加 bear tooken )
     * 用户认证后未正确加载 Principal。
     * 未正确配置 AuthenticationPrincipal 的解析器。
     * 用户未被存储到 SecurityContext。
     * 匿名用户访问。
     * 解决方法：
     *
     * 确保用户已登录并提供有效的认证凭据。
     * 检查 Spring Security 的配置，确保端点受保护。
     * 验证自定义的 UserDetailsService 是否正确实现。
     * 如果允许匿名用户访问，处理 null 的情况。
     *
     * @param user
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<User>> getCurrentUser(@AuthenticationPrincipal User user) {
        log.info("@AuthenticationPrincipal user: {}", JSON.toJSONString(user));
        //  优先使用 DTO：
        //  避免直接返回实体类，使用 DTO 控制序列化的字段。
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/user")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateUserInfo(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateUserRequest updateRequest) {

        log.info("Updating user info for user: {}", user.getUserId());

        // 调用 Service 更新用户信息并返回更新的用户实体
        User updatedUser = authenticationService.updateUserInfo(user.getUserId(), updateRequest);

        // 将实体类转换为 UpdateUserResponse 响应对象
        // service层 还是返回完整的 User 对象，但 Controller 通过响应对象封装需要的数据。
        UpdateUserResponse response = new UpdateUserResponse(
                updatedUser.getUserId(),
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getPhone()
        );

        return ResponseEntity.ok(ApiResponse.success(response));
    }

}  