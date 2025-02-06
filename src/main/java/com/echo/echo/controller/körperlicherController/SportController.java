package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.service.analyse.AnalyseSportService;
import com.echo.echo.service.körperlicherService.SportService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sport")
public class SportController {

    private SportService sportService;
    private AnalyseSportService analyseSportService;
    private Integer benutzerId = 1;

    public SportController(SportService sportService, AnalyseSportService analyseSportService){
        this.sportService = sportService;
        this.analyseSportService = analyseSportService;
    }

    @GetMapping("/{datumReactFormat}")
    public ResponseEntity<SportDaten> getSport(@PathVariable String datumReactFormat) {
        if (datumReactFormat == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            LocalDate datum = LocalDate.parse(datumReactFormat, DateTimeFormatter.ofPattern("d.M.yyyy"));
            return ResponseEntity.ok(sportService.getSport(datum, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putSport(@RequestBody SportDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            sportService.putSport(daten, benutzerId);        
        return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/analyse")
    public ResponseEntity<List<SportDaten>> getTageAnalyse(@RequestBody String datumReactFormat, @RequestBody Integer anzahltage) {
        if (datumReactFormat == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate datum = LocalDate.parse(datumReactFormat, DateTimeFormatter.ofPattern("d.M.yyyy"));
            return ResponseEntity.ok(analyseSportService.getTageAnalyse(datum, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
