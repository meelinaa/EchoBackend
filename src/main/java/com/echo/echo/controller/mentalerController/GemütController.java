package com.echo.echo.controller.mentalerController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.service.mentalerService.GemütService;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/gemüt")
public class GemütController {
    
    private GemütService gemütService;
    private Integer benutzerId = 1;

    public GemütController(GemütService gemütService){
        this.gemütService = gemütService;
    }

    @GetMapping("/{datum}")
    public ResponseEntity<GemütDaten> getGemüt(@PathVariable LocalDate datum) {
        return ResponseEntity.ok(gemütService.getGemüt(datum, benutzerId));
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putGemüt(@RequestBody GemütDaten daten) {
        gemütService.putGemüt(daten, benutzerId);        
        return ResponseEntity.ok().build();
    }
    
}
