package com.test.runrunbots.external;

import com.test.runrunbots.external.model.RouteResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class MapController {


   private final MapService mapService;


   public MapController(MapService mapService) {
       this.mapService = mapService;
   }


   @GetMapping("/routes")
   public String getRoutes() {

       return mapService.getRoutes();

   }

    @GetMapping("/routes/decoded")
    public RouteResponse getDecodedRoutes() {
        return mapService.getDecodedRoutes();
    }

}
