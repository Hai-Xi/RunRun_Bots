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
   public RouteResponse getRoutes() {

       return mapService.getRoutes();

   }

    @GetMapping("/routes/decoded")
    public String getDecodedRoutes() {
        return mapService.getDecodedRoutes();
    }

}
