package com.test.runrunbots.external;

import com.test.runrunbots.external.model.GeoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MapController {


   private final MapService mapService;


   public MapController(MapService mapService) {
       this.mapService = mapService;
   }


   @GetMapping("/v2:computeRoutes")
   public GeoResponse getRoutes(@RequestParam(value = "key") String key) {

           return mapService.getRoutes(key);

   }
}
