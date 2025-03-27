package com.test.runrunbots.external;

import com.test.runrunbots.external.model.GeoResponse;
import com.test.runrunbots.external.model.RouteRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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
