package com.test.runrunbots.model.dto.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;  

public class PasswordStrengthValidator implements ConstraintValidator<StrongPassword, String> {  
    
    @Override  
    public boolean isValid(String password, ConstraintValidatorContext context) {  
        if (password == null) {  
            return false;  
        }  
        
        // 检查密码是否包含数字  
        boolean hasDigit = password.matches(".*\\d.*");  
        // 检查密码是否包含小写字母  
        boolean hasLower = password.matches(".*[a-z].*");  
        // 检查密码是否包含大写字母  
        boolean hasUpper = password.matches(".*[A-Z].*");  
        // 检查密码是否包含特殊字符  
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+].*");  
        
        return hasDigit && hasLower && hasUpper && hasSpecial;  
    }  
}  