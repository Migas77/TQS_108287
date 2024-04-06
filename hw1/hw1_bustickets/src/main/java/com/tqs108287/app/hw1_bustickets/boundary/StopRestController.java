package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.entities.Stop;
import com.tqs108287.app.hw1_bustickets.service.IStopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stops")
@AllArgsConstructor
public class StopRestController {

    private final IStopService siteService;

    @GetMapping
    public ResponseEntity<List<Stop>> getAllStops(){
        return ResponseEntity.ok(siteService.getAllStops());
    }
}
