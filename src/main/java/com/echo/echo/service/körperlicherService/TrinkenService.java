package com.echo.echo.service.körperlicherService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.TrinkenRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TrinkenService {

    private TrinkenRepository trinkenRepository;
    private BenutzerRepository benutzerRepository;

    public TrinkenService(TrinkenRepository trinkenRepository, BenutzerRepository benutzerRepository){
        this.trinkenRepository = trinkenRepository;
        this.benutzerRepository = benutzerRepository;
    }

    public TrinkenDaten getTrinken(LocalDate datum, Integer benutzerId) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }
        try {
            TrinkenDaten daten = trinkenRepository.getByDatumUndBenutzer(datum, benutzerId);
            if (daten == null) {
                throw new EntityNotFoundException("Keine Daten gefunden für Datum " + datum + " und Benutzer-ID " + benutzerId);
            }
            return daten;
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten");
        }
    }

    public void putTrinken(TrinkenDaten daten, Integer benutzerId) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }

        Benutzer benutzer = benutzerRepository.findById(benutzerId)
                .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID " + benutzerId + " nicht gefunden"));
                
        try {
            TrinkenDaten vorhandeneDaten = trinkenRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

            if (vorhandeneDaten != null) {
                vorhandeneDaten.setLiter(daten.getLiter());
                vorhandeneDaten.setDatum(daten.getDatum());
                trinkenRepository.save(vorhandeneDaten);
            } else {
                daten.setBenutzer(benutzer);
                trinkenRepository.save(daten);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten");
        }
    }
    
}
