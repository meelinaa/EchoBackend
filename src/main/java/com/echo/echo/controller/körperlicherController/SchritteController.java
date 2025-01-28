package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.service.körperlicherService.SchritteService;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/schritte")
public class SchritteController {

    private SchritteService schritteService;
    private Integer benutzerId = 1;

    public SchritteController(SchritteService schritteService){
        this.schritteService = schritteService;
    }

    @GetMapping("/{datum}")
    public ResponseEntity<SchritteDaten> getSchritte(@PathVariable LocalDate datum) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            return ResponseEntity.ok(schritteService.getSchritte(datum, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putSchritte(@RequestBody SchritteDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            schritteService.putSchritte(daten, benutzerId);        
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
