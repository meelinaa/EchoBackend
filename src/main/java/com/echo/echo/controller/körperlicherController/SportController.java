package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.service.körperlicherService.SportService;


import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/sport")
public class SportController {

    private SportService sportService;
    private Integer benutzerId = 1;

    public SportController(SportService sportService){
        this.sportService = sportService;
    }

    @GetMapping("/{datum}")
    public ResponseEntity<SportDaten> getSport(@PathVariable LocalDate datum) {
        return ResponseEntity.ok(sportService.getSport(datum, benutzerId));
    }
    
    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putSport(@RequestBody SportDaten daten) {
        sportService.putSport(daten, benutzerId);        
        return ResponseEntity.ok().build();
    }
}
