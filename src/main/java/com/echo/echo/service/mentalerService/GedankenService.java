package com.echo.echo.service.mentalerService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.mentalerRepository.GedankenRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GedankenService {
    private GedankenRepository gedankenRepository;
    private BenutzerRepository benutzerRepository;

    public GedankenService(GedankenRepository gedankenRepository, BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
        this.gedankenRepository = gedankenRepository;
    }

    public GedankenDaten getGedanken(LocalDate datum, Integer benutzerId) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }
        try {
            GedankenDaten daten = gedankenRepository.getByDatumUndBenutzer(datum, benutzerId);
            if (daten == null) {
                throw new EntityNotFoundException("Keine Daten gefunden für Datum " + datum + " und Benutzer-ID " + benutzerId);
            }
            return daten;
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), e);
        }
    }

    public void putGedanken(GedankenDaten daten, Integer benutzerId) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }

        Benutzer benutzer = benutzerRepository.findById(benutzerId)
                .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID " + benutzerId + " nicht gefunden"));
                
        try {
            GedankenDaten vorhandeneDaten = gedankenRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

            if (vorhandeneDaten != null) {
                vorhandeneDaten.setGedanken(daten.getGedanken());
                vorhandeneDaten.setDatum(daten.getDatum());
                gedankenRepository.save(vorhandeneDaten);
            } else {
                daten.setBenutzer(benutzer);
                gedankenRepository.save(daten);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), e);
        }  
    }
    
}
