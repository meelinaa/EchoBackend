package com.echo.echo.service.persönlicherService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;


@Service
public class BenutzerService {

    private BenutzerRepository benutzerRepository;

    public BenutzerService(BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
    }

    Integer benutzerId = 1;

    public Benutzer getAlles() {
        System.out.println("BENUTZER SERVICE !!!!!!!!!!!!!!!!!!1");
        return benutzerRepository.findByIdWithAllData(benutzerId);
    }

    public AllgemeineDaten getAlleAllgemeinenDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID 1 nicht gefunden"));
        return benutzer.getAllgemein();
    }

    public List<TräumeDaten> getAlleTräumeDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID 1 nicht gefunden"));
        return benutzer.getTräume();
    }
    
    public List<SchlafDaten> getAlleSchlafDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID 1 nicht gefunden"));
        return benutzer.getSchlaf();
    }

    public List<SchritteDaten> getAlleSchritteDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID 1 nicht gefunden"));
        return benutzer.getSchritte();
    }

    public List<GedankenDaten> getAlleGedankenDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID 1 nicht gefunden"));
        return benutzer.getGedanken();
    }

    public List<SportDaten> getAlleSportDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID 1 nicht gefunden"));
        return benutzer.getSport();
    }

    public List<TrinkenDaten> getAlleTrinkenDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID 1 nicht gefunden"));
        return benutzer.getTrinken();
    }

    public List<GemütDaten> getAlleGemütDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID 1 nicht gefunden"));
        return benutzer.getGemüt();
    }
    
}
