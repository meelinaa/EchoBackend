package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.service.körperlicherService.TrinkenService;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/trinken")
public class TrinkenController {

    private TrinkenService trinkenService;
    private Integer benutzerId = 1;

    public TrinkenController(TrinkenService trinkenService){
        this.trinkenService = trinkenService;
    }

    @GetMapping("/{datum}")
    public ResponseEntity<TrinkenDaten> getTrinken(@PathVariable LocalDate datum) {
        return ResponseEntity.ok(trinkenService.getTrinken(datum, benutzerId));
    }
    
    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putTrinken(@RequestBody TrinkenDaten daten) {
        trinkenService.putTrinken(daten, benutzerId);
        
        return ResponseEntity.ok().build();
    }
}
