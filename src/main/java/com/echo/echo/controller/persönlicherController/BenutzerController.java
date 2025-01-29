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
        try {
            return ResponseEntity.ok(benutzerService.getAlleTräumeDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/schlaf")
    public ResponseEntity<List<SchlafDaten>> getAlleSchlafDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleSchlafDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/schritte")
    public ResponseEntity<List<SchritteDaten>> getAlleSchritteDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleSchritteDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/gedanken")
    public ResponseEntity<List<GedankenDaten>> getAlleGedankenDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleGedankenDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/sport")
    public ResponseEntity<List<SportDaten>> getAlleSportDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleSportDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/trinken")
    public ResponseEntity<List<TrinkenDaten>> getAlleTrinkenDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleTrinkenDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }

    @GetMapping("/gemüt")
    public ResponseEntity<List<GemütDaten>> getAlleGemütDaten() {
        try {
            return ResponseEntity.ok(benutzerService.getAlleGemütDaten());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }  
    }
}
