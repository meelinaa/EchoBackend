package com.echo.echo.controller.persönlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.service.persönlicherService.AllgemeinerService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/allgemein")
@AllArgsConstructor
@NoArgsConstructor
public class AllgemeinController {

    private AllgemeinerService allgemeinerService;
    private Integer id = 1;

    @GetMapping("/alles")
    public ResponseEntity<AllgemeineDaten> getAlleAllgemeinenDaten() {
        return ResponseEntity.ok(allgemeinerService.getAlleAllgemeinenDaten(id));
    }
    
    @PutMapping("/alles")
    public ResponseEntity<Void> setAlleAllgemeinenDaten(@RequestBody AllgemeineDaten daten) {
        allgemeinerService.setAlleAllgemeinenDaten(id, daten);
        return ResponseEntity.ok().build();
    }

}


