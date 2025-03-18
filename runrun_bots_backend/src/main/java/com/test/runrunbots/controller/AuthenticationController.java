package com.test.runrunbots.controller;



import com.test.runrunbots.model.dto.user.AuthResponse;
import com.test.runrunbots.model.dto.user.LoginRequest;
import com.test.runrunbots.model.dto.user.UserRegistrationRequest;
import com.test.runrunbots.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {


   private final AuthenticationService authenticationService;


   public AuthenticationController(AuthenticationService authenticationService) {
       this.authenticationService = authenticationService;
   }


   @PostMapping("/register")
   @ResponseStatus(HttpStatus.CREATED)
   public void register(@RequestBody UserRegistrationRequest body) {
       authenticationService.register(body.getName(), body.getEmail(), body.getPassword(), body.getPhone() );
   }


   @PostMapping("/login")
   public AuthResponse login(@RequestBody LoginRequest body) {
       String token = authenticationService.login(body.getEmail(), body.getPassword());
       AuthResponse authResponse = new AuthResponse(token,body.getEmail());
       return authResponse;
   }
}
