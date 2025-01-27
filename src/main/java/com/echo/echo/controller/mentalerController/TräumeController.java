package com.echo.echo.controller.mentalerController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.service.mentalerService.TräumeService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/träume")
@AllArgsConstructor
@NoArgsConstructor
public class TräumeController {
    private TräumeService träumeService;
    private Integer benutzerId = 1;

    @GetMapping("/{datum}")
    public ResponseEntity<TräumeDaten> getAlleTraumInfos(@PathVariable LocalDate datum) {
        return ResponseEntity.ok(träumeService.getTraum(datum, benutzerId));
    }

    @PutMapping("/hinzufügen")
    public ResponseEntity<Void> putTraum(@RequestBody TräumeDaten daten) {
        träumeService.putTraum(daten, benutzerId);
        return ResponseEntity.ok().build();
    }
    
}
