package com.echo.echo.service.persönlicherService;

import org.springframework.stereotype.Service;

import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.persönlicherRepository.AllgemeinRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AllgemeinerService {

	private AllgemeinRepository allgemeinRepository;
	private BenutzerRepository benutzerRepository;

    public AllgemeinerService (AllgemeinRepository allgemeinRepository, BenutzerRepository benutzerRepository){
        this.allgemeinRepository = allgemeinRepository;
        this.benutzerRepository = benutzerRepository;
    }

    public AllgemeineDaten getAllgemeinenDaten(Integer benutzerId) {        
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }
        try {
            AllgemeineDaten daten = allgemeinRepository.findAllById(benutzerId);
            if (daten == null) {
                throw new EntityNotFoundException("Keine Daten gefunden für Benutzer-ID " + benutzerId);
            }
            return daten;
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), e);
        }
    }

    public void putAlleAllgemeinenDaten(Integer benutzerId, AllgemeineDaten daten) {
        if (daten == null) {
            throw new IllegalArgumentException("Daten dürfen nicht null sein");
        }
        if (benutzerId == null) {
            throw new IllegalArgumentException("Benutzer ID darf nicht null sein");
        }

        Benutzer benutzer = benutzerRepository.findById(benutzerId)
                .orElseThrow(() -> new EntityNotFoundException("Benutzer mit ID " + benutzerId + " nicht gefunden"));
                
        try {
            AllgemeineDaten vorhandeneDaten = benutzer.getAllgemein();

            if (vorhandeneDaten != null) {
                vorhandeneDaten.setGröße(daten.getGröße());
                vorhandeneDaten.setGewicht(daten.getGewicht());
                vorhandeneDaten.setAlter(daten.getAlter());
                vorhandeneDaten.setGeschlecht(daten.getGeschlecht());
                vorhandeneDaten.setBmi(daten.getBmi());
                allgemeinRepository.save(vorhandeneDaten);
            } else {
                daten.setBenutzer(benutzer);
                allgemeinRepository.save(daten);
            }
        } catch (Exception e) {
            throw new RuntimeException("Ein unerwarteter Fehler ist aufgetreten: " + e.getMessage(), e);
        }
    }
}
    

