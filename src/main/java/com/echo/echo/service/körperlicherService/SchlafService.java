package com.echo.echo.service.körperlicherService;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.SchlafRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SchlafService {

    private SchlafRepository schlafRepository;
    private BenutzerRepository benutzerRepository;

    public SchlafDaten vorhandeneDaten;

    public SchlafService(SchlafRepository schlafRepository, BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
        this.schlafRepository = schlafRepository;
    }

    public SchlafDaten getSchlaf(LocalDate datum, Integer benutzerId) {
        if (datum == null) {
            throw new IllegalArgumentException("Datum darf nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }
        try {
            SchlafDaten daten = schlafRepository.getByDatumUndBenutzer(datum, benutzerId);
            if (daten == null) {
                SchlafDaten neueDaten = new SchlafDaten(); 
                neueDaten.setDatum(datum);
                neueDaten.setSchlafenszeit(LocalTime.parse("00:00"));
                return neueDaten;
            } else {
                return daten;
            }
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten");
        }
    }

    public void putSchlaf(SchlafDaten daten, Integer benutzerId) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }

        Benutzer benutzer = benutzerRepository.findById(benutzerId)
                .orElseThrow(() -> new EntityNotFoundException("Benutzer nicht gefunden"));
                
        try {
            vorhandeneDaten = schlafRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

            if (vorhandeneDaten != null) {
                vorhandeneDaten.setSchlafenszeit(daten.getSchlafenszeit());
                vorhandeneDaten.setDatum(daten.getDatum());
                schlafRepository.save(vorhandeneDaten);
            } else {
                daten.setBenutzer(benutzer);
                schlafRepository.save(daten);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten");
        }
    }
    
}
