package com.echo.echo.controller.persönlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.service.persönlicherService.BenutzerService;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/benutzer")
public class BenutzerController {
    
    private BenutzerService benutzerService;

    public BenutzerController(BenutzerService benutzerService){
        this.benutzerService = benutzerService;
    }

    @GetMapping("/allgemein")
    public ResponseEntity<AllgemeineDaten> getAlleAllgemeinenDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleAllgemeinenDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/träume")
    public ResponseEntity<List<TräumeDaten>> getAlleTräumeDaten() {
        return ResponseEntity.ok(benutzerService.getAlleTräumeDaten());
        try {
            return ResponseEntity.ok(benutzerService.getAlleAllgemeinenDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/schlaf")
    public ResponseEntity<List<SchlafDaten>> getAlleSchlafDaten() {
        return ResponseEntity.ok(benutzerService.getAlleSchlafDaten());
        try {
            return ResponseEntity.ok(benutzerService.getAlleAllgemeinenDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/schritte")
    public ResponseEntity<List<SchritteDaten>> getAlleSchritteDaten() {
        return ResponseEntity.ok(benutzerService.getAlleSchritteDaten());
        try {
            return ResponseEntity.ok(benutzerService.getAlleAllgemeinenDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/gedanken")
    public ResponseEntity<List<GedankenDaten>> getAlleGedankenDaten() {
        return ResponseEntity.ok(benutzerService.getAlleGedankenDaten());
        try {
            return ResponseEntity.ok(benutzerService.getAlleAllgemeinenDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/sport")
    public ResponseEntity<List<SportDaten>> getAlleSportDaten() {
        return ResponseEntity.ok(benutzerService.getAlleSportDaten());
        try {
            return ResponseEntity.ok(benutzerService.getAlleAllgemeinenDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/trinken")
    public ResponseEntity<List<TrinkenDaten>> getAlleTrinkenDaten() {
        return ResponseEntity.ok(benutzerService.getAlleTrinkenDaten());
    }

    @GetMapping("/gemüt")
    public ResponseEntity<List<GemütDaten>> getAlleGemütDaten() {
        return ResponseEntity.ok(benutzerService.getAlleGemütDaten());
    }
}
