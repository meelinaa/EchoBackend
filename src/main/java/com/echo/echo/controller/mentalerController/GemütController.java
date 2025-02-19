package com.echo.echo.controller.mentalerController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.service.mentalerService.GemütService;
import com.echo.echo.service.persönlicherService.BenutzerService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// ---- WIRD ERSTMAL NICHT BEARBEITET ---

@RestController
@RequestMapping("/gemüt")
public class GemütController {
    
    private GemütService gemütService;
    private BenutzerService benutzerService;
    private Integer benutzerId = 1;

    public GemütController(GemütService gemütService, BenutzerService benutzerService){
        this.gemütService = gemütService;
        this.benutzerService = benutzerService;
    }

    @GetMapping("/{datumReactFormat}")
    public ResponseEntity<GemütDaten> getGemüt(@PathVariable @DateTimeFormat(pattern = "d.M.yyyy") LocalDate datum) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            return ResponseEntity.ok(gemütService.getGemüt(datum, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putGemüt(@RequestBody GemütDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            gemütService.putGemüt(daten, benutzerId);        
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/analyse")
    public ResponseEntity<List<GemütDaten>> getAlleGemütDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleGemütDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }  
    }
    
}
