package com.echo.echo.service.mentalerService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.mentalerRepository.GedankenRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

@Service
public class GedankenService {
    private GedankenRepository gedankenRepository;
    private BenutzerRepository benutzerRepository;

    public GedankenService(GedankenRepository gedankenRepository, BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
        this.gedankenRepository = gedankenRepository;
    }

    public GedankenDaten getGedanken(LocalDate datum, Integer benutzerId) {
        return gedankenRepository.getByDatumUndBenutzer(datum, benutzerId);
    }

    public void putGedanken(GedankenDaten daten, Integer benutzerId) {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
        .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID " + benutzerId + " nicht gefunden"));

        GedankenDaten vorhandeneDaten = gedankenRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

        if (vorhandeneDaten != null) {
            vorhandeneDaten.setGedanken(daten.getGedanken());
            gedankenRepository.save(vorhandeneDaten);
        } else {
            daten.setBenutzer(benutzer);
            gedankenRepository.save(daten);
        }
    }
    
}
