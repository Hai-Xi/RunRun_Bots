package com.test.runrunbots.service;



import com.test.runrunbots.controller.UserAlreadyExistException;
import com.test.runrunbots.model.User;
import com.test.runrunbots.repository.UserRepository;
import com.test.runrunbots.security.JwtHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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


   public User register(String username, String email, String password,  String phone) throws UserAlreadyExistException {
       if (userRepository.existsByUsername(username)) {
           throw new UserAlreadyExistException();
       }


       User user = new User(null, username, email, passwordEncoder.encode(password),  phone);
       return userRepository.save(user);
   }


   public String login(String username, String password) {
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
       return jwtHandler.generateToken(username);
   }
}
