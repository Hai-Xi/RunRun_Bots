package com.test.runrunbots.external;

import com.test.runrunbots.external.model.GeoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MapController {


   private final MapService mapService;


   public MapController(MapService mapService) {
       this.mapService = mapService;
   }


   @PostMapping("/v2:computeRoutes")
   public GeoResponse getRoutes(@RequestParam(value = "key") String key,
                                @RequestBody Map<String, Map<String, Object>> requestBody) {

           return mapService.getRoutes(key,requestBody);

   }
}
