package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.service.körperlicherService.SchlafService;


import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/schlaf")
public class SchlafController {

    private SchlafService schlafService;
    private Integer benutzerId = 1;

    public SchlafController(SchlafService schlafService){
        this.schlafService = schlafService;
    }


    @GetMapping("/{datum}")
    public ResponseEntity<SchlafDaten> getSchlaf(@PathVariable LocalDate datum) {
        System.out.println("SCHLAF CONTROLLER !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return ResponseEntity.ok(schlafService.getSchlaf(datum, benutzerId));
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putSchlaf(@RequestBody SchlafDaten daten) {
        schlafService.putSchlaf(daten, benutzerId);        
        return ResponseEntity.ok().build();
    }
    
}
