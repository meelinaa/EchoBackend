package com.echo.echo.service.analyse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.service.körperlicherService.SportService;

@Service
public class AnalyseSportService {

    private final SportService sportService;
    private Integer benutzerId = 1;

    public AnalyseSportService(SportService sportService){
        this.sportService = sportService;
    }

    public List<SportDaten> getTageAnalyse(LocalDate heute, Integer anzahltage) {
        if (heute == null) {
            throw new IllegalArgumentException("Es wurde kein aktuelles Datum weitergegeben.");
        }
        try {
            List<SportDaten> woche = new ArrayList<>();

            for (int i = 0; i < anzahltage; i++) {
                LocalDate datum = heute.minusDays(i);
                SportDaten tag = sportService.getSport(datum, benutzerId);
                woche.add(tag);
            }

            return woche;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Daten abgerufen werden.");
        }
    }

    public LocalTime getDurchschnittTraining(LocalDate heute, Integer anzahltage) {
        List<SportDaten> woche = getTageAnalyse(heute, anzahltage);

        int summe = 0;
        for (SportDaten tag : woche) {
            summe += tag.getTrainingsDauer().toSecondOfDay();
        }        

        LocalTime durchschnitt = LocalTime.ofSecondOfDay(summe / anzahltage);

        return durchschnitt;
    }
    
    public SportDaten getKürzesteTrainingsdauer(LocalDate heute, Integer anzahltage) {
        List<SportDaten> woche = getTageAnalyse(heute, anzahltage);

        SportDaten kürzereTrainingsdauer = null;
        LocalTime trainingsDauerRef = LocalTime.MAX;

        for (SportDaten tag : woche) {
            if (tag.getTrainingsDauer().isBefore(trainingsDauerRef)) {
                kürzereTrainingsdauer = tag;
                trainingsDauerRef = tag.getTrainingsDauer();
            }
        }

        return kürzereTrainingsdauer;
    }

    public SportDaten getLängsteTrainingsdauer(LocalDate heute, Integer anzahltage) {
        List<SportDaten> woche = getTageAnalyse(heute, anzahltage);

        SportDaten längsteTrainingsdauer = null;
        LocalTime trainingsDauerRef = LocalTime.MIN;

        for (SportDaten tag : woche) {
            if (tag.getTrainingsDauer().isAfter(trainingsDauerRef)) {
                längsteTrainingsdauer = tag;
                trainingsDauerRef = tag.getTrainingsDauer();
            }
        }

        return längsteTrainingsdauer;
    }
}
