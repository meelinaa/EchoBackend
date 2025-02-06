package com.echo.echo.service.analyse;

import java.time.LocalDate;
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
            List<TrinkenDaten> woche = List.of(new TrinkenDaten());

            int i = 0;
            do { 
                LocalDate datum = heute.minusDays(i);
                TrinkenDaten tag = trinkenService.getTrinken(datum, benutzerId);
                woche.add(tag);
                i--; 
            } while (i < anzahltage); 

            return woche;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Daten abgerufen werden.");
        }
    }

}
