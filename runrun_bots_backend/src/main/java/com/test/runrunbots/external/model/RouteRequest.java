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
 * - 简洁：可以通过链式调用直接构造复杂嵌套对象, 不再需要 创建多个中间对象 逐步调用 set()。
 * - 可读：链式调用构造每个子对象，Builder 模式让代码更易读，所有参数在一个流中完整展现。使用嵌套 @Builder 构造复杂的层级数据更加直观。
 * - 安全：减少了对象在后续修改中丢失字段的风险，创建即为不可变对象。
 * - 测试友好：( @Builder RouteResponse.java ) 更易于在单元测试中创建模拟数据。
 *
 * 测试和构建更简单
 * 使用 Builder ，这种之前需要很多行代码构建请求对象的复杂代码：
 *
 * java
 * // 先创建内层对象，再创建外层对象，层层嵌套
 * RouteRequest.LatLng originLatLng = new RouteRequest.LatLng();
 * originLatLng.setLatitude(37.419734);
 * originLatLng.setLongitude(-122.0827784);
 *
 * RouteRequest.Location originLocation = new RouteRequest.Location();
 * originLocation.setLatLng(originLatLng);
 *
 * RouteRequest.LocationData origin = new RouteRequest.LocationData();
 * origin.setLocation(originLocation);
 *
 * // ... 还有很多代码
 * 变成了更简洁、更可读的链式调用：
 *
 * java
 * RouteRequest request = RouteRequest.builder()
 *     .origin(RouteRequest.LocationData.builder()
 *         .location(RouteRequest.Location.builder()
 *             .latLng(RouteRequest.LatLng.builder()
 *                 .latitude(37.419734)
 *                 .longitude(-122.0827784)
 *                 .build())
 *             .build())
 *         .build())
 *     // ... 其他属性
 *     .build();
 *
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