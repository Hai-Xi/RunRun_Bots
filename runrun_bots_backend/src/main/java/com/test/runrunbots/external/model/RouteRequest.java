package com.test.runrunbots.external.model;

import lombok.Data;

/**
 * requestBody 包含大量嵌套的结构，建议创建一个 Java DTO（数据传输对象），以便清晰地构造此 JSON 结构
 */
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
    public static class LocationData {  
        private Location location;  
    }  

    @Data  
    public static class Location {  
        private LatLng latLng;  
    }  

    @Data  
    public static class LatLng {  
        private double latitude;  
        private double longitude;  
    }  

    @Data  
    public static class RouteModifiers {  
        private boolean avoidTolls;  
        private boolean avoidHighways;  
        private boolean avoidFerries;  
    }  
}  