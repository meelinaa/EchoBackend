package com.echo.echo.service.mentalerService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.mentalerRepository.GemütRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class GemütService {
    
    private GemütRepository gemütRepository;
    private BenutzerRepository benutzerRepository;

    public GemütService(GemütRepository gemütRepository, BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
        this.gemütRepository = gemütRepository;
    }

    public GemütDaten getGemüt(LocalDate datum, Integer benutzerId) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }
        try {
            GemütDaten daten = gemütRepository.getByDatumUndBenutzer(datum, benutzerId);
            if (daten == null) {
                throw new EntityNotFoundException("Keine Daten gefunden für Datum " + datum + " und Benutzer-ID " + benutzerId);
            }
            return daten;
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten");
        }
    }

    public void putGemüt(GemütDaten daten, Integer benutzerId) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }

        Benutzer benutzer = benutzerRepository.findById(benutzerId)
                .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID " + benutzerId + " nicht gefunden"));
                
        try {
            GemütDaten vorhandeneDaten = gemütRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

            if (vorhandeneDaten != null) {
                vorhandeneDaten.setBeschreibung(daten.getBeschreibung());
                vorhandeneDaten.setGemütszustand(daten.getGemütszustand());
                vorhandeneDaten.setGrund(daten.getGrund());
                vorhandeneDaten.setDatum(daten.getDatum());
                gemütRepository.save(vorhandeneDaten);
            } else {
                daten.setBenutzer(benutzer);
                gemütRepository.save(daten);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten");
        }
    }

}
