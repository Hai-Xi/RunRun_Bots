package com.test.runrunbots.external.model;

import lombok.Builder;
import lombok.Data;

/**
 * requestBody 包含大量嵌套的结构，建议创建一个 Java DTO（数据传输对象），以便清晰地构造此 JSON 结构
 *
 *
 * @Builder, 现在，每个嵌套对象都可以通过 Builder 模式链式构造，避免重复写 Setter
 *
 * 适合用于构造复杂的嵌套对象，尤其是 API 调用（请求构造或响应解析）
 *
 */
@Builder
@Data  
public class RouteRequest {  

    private LocationData origin;  
    private LocationData destination;  
    private String travelMode;  
    private String routingPreference;  
    private boolean computeAlternativeRoutes;  
    private RouteModifiers routeModifiers;  
    private String languageCode;  
    private String units;  

    @Data
    @Builder // 嵌套类也使用 Builder
    public static class LocationData {  
        private Location location;  
    }  

    @Data
    @Builder // 嵌套类也使用 Builder
    public static class Location {  
        private LatLng latLng;  
    }  

    @Data
    @Builder // 嵌套类也使用 Builder
    public static class LatLng {  
        private double latitude;  
        private double longitude;  
    }  

    @Data
    @Builder // 嵌套类也使用 Builder
    public static class RouteModifiers {  
        private boolean avoidTolls;  
        private boolean avoidHighways;  
        private boolean avoidFerries;  
    }  
}  