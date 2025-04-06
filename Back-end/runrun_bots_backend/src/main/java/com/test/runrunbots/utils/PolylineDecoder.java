package com.test.runrunbots.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * 解码工具类，根据 encodedPolyline 转换为经纬度坐标点。
 */
public class PolylineDecoder {  

    public static List<LatLng> decodePolyline(String encodedPolyline) {  
        List<LatLng> polyline = new ArrayList<>();  

        int index = 0;  
        int len = encodedPolyline.length();  
        int lat = 0, lng = 0;  

        while (index < len) {  
            int b, shift = 0, result = 0;  
            do {  
                b = encodedPolyline.charAt(index++) - 63;  
                result |= (b & 0x1f) << shift;  
                shift += 5;  
            } while (b >= 0x20);  
            int deltaLat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));  
            lat += deltaLat;  

            shift = 0;  
            result = 0;  
            do {  
                b = encodedPolyline.charAt(index++) - 63;  
                result |= (b & 0x1f) << shift;  
                shift += 5;  
            } while (b >= 0x20);  
            int deltaLng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));  
            lng += deltaLng;  

            polyline.add(new LatLng(lat / 1E5, lng / 1E5));  
        }  

        return polyline;  
    }  

    public static class LatLng {  
        private final double latitude;  
        private final double longitude;  

        public LatLng(double latitude, double longitude) {  
            this.latitude = latitude;  
            this.longitude = longitude;  
        }  

        public double getLatitude() {  
            return latitude;  
        }  

        public double getLongitude() {  
            return longitude;  
        }  

        @Override  
        public String toString() {  
            return "LatLng{" + "latitude=" + latitude + ", longitude=" + longitude + '}';  
        }  
    }  
}  