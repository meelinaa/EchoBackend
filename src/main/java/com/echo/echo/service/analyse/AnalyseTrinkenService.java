package com.echo.echo.service.analyse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.service.körperlicherService.TrinkenService;

@Service
public class AnalyseTrinkenService {
    
    private final TrinkenService trinkenService;
    private Integer benutzerId = 1;

    public AnalyseTrinkenService(TrinkenService trinkenService){
        this.trinkenService = trinkenService;
    }

    public List<TrinkenDaten> getTageAnalyse(LocalDate heute, Integer anzahltage) {
        if (heute == null) {
            throw new IllegalArgumentException("Es wurde kein aktuelles Datum weitergegeben.");
        }
        try {
            List<TrinkenDaten> woche = new ArrayList<>();

            for (int i = 0; i < anzahltage; i++) {
                LocalDate datum = heute.minusDays(i);
                TrinkenDaten tag = trinkenService.getTrinken(datum, benutzerId);
                woche.add(tag);
            }

            return woche;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Daten abgerufen werden.");
        }
        
    }

    public Double getDurchschnittLiter(LocalDate heute, Integer anzahltage) {
        List<TrinkenDaten> woche = getTageAnalyse(heute, anzahltage);

        Double summe = 0.;
        for (TrinkenDaten tag : woche) {
            summe += tag.getLiter();
        }

        Double durchschnitt = summe / anzahltage;

        return durchschnitt;
    }

    public Integer getDurchschnittBecher(LocalDate heute, Integer anzahltage) {
        List<TrinkenDaten> woche = getTageAnalyse(heute, anzahltage);

        Integer summe = 0;
        for (TrinkenDaten tag : woche) {
            summe += tag.getBecher();
        }

        Integer durchschnitt = (summe / anzahltage);
        return durchschnitt;
    }

}
