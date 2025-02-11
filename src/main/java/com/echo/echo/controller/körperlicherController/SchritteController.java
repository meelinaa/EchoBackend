package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.service.analyse.AnalyseSchritteService;
import com.echo.echo.service.körperlicherService.SchritteService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/schritte")
public class SchritteController {

    private SchritteService schritteService;
    private AnalyseSchritteService analyseSchritteService;
    private Integer benutzerId = 1;

    public SchritteController(SchritteService schritteService, AnalyseSchritteService analyseSchritteService2){
        this.schritteService = schritteService;
        this.analyseSchritteService = analyseSchritteService2;
    }

    @GetMapping("/{datum}")
    public ResponseEntity<SchritteDaten> getSchritte(@PathVariable String datum) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(schritteService.getSchritte(parsedDate, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/analyse/{datum}/{anzahltage}")
    public ResponseEntity<List<SchritteDaten>> getTageAnalyse(@PathVariable String datum, @PathVariable Integer anzahltage) {
        if (datum == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(analyseSchritteService.getTageAnalyse(parsedDate, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/analyse/durchschnittSchritte/{datum}/{anzahltage}")
    public ResponseEntity<Integer> getDurchschnittSchritte(@PathVariable String datum, @PathVariable Integer anzahltage) {
        if (datum == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(analyseSchritteService.getDurchschnittSchritte(parsedDate, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/analyse/durchschnittMeter/{datum}/{anzahltage}")
    public ResponseEntity<Double> getDurchschnittMeter(@PathVariable String datum, @PathVariable Integer anzahltage) {
        if (datum == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(analyseSchritteService.getDurchschnittMeter(parsedDate, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    

}
