package com.test.runrunbots.external;

import com.test.runrunbots.external.model.GeoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "map-api")
public interface MapApiClient {


   @GetMapping("/routes")
   GeoResponse getRoutes(@RequestParam("key") String key,
                         @RequestBody Map<String, Map<String, Object>> requestBody);

}
