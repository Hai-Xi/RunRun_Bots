package com.test.runrunbots.model.dto.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;  
import java.lang.annotation.*;  

@Documented  
@Constraint(validatedBy = PasswordStrengthValidator.class)  
@Target({ElementType.FIELD})  
@Retention(RetentionPolicy.RUNTIME)  
public @interface StrongPassword {  
    String message() default "Password strength insufficient";
    Class<?>[] groups() default {};  
    Class<? extends Payload>[] payload() default {};  
}  