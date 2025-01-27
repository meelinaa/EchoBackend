package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.service.körperlicherService.SchlafService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/allgemein")
@AllArgsConstructor
@NoArgsConstructor
public class SchlafController {

    private SchlafService schlafService;

    @GetMapping("/{datum}")
    public ResponseEntity<SchlafDaten> getSchlaf(@PathVariable LocalDate datum) {
        return ResponseEntity.ok(schlafService.getSchlaf(datum));
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putSchlaf(@RequestBody SchlafDaten daten) {
        schlafService.putSchlaf(daten);        
        return ResponseEntity.ok().build();
    }
    
}
