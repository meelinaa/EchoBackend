package com.echo.echo.service.mentalerService;

import java.time.LocalDate;

import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.mentalerRepository.TräumeRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TräumeService {

    TräumeRepository träumeRepository;
    BenutzerRepository benutzerRepository;

    public TräumeDaten getTraum(LocalDate datum, Integer benutzerId) {
        return träumeRepository.getByDatumUndBenutzer(datum, benutzerId);
    }

    public void putTraum(TräumeDaten daten, Integer benutzerId) {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
        .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID " + benutzerId + " nicht gefunden"));

        TräumeDaten vorhandeneDaten = träumeRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

        if (vorhandeneDaten != null) {
            vorhandeneDaten.setBewertung(daten.getBewertung());
            vorhandeneDaten.setTraum(daten.getTraum());
            träumeRepository.save(vorhandeneDaten);
        } else {
            daten.setBenutzer(benutzer);
            träumeRepository.save(daten);
        }
    }
    
}
