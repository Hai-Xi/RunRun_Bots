package com.test.runrunbots.repository;

import com.test.runrunbots.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);  
    boolean existsByPhone(String phone);

    UserDetails findByUsername(String username);

    boolean existsByUsername(String username);

}  