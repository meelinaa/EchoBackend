package com.echo.echo.service.körperlicherService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.SportRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

@Service
public class SportService {

    private SportRepository sportRepository;
    private BenutzerRepository benutzerRepository;

    public SportService(SportRepository sportRepository, BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
        this.sportRepository = sportRepository;
    }

    public SportDaten getSport(LocalDate datum, Integer benutzerId) {
        return sportRepository.getByDatumUndBenutzer(datum, benutzerId);
    }

    public void putSport(SportDaten daten, Integer benutzerId) {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
        .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID " + benutzerId + " nicht gefunden"));

        SportDaten vorhandeneDaten = sportRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

        if (vorhandeneDaten != null) {
            vorhandeneDaten.setSportart(daten.getSportart());
            vorhandeneDaten.setTrauningsDauer(daten.getTrauningsDauer());
            sportRepository.save(vorhandeneDaten);
        } else {
            daten.setBenutzer(benutzer);
            sportRepository.save(daten);
        }
    }
    
}
