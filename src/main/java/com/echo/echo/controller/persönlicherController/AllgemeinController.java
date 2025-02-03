package com.echo.echo.controller.persönlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.service.persönlicherService.AllgemeinerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/allgemein")
public class AllgemeinController {

    private AllgemeinerService allgemeinerService;

    public AllgemeinController(AllgemeinerService allgemeinerService){
        this.allgemeinerService = allgemeinerService;
    }
    private Integer benutzerId = 1;

    @GetMapping("/alles")
    public ResponseEntity<AllgemeineDaten> getAllgemeineDaten() {
        try {
            return ResponseEntity.ok(allgemeinerService.getAllgemeinenDaten(benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }        
    }
    
    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putAlleAllgemeinenDaten(@RequestBody AllgemeineDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            allgemeinerService.putAlleAllgemeinenDaten(benutzerId, daten);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}


