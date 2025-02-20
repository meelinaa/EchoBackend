package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.service.analyse.AnalyseSchlafService;
import com.echo.echo.service.körperlicherService.SchlafService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/schlaf")
public class SchlafController {

    private SchlafService schlafService;
    private AnalyseSchlafService analyseSchlafService;
    private Integer benutzerId = 1;

    public SchlafController(SchlafService schlafService, AnalyseSchlafService analyseSchlafService){
        this.schlafService = schlafService;
        this.analyseSchlafService = analyseSchlafService;
    }


    @GetMapping("/{datum}")
    public ResponseEntity<SchlafDaten> getSchlaf(@PathVariable String datum) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(schlafService.getSchlaf(parsedDate, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<String> putSchlaf(@RequestBody SchlafDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            schlafService.putSchlaf(daten, benutzerId);        
            return ResponseEntity.ok().body("Daten wurden gespeichert");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Daten konnten nicht gespeichert werden:" +  e.getMessage());
        }
    }

    @GetMapping("/analyse/{datum}/{anzahltage}")
    public ResponseEntity<List<SchlafDaten>> getTageAnalyse(@PathVariable String datum, @PathVariable Integer anzahltage) {
        if (datum == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(analyseSchlafService.getTageAnalyse(parsedDate, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/analyse/durchschnitt/{datum}/{anzahltage}")
    public ResponseEntity<LocalTime> getDurchschnittZeit(@PathVariable String datum, @PathVariable Integer anzahltage) {
        if (datum == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(analyseSchlafService.getDurchschnittZeit(parsedDate, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
    
}
