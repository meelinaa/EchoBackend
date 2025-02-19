package com.echo.echo.controller.mentalerController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.service.analyse.AnalyseTräumeService;
import com.echo.echo.service.mentalerService.TräumeService;
import com.echo.echo.service.persönlicherService.BenutzerService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/träume")
public class TräumeController {
    
    private TräumeService träumeService;
    private BenutzerService benutzerService;
    private AnalyseTräumeService analyseTräumeService;

    public TräumeController(TräumeService träumeService, BenutzerService benutzerService, AnalyseTräumeService analyseTräumeService){
        this.träumeService = träumeService;
        this.benutzerService = benutzerService;
        this.analyseTräumeService = analyseTräumeService;
    }
    private Integer benutzerId = 1;

    @GetMapping("/{datum}")
    public ResponseEntity<TräumeDaten> getTraum(@PathVariable String datum) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(träumeService.getTraum(parsedDate, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<String> putTraum(@RequestBody TräumeDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            träumeService.putTraum(daten, benutzerId);
            return ResponseEntity.ok().body("Daten wurden gespeichert");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Daten konnten nicht gespeichert werden:" +  e.getMessage());
        }
    }

    @GetMapping("/analyse")
    public ResponseEntity<List<TräumeDaten>> getAlleTräumeDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleTräumeDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }  
    }

    @GetMapping("/analyse/durchschnittBewertung/{datum}/{anzahltage}")
    public ResponseEntity<Integer> getDurchschnittBewertung(@PathVariable String datum, @PathVariable Integer anzahltage) {
        if (datum == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate parsedDate = LocalDate.parse(datum);
            return ResponseEntity.ok(analyseTräumeService.getDurchschnittBewertung(parsedDate, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
}
