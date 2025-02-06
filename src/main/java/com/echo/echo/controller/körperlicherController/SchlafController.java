package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.service.analyse.AnalyseSchlafService;
import com.echo.echo.service.körperlicherService.SchlafService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


    @GetMapping("/{datumReactFormat}")
    public ResponseEntity<SchlafDaten> getSchlaf(@PathVariable String datumReactFormat) {
        if (datumReactFormat == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            LocalDate datum = LocalDate.parse(datumReactFormat, DateTimeFormatter.ofPattern("d.M.yyyy"));
            return ResponseEntity.ok(schlafService.getSchlaf(datum, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putSchlaf(@RequestBody SchlafDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        try {
            schlafService.putSchlaf(daten, benutzerId);        
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/analyse")
    public ResponseEntity<List<SchlafDaten>> getTageAnalyse(@RequestBody String datumReactFormat, @RequestBody Integer anzahltage) {
        if (datumReactFormat == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate datum = LocalDate.parse(datumReactFormat, DateTimeFormatter.ofPattern("d.M.yyyy"));
            return ResponseEntity.ok(analyseSchlafService.getTageAnalyse(datum, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/analyse/durchschnitt/zeit")
    public ResponseEntity<String> getDurchschnittZeit(@RequestBody String datumReactFormat, @RequestBody Integer anzahltage) {
        if (datumReactFormat == null || anzahltage == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            LocalDate datum = LocalDate.parse(datumReactFormat, DateTimeFormatter.ofPattern("d.M.yyyy"));
            return ResponseEntity.ok(analyseSchlafService.getDurchschnittZeit(datum, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
    
}
