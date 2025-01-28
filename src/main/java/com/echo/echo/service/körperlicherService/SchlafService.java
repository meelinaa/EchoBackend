package com.echo.echo.service.körperlicherService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.SchlafRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

@Service
public class SchlafService {

    private SchlafRepository schlafRepository;
    private BenutzerRepository benutzerRepository;

    public SchlafService(SchlafRepository schlafRepository, BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
        this.schlafRepository = schlafRepository;
    }

    public SchlafDaten getSchlaf(LocalDate datum, Integer benutzerId) {
        return schlafRepository.getByDatumUndBenutzer(datum, benutzerId);

    }

    public void putSchlaf(SchlafDaten daten, Integer benutzerId) {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
        .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID " + benutzerId + " nicht gefunden"));

        SchlafDaten vorhandeneDaten = schlafRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

        if (vorhandeneDaten != null) {
            vorhandeneDaten.setSchlafBewertung(daten.getSchlafBewertung());
            vorhandeneDaten.setSchlafenszeit(daten.getSchlafenszeit());
            vorhandeneDaten.setDatum(daten.getDatum());
            schlafRepository.save(vorhandeneDaten);
        } else {
            daten.setBenutzer(benutzer);
            schlafRepository.save(daten);
        }
    }
    
}
