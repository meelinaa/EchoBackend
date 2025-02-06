package com.echo.echo.controller.mentalerController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.service.mentalerService.GedankenService;
import com.echo.echo.service.persönlicherService.BenutzerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private Integer benutzerId = 1;

    public GedankenController(GedankenService gedankenService, BenutzerService benutzerService){
        this.gedankenService = gedankenService;
        this.benutzerService = benutzerService;
    }

    @GetMapping("/{datumReactFormat}")
    public ResponseEntity<GedankenDaten> getGedanken(@PathVariable String datumReactFormat) {
        if (datumReactFormat == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            LocalDate datum = LocalDate.parse(datumReactFormat, DateTimeFormatter.ofPattern("d.M.yyyy"));

            return ResponseEntity.ok(gedankenService.getGedanken(datum, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putGedanken(@RequestBody GedankenDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            gedankenService.putGedanken(daten, benutzerId);        
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
    
}
