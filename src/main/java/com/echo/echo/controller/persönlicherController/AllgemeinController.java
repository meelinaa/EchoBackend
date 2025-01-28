package com.echo.echo.controller.persönlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.service.persönlicherService.AllgemeinerService;

import lombok.RequiredArgsConstructor;

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
    public ResponseEntity<AllgemeineDaten> getAllgemeinenDaten() {
        System.out.println("AllgemeinController aufgerufen !!!!!!!!!!!!!!");
        return ResponseEntity.ok(allgemeinerService.getAllgemeinenDaten(benutzerId));
    }
    
    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> setAlleAllgemeinenDaten(@RequestBody AllgemeineDaten daten) {
        allgemeinerService.setAlleAllgemeinenDaten(benutzerId, daten);
        return ResponseEntity.ok().build();
    }

}


