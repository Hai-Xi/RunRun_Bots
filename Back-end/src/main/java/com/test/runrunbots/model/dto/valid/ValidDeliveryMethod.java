package com.test.runrunbots.model.dto.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;  

import java.lang.annotation.*;  

@Documented  
@Constraint(validatedBy = DeliveryMethodValidator.class) // 指定校验逻辑  
@Target({ElementType.FIELD, ElementType.PARAMETER})      // 适用范围：字段和方法参数  
@Retention(RetentionPolicy.RUNTIME)                     // 注解保留到运行时  
public @interface ValidDeliveryMethod {  

    // 默认的错误消息  
    String message() default "Invalid Delivery Method";  

    // 分组（标准属性，通常不需要修改）  
    Class<?>[] groups() default {};  

    // 用于附加信息传递（标准属性，通常不需要修改）  
    Class<? extends Payload>[] payload() default {};  
}  