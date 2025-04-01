package com.test.runrunbots.model.dto.valid;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhone {
    String message() default "电话号码格式不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
