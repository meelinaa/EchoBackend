package com.echo.echo.service.mentalerService;

import java.time.LocalDate;

import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.mentalerRepository.GemütRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GemütService {
    
    private GemütRepository gemütRepository;
    private BenutzerRepository benutzerRepository;

    public GemütDaten getGemüt(LocalDate datum, Integer benutzerId) {
        return gemütRepository.getByDatumUndBenutzer(datum, benutzerId);
    }

    public void putGemüt(GemütDaten daten, Integer benutzerId) {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
        .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID " + benutzerId + " nicht gefunden"));

        GemütDaten vorhandeneDaten = benutzer.getGemüt(daten.getDatum());

        if (vorhandeneDaten != null) {
            vorhandeneDaten.setBeschreibung(daten.getBeschreibung());
            vorhandeneDaten.setGemütszustand(daten.getGemütszustand());
            vorhandeneDaten.setGrund(daten.getGrund());
            gemütRepository.save(vorhandeneDaten);
        } else {
            daten.setBenutzer(benutzer);
            gemütRepository.save(daten);
        }
    }

    
}
