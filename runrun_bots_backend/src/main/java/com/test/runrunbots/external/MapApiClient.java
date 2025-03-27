package com.test.runrunbots.external;

import com.test.runrunbots.external.model.RouteRequest;
import com.test.runrunbots.external.model.RouteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 *
 * 如果你的系统同时需要调用 Map Api 和其他 API（比如第三方支付接口、天气信息等），则用 OpenFeign 而不是 map java sdk 会更适合，因为它的声明式编程方式更通用。
 *                                                                                       ( https://developers.google.com/maps/documentation/geocoding/overview?hl=zh-cn )
 *                                                                                       ( https://github.com/googlemaps/google-maps-services-java )
 *
 * 如果你的项目采用微服务架构（Spring Cloud，例如 Eureka、Ribbon、OpenFeign ），OpenFeign 能与这些组件无缝集成。
 *
 * 如需在调用中添加非标准逻辑：拦截器（认证或日志）、动态代理，或自定义异常处理等。
 * OpenFeign 支持自定义配置和组件，开发自由度更高。
 *
 * ---------------------------------------------------------
 *
 * map api sdk ( implementation 'com.google.maps:google-maps-services:(insert latest version)' ) 是为了方便开发者更快、更简便地调用 Maps API. 它封装了：
 *
 * 请求地址管理（无需手动拼接 URL 和参数）。
 * 内置的 认证（如 API Key 自动注入）。
 * HTTP 请求的实现细节（通过底层 OkHttp 或 Apache HTTP Client 完成实际的网络调用）。
 * 响应结果处理（解析复杂嵌套的 JSON 到 Java 对象）。
 *
 * 封装了常见的细节，简化了 API 调用操作。
 * 面向对象：通常 map api sdk 会直接基于请求/响应提供 POJO 的封装，省去了自行编写解析代码的麻烦。
 *
 */
@FeignClient(name = "map-api")
public interface MapApiClient {

   /**
    * 调用接口时支持传递查询参数和请求体
    * <p>
    * // @param key 查询参数（如 API Key）
    *
    * @param requestBody 请求体，包含复杂 JSON 数据
    * @return API 返回结果
    */
   @PostMapping(consumes = "application/json", produces = "application/json")
   String getRoutes(// @RequestParam("key") String key,
                    @RequestBody RouteRequest requestBody);

}
