package com.echo.echo.service.körperlicherService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.SportRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SportService {

    private SportRepository sportRepository;
    private BenutzerRepository benutzerRepository;

    public SportService(SportRepository sportRepository, BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
        this.sportRepository = sportRepository;
    }

    public SportDaten getSport(LocalDate datum, Integer benutzerId) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }
        try {
            SportDaten daten = sportRepository.getByDatumUndBenutzer(datum, benutzerId);
            if (daten == null) {
                throw new EntityNotFoundException("Keine Daten gefunden für Datum " + datum + " und Benutzer-ID " + benutzerId);
            }
            return daten;
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten");
        }
    }

    public void putSport(SportDaten daten, Integer benutzerId) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }

        Benutzer benutzer = benutzerRepository.findById(benutzerId)
                .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID " + benutzerId + " nicht gefunden"));
                
        try {
            SportDaten vorhandeneDaten = sportRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

            if (vorhandeneDaten != null) {
                vorhandeneDaten.setSportart(daten.getSportart());
                vorhandeneDaten.setTrainingsDauer(daten.getTrainingsDauer());
                vorhandeneDaten.setDatum(daten.getDatum());
                sportRepository.save(vorhandeneDaten);
            } else {
                daten.setBenutzer(benutzer);
                sportRepository.save(daten);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), e);
        }
    }
    
}
