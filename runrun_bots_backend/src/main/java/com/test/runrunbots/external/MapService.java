package com.test.runrunbots.external;

import com.test.runrunbots.external.model.RouteRequest;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    private final MapApiClient feignClient;

    public MapService(MapApiClient feignClient) {
        this.feignClient = feignClient;
    }

    public String getRoutes() {
        // 查询参数（API Key）
        // String key = "a";

        // 构造请求体
        RouteRequest routeRequest = new RouteRequest();

        // 起点坐标  
        RouteRequest.LocationData origin = new RouteRequest.LocationData();
        RouteRequest.Location originLocation = new RouteRequest.Location();
        RouteRequest.LatLng originLatLng = new RouteRequest.LatLng();
        originLatLng.setLatitude(37.419734);
        originLatLng.setLongitude(-122.0827784);
        originLocation.setLatLng(originLatLng);
        origin.setLocation(originLocation);
        routeRequest.setOrigin(origin);

        // 终点坐标  
        RouteRequest.LocationData destination = new RouteRequest.LocationData();
        RouteRequest.Location destinationLocation = new RouteRequest.Location();
        RouteRequest.LatLng destinationLatLng = new RouteRequest.LatLng();
        destinationLatLng.setLatitude(37.417670);
        destinationLatLng.setLongitude(-122.079595);
        destinationLocation.setLatLng(destinationLatLng);
        destination.setLocation(destinationLocation);
        routeRequest.setDestination(destination);

        // 行车模式、偏好、语言等其他参数设置  
        routeRequest.setTravelMode("DRIVE");
        routeRequest.setRoutingPreference("TRAFFIC_AWARE");
        routeRequest.setComputeAlternativeRoutes(false);

        // 设置额外的路由修饰参数  
        RouteRequest.RouteModifiers routeModifiers = new RouteRequest.RouteModifiers();
        routeModifiers.setAvoidTolls(false);
        routeModifiers.setAvoidHighways(false);
        routeModifiers.setAvoidFerries(false);
        routeRequest.setRouteModifiers(routeModifiers);

        routeRequest.setLanguageCode("en-US");
        routeRequest.setUnits("IMPERIAL");

        // 调用 Feign 接口  
        return feignClient.getRoutes( // key,
                                      routeRequest);
    }
}  