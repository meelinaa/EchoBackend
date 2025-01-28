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
        return ResponseEntity.ok(schritteService.getSchritte(datum, benutzerId));
    }
    
    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putSchritte(@RequestBody SchritteDaten daten) {
        schritteService.putSchritte(daten, benutzerId);        
        return ResponseEntity.ok().build();
    }
}
