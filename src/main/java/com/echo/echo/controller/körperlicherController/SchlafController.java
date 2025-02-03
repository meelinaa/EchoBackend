package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.service.analyse.AnalyseSchlafService;
import com.echo.echo.service.körperlicherService.SchlafService;

import java.time.LocalDate;
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
    public ResponseEntity<SchlafDaten> getSchlaf(@PathVariable LocalDate datum) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        try {
            return ResponseEntity.ok(schlafService.getSchlaf(datum, benutzerId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
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
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/analyse")
    public ResponseEntity<List<SchlafDaten>> getTageAnalyse(@RequestBody LocalDate heute, @RequestBody Integer anzahltage) {
        if (heute == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            return ResponseEntity.ok(analyseSchlafService.getTageAnalyse(heute, anzahltage));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
}
