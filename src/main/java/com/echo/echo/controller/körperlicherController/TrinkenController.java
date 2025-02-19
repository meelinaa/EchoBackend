package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.service.analyse.AnalyseTrinkenService;
import com.echo.echo.service.körperlicherService.TrinkenService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/trinken")
public class TrinkenController {

    private TrinkenService trinkenService;
    private AnalyseTrinkenService analyseTrinkenService;
    private Integer benutzerId = 1;

    public TrinkenController(TrinkenService trinkenService, AnalyseTrinkenService analyseTrinkenService){
        this.trinkenService = trinkenService;
        this.analyseTrinkenService = analyseTrinkenService;
    }

    @GetMapping("/{datum}")
    public ResponseEntity<TrinkenDaten> getTrinken(@PathVariable String datum) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(trinkenService.getTrinken(parsedDate, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/hinzufügen")
    public ResponseEntity<String> putTrinken(@RequestBody TrinkenDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            trinkenService.putTrinken(daten, benutzerId);
            return ResponseEntity.ok().body("Daten wurden gespeichert");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Daten konnten nicht gespeichert werden:" +  e.getMessage());
        }
    }

    @GetMapping("/analyse/{datum}/{anzahltage}")
    public ResponseEntity<List<TrinkenDaten>> getTageAnalyse(@PathVariable String datum, @PathVariable Integer anzahltage) {
        if (datum == null || anzahltage == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(analyseTrinkenService.getTageAnalyse(parsedDate, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/analyse/durchschnittLiter/{datum}/{anzahltage}")
    public ResponseEntity<Double> getDurchschnittLiter(@PathVariable String datum, @PathVariable Integer anzahltage) {
        if (datum == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(analyseTrinkenService.getDurchschnittLiter(parsedDate, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Anzahl Tage maximal 26 - Alles darüber löst 400 aus
    @GetMapping("/analyse/durchschnittBecher/{datum}/{anzahltage}")
    public ResponseEntity<Integer> getDurchschnittBecher(@PathVariable String datum, @PathVariable Integer anzahltage) {
        if (datum == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(analyseTrinkenService.getDurchschnittBecher(parsedDate, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
