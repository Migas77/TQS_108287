package com.tqs108287.app.hw1_bustickets.boundary;

import com.tqs108287.app.hw1_bustickets.entities.Site;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sites")
public class SiteRestController {

    @GetMapping
    public ResponseEntity<List<Site>> getSites(){
        return null;
    }

}
