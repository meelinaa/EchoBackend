package com.echo.echo.service.körperlicherService;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.SchritteRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

@Service
public class SchritteService {

    private SchritteRepository schritteRepository;
    private BenutzerRepository benutzerRepository;

    public SchritteService(SchritteRepository schritteRepository, BenutzerRepository benutzerRepository){
        this.benutzerRepository = benutzerRepository;
        this.schritteRepository = schritteRepository;
    }

    public SchritteDaten getSchritte(LocalDate datum, Integer benutzerId) {
        return schritteRepository.getByDatumUndBenutzer(datum, benutzerId);

    }

    public void putSchritte(SchritteDaten daten, Integer benutzerId) {
        Benutzer benutzer = benutzerRepository.findById(benutzerId)
        .orElseThrow(() -> new IllegalArgumentException("Benutzer mit ID " + benutzerId + " nicht gefunden"));

        SchritteDaten vorhandeneDaten = schritteRepository.getByDatumUndBenutzer(daten.getDatum(), benutzerId);

        if (vorhandeneDaten != null) {
            vorhandeneDaten.setSchritte(daten.getSchritte());
            vorhandeneDaten.setMeter(daten.getMeter());
            schritteRepository.save(vorhandeneDaten);
        } else {
            daten.setBenutzer(benutzer);
            schritteRepository.save(daten);
        }
    }
    
}
