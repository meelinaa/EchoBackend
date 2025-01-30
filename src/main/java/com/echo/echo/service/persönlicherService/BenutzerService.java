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

import jakarta.persistence.EntityNotFoundException;

@Service
public class BenutzerService {

    private BenutzerRepository benutzerRepository;

    public BenutzerService(BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
    }

    Integer benutzerId = 1;

    public AllgemeineDaten getAlleAllgemeinenDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID" + benutzerId + "nicht gefunden"));
        if (benutzer.getAllgemein() == null) {
            throw new EntityNotFoundException("AllgemeineDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.");
        }
        return benutzer.getAllgemein();
    }

    public List<TräumeDaten> getAlleTräumeDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID" + benutzerId + "nicht gefunden"));
        if (benutzer.getTräume() == null) {
            throw new EntityNotFoundException("TräumeDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.");
        }
        return benutzer.getTräume();
    }
    
    public List<SchlafDaten> getAlleSchlafDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID" + benutzerId + "nicht gefunden"));
        if (benutzer.getSchlaf() == null) {
            throw new EntityNotFoundException("SchlafDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.");
        }
        return benutzer.getSchlaf();
    }

    public List<SchritteDaten> getAlleSchritteDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID" + benutzerId + "nicht gefunden"));
        if (benutzer.getSchritte() == null) {
            throw new EntityNotFoundException("SchritteDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.");
        }
        return benutzer.getSchritte();
    }

    public List<GedankenDaten> getAlleGedankenDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID" + benutzerId + "nicht gefunden"));
        if (benutzer.getGedanken() == null) {
            throw new EntityNotFoundException("GedankenDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.");
        }
        return benutzer.getGedanken();
    }

    public List<SportDaten> getAlleSportDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID" + benutzerId + "nicht gefunden"));
        if (benutzer.getSport() == null) {
            throw new EntityNotFoundException("SportDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.");
        }
        return benutzer.getSport();
    }

    public List<TrinkenDaten> getAlleTrinkenDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID" + benutzerId + "nicht gefunden"));
        if (benutzer.getTrinken() == null) {
            throw new EntityNotFoundException("TrinkenDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.");
        }
        return benutzer.getTrinken();
    }

    public List<GemütDaten> getAlleGemütDaten() {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID" + benutzerId + "nicht gefunden"));
        if (benutzer.getGemüt() == null) {
            throw new EntityNotFoundException("GemütDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.");
        }
        return benutzer.getGemüt();
    }
    
}
