package com.test.runrunbots.external;


import com.test.runrunbots.external.model.GeoResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MapService {


   private final MapApiClient mapApiClient;


   public MapService(MapApiClient mapApiClient) {
       this.mapApiClient = mapApiClient;
   }


    public GeoResponse getRoutes(String key, Map<String, Map<String, Object>> requestBody) {
        return mapApiClient.getRoutes(key, requestBody);
    }

}
