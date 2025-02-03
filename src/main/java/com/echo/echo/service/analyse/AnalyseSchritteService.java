package com.echo.echo.service.analyse;

import java.time.LocalDate;
import java.util.List;

import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.service.körperlicherService.SchritteService;

public class AnalyseSchritteService {

    private final SchritteService schritteService;
    private Integer benutzerId = 1;

    public AnalyseSchritteService(SchritteService schritteService){
        this.schritteService = schritteService;
    }

    public List<SchritteDaten> getTageAnalyse(LocalDate heute, Integer anzahltage) {
        if (heute == null) {
            throw new IllegalArgumentException("Es wurde kein aktuelles Datum weitergegeben.");
        }
        try {
            List<SchritteDaten> woche = List.of(new SchritteDaten());

            int i = 0;
            do { 
                LocalDate datum = heute.minusDays(i);
                SchritteDaten tag = schritteService.getSchritte(datum, benutzerId);
                woche.add(tag);
                i--; 
            } while (i < anzahltage); 

            return woche;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Daten abgerufen werden.");
        }
    }
    
}
