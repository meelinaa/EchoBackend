package com.echo.echo.service.mentalerService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.mentalerRepository.TräumeRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

@Service
public class TräumeService {

    TräumeRepository träumeRepository;
    BenutzerRepository benutzerRepository;

    public TräumeService(TräumeRepository träumeRepository, BenutzerRepository benutzerRepository){
        this.träumeRepository = träumeRepository;
        this.benutzerRepository = benutzerRepository;
    }

    public TräumeDaten getTraum(LocalDate datum, Integer benutzerId) {
        System.out.println("TRÄUME SERVICE !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return träumeRepository.getByDatumUndBenutzer(datum, benutzerId);
    }

    public void putTraum(TräumeDaten daten, Integer benutzerId) {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
        .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID " + benutzerId + " nicht gefunden"));

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
    }
    
}
