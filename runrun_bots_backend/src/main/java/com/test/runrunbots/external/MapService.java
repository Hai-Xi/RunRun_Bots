package com.test.runrunbots.external;


import com.test.runrunbots.external.model.GeoResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {


   private final MapApiClient mapApiClient;


   public MapService(MapApiClient mapApiClient) {
       this.mapApiClient = mapApiClient;
   }


    public GeoResponse getRoutes(String key) {
        return mapApiClient.getRoutes(key);
    }

}
