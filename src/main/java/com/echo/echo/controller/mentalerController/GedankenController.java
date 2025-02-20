package com.echo.echo.controller.mentalerController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.service.analyse.AnalyseGedankenService;
import com.echo.echo.service.mentalerService.GedankenService;
import com.echo.echo.service.persönlicherService.BenutzerService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/gedanken")
public class GedankenController {

    private GedankenService gedankenService;
    private BenutzerService benutzerService;
    private AnalyseGedankenService analyseGedankenService;
    private Integer benutzerId = 1;

    public GedankenController(GedankenService gedankenService, BenutzerService benutzerService, AnalyseGedankenService analyseGedankenService){
        this.gedankenService = gedankenService;
        this.benutzerService = benutzerService;
        this.analyseGedankenService = analyseGedankenService;
    }

    @GetMapping("/{datum}")
    public ResponseEntity<GedankenDaten> getGedanken(@PathVariable String datum) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(gedankenService.getGedanken(parsedDate, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<String> putGedanken(@RequestBody GedankenDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            gedankenService.putGedanken(daten, benutzerId);        
            return ResponseEntity.ok().body("Daten wurden gespeichert");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Daten konnten nicht gespeichert werden:" +  e.getMessage());
        }
    }

    @GetMapping("/analyse")
    public ResponseEntity<List<GedankenDaten>> getAlleGedankenDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleGedankenDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }  
    }

    @GetMapping("/analyse/anzahlGedanken/{datum}/{anzahltage}")
    public ResponseEntity<Integer> getAnzahlGedanken(@PathVariable String datum, @PathVariable Integer anzahltage) {
        if (datum == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(analyseGedankenService.getAnzahlGedanken(parsedDate, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
}
