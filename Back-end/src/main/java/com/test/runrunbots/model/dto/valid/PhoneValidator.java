package com.test.runrunbots.model.dto.valid;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 校验逻辑：电话号码只能包含数字，且长度为10-15
        return value != null && value.matches("^[0-9]{10,15}$");
    }
}
