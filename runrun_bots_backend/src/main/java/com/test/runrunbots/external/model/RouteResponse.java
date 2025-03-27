package com.test.runrunbots.external.model;

import lombok.Data;
import java.util.List;  

@Data  
public class RouteResponse {  

    private List<Route> routes;  

    @Data  
    public static class Route {  
        private int distanceMeters;  
        private String duration;  
        private Polyline polyline;  

        @Data  
        public static class Polyline {  
            private String encodedPolyline;  
        }  
    }  
}  