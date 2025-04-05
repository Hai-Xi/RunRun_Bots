package com.test.runrunbots.model.dto.order;

import com.test.runrunbots.model.DeliveryMethod;
import com.test.runrunbots.model.dto.valid.ValidDeliveryMethod;
import lombok.Data;

import java.time.LocalDateTime;

@Data  
public class CreateOrderRequest {
    /**
     * prder table
     */
    private Long userId;
    private String itemDescription;  
    private String pickupLocation;  
    private String deliveryLocation;

    @ValidDeliveryMethod
    private DeliveryMethod deliveryMethod; // ROBOT or DRONE

    /**
     *
     * LocalDateTime 是 java.time 引入的类型，在序列化为 JSON 时需要确保其格式可读。通常用到 Jackson 或别的序列化工具
     *
     * 1. jackson
     * spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
     * spring.jackson.time-zone=Asia/Shanghai
     *
     *
     * Spring Boot 或 RESTful API，LocalDateTime 在处理 JSON 请求和响应时可能需要格式化。默认情况下，Spring Boot 的
     * Jackson 会直接将 LocalDateTime 序列化为 ISO 8601 格式（yyyy-MM-dd'T'HH:mm:ss）。
     * 如果你希望自定义日期时间格式（如 yyyy-MM-dd HH:mm:ss），还可以在字段上使用 @JsonFormat 注解。
     * @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 自定义日期时间格式
     * private LocalDateTime estimatedArrivalTime;
     *
     *
     * 当你在 Controller 中接收 LocalDateTime 类型数据时，建议确保其格式符合预期，避免解析错误。例如，在参数中指定日期格式。
     * @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime newEstimatedArrivalTime
     * 规定 @DateTimeFormat，用户请求时必须按照 yyyy-MM-dd'T'HH:mm:ss 格式传递时间。
     *
     *
     * 日期时间格式：对外提供接口时，建议明确日期时间格式，并使用 @DateTimeFormat 或全局配置 spring.jackson/ 字段注解 @JsonFormat。
     * 序列化与反序列化：通过 Jackson（或类似工具）自动将 Java 对象与 JSON 互相转换。
     *
     */
    private LocalDateTime estimatedArrivalTime;

    /**
     * payment table
     */
    private Double totalAmount;   // Total order amount
    private String paymentMethod; // Payment method, e.g., Credit Card
    private String paymentStatus; // Payment status, e.g., PENDING

}  