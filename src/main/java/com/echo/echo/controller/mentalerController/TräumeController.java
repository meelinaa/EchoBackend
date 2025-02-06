package com.echo.echo.controller.mentalerController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.service.mentalerService.TräumeService;
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
@RequestMapping("/träume")
public class TräumeController {
    
    private TräumeService träumeService;
    private BenutzerService benutzerService;

    public TräumeController(TräumeService träumeService, BenutzerService benutzerService){
        this.träumeService = träumeService;
        this.benutzerService = benutzerService;
    }
    private Integer benutzerId = 1;

    @GetMapping("/{datumReactFormat}")
    public ResponseEntity<TräumeDaten> getTraum(@PathVariable String datumReactFormat) {
        if (datumReactFormat == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            LocalDate datum = LocalDate.parse(datumReactFormat, DateTimeFormatter.ofPattern("d.M.yyyy"));
            return ResponseEntity.ok(träumeService.getTraum(datum, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putTraum(@RequestBody TräumeDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            träumeService.putTraum(daten, benutzerId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/analyse")
    public ResponseEntity<List<TräumeDaten>> getAlleTräumeDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleTräumeDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }  
    }
    
}
