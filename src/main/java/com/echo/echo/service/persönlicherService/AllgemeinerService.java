package com.echo.echo.service.persönlicherService;

import org.springframework.stereotype.Service;

import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.persönlicherRepository.AllgemeinRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;


@Service
public class AllgemeinerService {

	private AllgemeinRepository allgemeinRepository;
	private BenutzerRepository benutzerRepository;

    public AllgemeinerService (AllgemeinRepository allgemeinRepository, BenutzerRepository benutzerRepository){
        this.allgemeinRepository = allgemeinRepository;
        this.benutzerRepository = benutzerRepository;
    }

    public AllgemeineDaten getAllgemeinenDaten(Integer id) {
        System.out.println("AllgemeinerService aufgerufen !!!!!!!!!!!!!!");
        return allgemeinRepository.findAllById(id);
    }

    public void setAlleAllgemeinenDaten(Integer benutzerId, AllgemeineDaten daten) {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
        .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID " + benutzerId + " nicht gefunden"));

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
    }
}
    

