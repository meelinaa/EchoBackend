package com.echo.echo.service.analyse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.service.mentalerService.TräumeService;

@Service
public class AnalyseTräumeService {

    private final TräumeService träumeService;
    private Integer benutzerId = 1;

    public AnalyseTräumeService(TräumeService träumeService){
        this.träumeService = träumeService;
    }

    public List<TräumeDaten> getTageAnalyse(LocalDate heute, Integer anzahltage) {
        if (heute == null) {
            throw new IllegalArgumentException("Es wurde kein aktuelles Datum weitergegeben.");
        }
        try {
            List<TräumeDaten> woche = new ArrayList<>();

            for (int i = 0; i < anzahltage; i++) {
                LocalDate datum = heute.minusDays(i);
                TräumeDaten tag = träumeService.getTraum(datum, benutzerId);
                woche.add(tag);
            }

            return woche;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Daten abgerufen werden.");
        }
    }

    public Integer getDurchschnittBewertung(LocalDate heute, Integer anzahltage) {
        List<TräumeDaten> woche = getTageAnalyse(heute, anzahltage);

        Integer summe = 0;
        for (TräumeDaten tag : woche) {
            summe += tag.getBewertung();
        }

        Integer durchschnitt = (summe / anzahltage);

        return durchschnitt;
    }
    
}
