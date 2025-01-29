package com.echo.echo.service.mentalerService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.mentalerRepository.TräumeRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TräumeService {

    TräumeRepository träumeRepository;
    BenutzerRepository benutzerRepository;

    public TräumeService(TräumeRepository träumeRepository, BenutzerRepository benutzerRepository){
        this.träumeRepository = träumeRepository;
        this.benutzerRepository = benutzerRepository;
    }

    public TräumeDaten getTraum(LocalDate datum, Integer benutzerId) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }
        try {
            TräumeDaten daten = träumeRepository.getByDatumUndBenutzer(datum, benutzerId);
            if (daten == null) {
                throw new EntityNotFoundException("Keine Daten gefunden für Datum " + datum + " und Benutzer-ID " + benutzerId);
            }
            return daten;
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), e);
        }
    }

    public void putTraum(TräumeDaten daten, Integer benutzerId) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }

        Benutzer benutzer = benutzerRepository.findById(benutzerId)
                .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID " + benutzerId + " nicht gefunden"));
                
        try {
            TräumeDaten vorhandeneDaten = träumeRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

            if (vorhandeneDaten != null) {
                vorhandeneDaten.setBewertung(daten.getBewertung());
                vorhandeneDaten.setTraum(daten.getTraum());
                vorhandeneDaten.setDatum(daten.getDatum());
                träumeRepository.save(vorhandeneDaten);
            } else {
                daten.setBenutzer(benutzer);
                träumeRepository.save(daten);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), e);
        }
    }
    
}
