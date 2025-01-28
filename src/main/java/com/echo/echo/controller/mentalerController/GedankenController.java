package com.echo.echo.controller.mentalerController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.service.mentalerService.GedankenService;


import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/gedanken")
public class GedankenController {

    private GedankenService gedankenService;
    private Integer benutzerId = 1;

    public GedankenController(GedankenService gedankenService){
        this.gedankenService = gedankenService;
    }

    @GetMapping("/{datum}")
    public ResponseEntity<GedankenDaten> getGedanken(@PathVariable LocalDate datum) {
        return ResponseEntity.ok(gedankenService.getGedanken(datum, benutzerId));
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putGedanken(@RequestBody GedankenDaten gedankenDaten) {
        gedankenService.putGedanken(gedankenDaten, benutzerId);        
        return ResponseEntity.ok().build();
    }
    
}
