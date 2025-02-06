package com.echo.echo.service.analyse;

import java.time.LocalDate;
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
            List<SportDaten> woche = List.of(new SportDaten());

            int i = 0;
            do { 
                LocalDate datum = heute.minusDays(i);
                SportDaten tag = sportService.getSport(datum, benutzerId);
                woche.add(tag);
                i--; 
            } while (i < anzahltage); 

            return woche;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Daten abgerufen werden.");
        }
    }
    
}
