package com.echo.echo.service.körperlicherService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.SchritteRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SchritteService {

    private SchritteRepository schritteRepository;
    private BenutzerRepository benutzerRepository;

    public SchritteService(SchritteRepository schritteRepository, BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
        this.schritteRepository = schritteRepository;
    }

    public SchritteDaten getSchritte(LocalDate datum, Integer benutzerId) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }
        try {
            SchritteDaten daten = schritteRepository.getByDatumUndBenutzer(datum, benutzerId);
            if (daten == null) {
                throw new EntityNotFoundException("Keine Daten gefunden für Datum " + datum + " und Benutzer-ID " + benutzerId);
            }
            return daten;
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), e);
        }

    }

    public void putSchritte(SchritteDaten daten, Integer benutzerId) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }

        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID " + benutzerId + " nicht gefunden"));

        try {
            SchritteDaten vorhandeneDaten = schritteRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

            if (vorhandeneDaten != null) {
                vorhandeneDaten.setSchritte(daten.getSchritte());
                vorhandeneDaten.setMeter(daten.getMeter());
                vorhandeneDaten.setDatum(daten.getDatum());
                schritteRepository.save(vorhandeneDaten);
            } else {
                daten.setBenutzer(benutzer);
                schritteRepository.save(daten);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), e);
        }  
    }
    
}
