package com.test.runrunbots.service;



import com.alibaba.fastjson2.JSON;
import com.test.runrunbots.controller.UserAlreadyExistException;
import com.test.runrunbots.model.User;
import com.test.runrunbots.model.UserRole;
import com.test.runrunbots.model.dto.user.LoginAuthResponse;
import com.test.runrunbots.model.dto.user.LoginRequest;
import com.test.runrunbots.model.dto.user.UpdateUserRequest;
import com.test.runrunbots.repository.UserRepository;
import com.test.runrunbots.security.JwtHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Service
public class AuthenticationService {


   private final AuthenticationManager authenticationManager;
   private final JwtHandler jwtHandler;
   private final PasswordEncoder passwordEncoder;
   private final UserRepository userRepository;


   public AuthenticationService(
           AuthenticationManager authenticationManager,
           JwtHandler jwtHandler,
           PasswordEncoder passwordEncoder,
           UserRepository userRepository
   ) {
       this.authenticationManager = authenticationManager;
       this.jwtHandler = jwtHandler;
       this.passwordEncoder = passwordEncoder;
       this.userRepository = userRepository;
   }

    @Transactional
   public User register(String username, String email, String phone, String password, UserRole role) throws UserAlreadyExistException {
       log.info("Registering user: {}", username);
       if (userRepository.existsByUsername(username)) {
           throw new UserAlreadyExistException();
       }

       log.info("---------------------db saving------------------");
//       User user = new User( null, username, email, phone, passwordEncoder.encode(password) );
       User user = new User();
       user.setUsername(username);
       user.setEmail(email);
       user.setPhone(phone);
       user.setPassword(passwordEncoder.encode(password));
       user.setRole(role);

       log.info("Registering user: {} ==>> ", user);
       return userRepository.save(user);
   }


//   public String login(String username, String password) {
//       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//       return jwtHandler.generateToken(username);
//   }

    public LoginAuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        jwtHandler.generateToken(loginRequest.getUsername());
        LoginAuthResponse authResponse = new LoginAuthResponse();
        authResponse.setToken(jwtHandler.generateToken(loginRequest.getUsername()));
        authResponse.setUser(loginRequest.getUsername());
        log.info("Logining...: {} ==>> ", JSON.toJSONString(authResponse));

        return authResponse;
    }

    /**
     * 更新用户信息
     */
    @Transactional
    public User updateUserInfo(Long userId, UpdateUserRequest updateRequest, LocalDateTime now) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getUserId().equals(userId)) {
            throw new AccessDeniedException("You can only update your own information");
        }

        // 更新用户的邮箱和电话
        if (updateRequest.getPhoneNumber() != null) {
            user.setPhone(updateRequest.getPhoneNumber());
        }
        if (updateRequest.getEmail() != null) {
            user.setEmail(updateRequest.getEmail());
        }
        user.setUpdatedAt(now);

        // 保存用户信息
        return userRepository.save(user);
    }

}
