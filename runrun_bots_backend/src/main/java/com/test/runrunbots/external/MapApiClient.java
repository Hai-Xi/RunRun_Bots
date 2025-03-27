package com.test.runrunbots.external;

import com.test.runrunbots.external.model.GeoResponse;
import com.test.runrunbots.external.model.RouteRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


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
 *
 */
@FeignClient(name = "map-api")
public interface MapApiClient {

   /**
    * 调用接口时支持传递查询参数和请求体
    *
    * // @param key 查询参数（如 API Key）
    * @param requestBody 请求体，包含复杂 JSON 数据
    * @return API 返回结果
    */
   @PostMapping(consumes = "application/json", produces = "application/json")
   String getRoutes(// @RequestParam("key") String key,
                    @RequestBody RouteRequest requestBody);

}
