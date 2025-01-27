package com.echo.echo.controller.mentalerController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.service.mentalerService.GedankenService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/gedanken")
@AllArgsConstructor
@NoArgsConstructor
public class GedankenController {

    private GedankenService gedankenService;

    @GetMapping("/{datum}")
    public ResponseEntity<GedankenDaten> getGedanken(@PathVariable LocalDate datum) {
        return ResponseEntity.ok(gedankenService.getGedanken(datum));
    }

    @PutMapping("hinzufügen")
    public ResponseEntity<Void> putGedanken(@RequestBody GedankenDaten gedankenDaten) {
        gedankenService.putGedanken(gedankenDaten);        
        return ResponseEntity.ok().build();
    }
    
}
