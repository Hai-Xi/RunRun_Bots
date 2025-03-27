package com.test.runrunbots.external;

import com.alibaba.fastjson2.JSON;
import com.test.runrunbots.external.model.RouteRequest;
import com.test.runrunbots.external.model.RouteResponse;
import com.test.runrunbots.utils.PolylineDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public RouteResponse getDecodedRoutes() {

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

        // 调用 Feign Client 获取响应
        String response = feignClient.getRoutes(routeRequest);
        log.info("调用 Feign Client 获取响应 - Routes response - jsonstring: {} ", response);
        // Deserialize JSON to (RouteResponse) Java object
        RouteResponse jsonObject = JSON.parseObject(response, RouteResponse.class);
        log.info("调用 Feign Client 获取响应 - Routes response - object: {} ", jsonObject);

        // 解码 polyline 数据
        StringBuilder result = new StringBuilder();
        RouteResponse routeResponse = null;
        for (RouteResponse.Route route : jsonObject.getRoutes()) {
            result.append("Distance: ").append(route.getDistanceMeters()).append(" meters\n");
            result.append("Duration: ").append(route.getDuration()).append("\n");
            result.append("Decoded Polyline Points:\n");

            String encodedPolyline = route.getPolyline().getEncodedPolyline();
            List<PolylineDecoder.LatLng> points = PolylineDecoder.decodePolyline(encodedPolyline);

            for (PolylineDecoder.LatLng point : points) {
                result.append("  ").append(point).append("\n");
            }

            log.info("result.toString(): {}", result.toString());

            RouteResponse.Route modifiedRoute = new RouteResponse.Route();
            modifiedRoute.setDistanceMeters(route.getDistanceMeters());
            modifiedRoute.setDuration(route.getDuration());
            modifiedRoute.setPolyline(route.getPolyline());
            modifiedRoute.setCoordinates(points);
            log.info("modifiedRoute.toString(): {}", modifiedRoute.toString());

            routeResponse = new RouteResponse();
            List<RouteResponse.Route> routes = routeResponse.getRoutes();
            routes = new ArrayList<>();
            routes.add(modifiedRoute);
            routeResponse.setRoutes(routes);


            RouteResponse.Route data = jsonObject.getRoutes().get(0);
            // 直接使用 data 对象，不要尝试转换为 List
            int distance = data.getDistanceMeters();
            String duration = data.getDuration();

            routeResponse.setRoutes(routes);
        }
        return routeResponse;
    }

}  