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
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
            .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID " + benutzerId + " nicht gefunden"));
        
        TrinkenDaten daten = trinkenRepository.getByDatumUndBenutzer(datum, benutzerId);
        if (daten == null) {
            TrinkenDaten neueDaten = new TrinkenDaten();
            neueDaten.setBenutzer(benutzer);
            neueDaten.setBecher(0);
            neueDaten.setDatum(datum);
            neueDaten.setLiter(0.);
            return neueDaten;
        }
        return daten;
        
    }

    public void putTrinken(TrinkenDaten daten, Integer benutzerId) {    
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
                .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID " + benutzerId + " nicht gefunden"));
                
        TrinkenDaten vorhandeneDaten = trinkenRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);
        
        if (vorhandeneDaten != null) {
            vorhandeneDaten.setLiter(daten.getLiter());
            vorhandeneDaten.setDatum(daten.getDatum());
            vorhandeneDaten.setBecher(daten.getBecher());
            trinkenRepository.save(vorhandeneDaten);
        } else {
            daten.setBenutzer(benutzer);
            trinkenRepository.save(daten);
        }
    }
    
}
