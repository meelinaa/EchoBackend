package com.echo.echo.service.analyse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.service.mentalerService.GedankenService;

@Service
public class AnalyseGedankenService {

    private final GedankenService gedankenService;
    private Integer benutzerId = 1;

    public AnalyseGedankenService(GedankenService gedankenService){
        this.gedankenService = gedankenService;
    }

    public List<GedankenDaten> getTageAnalyse(LocalDate heute, Integer anzahltage) {
        if (heute == null) {
            throw new IllegalArgumentException("Es wurde kein aktuelles Datum weitergegeben.");
        }
        try {
            List<GedankenDaten> woche = new ArrayList<>();

            for (int i = 0; i < anzahltage; i++) {
                LocalDate datum = heute.minusDays(i);
                GedankenDaten tag = gedankenService.getGedanken(datum, benutzerId);
                woche.add(tag);
            }

            return woche;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Daten abgerufen werden.");
        }
    }

    public Integer getAnzahlGedanken(LocalDate heute, Integer anzahltage) {
        List<GedankenDaten> woche = getTageAnalyse(heute, anzahltage);

        int summe = 0;
        for (GedankenDaten tag : woche) {
            if(tag.getId() != null)
            summe ++;
        } 

        return summe;
    }
    
}
