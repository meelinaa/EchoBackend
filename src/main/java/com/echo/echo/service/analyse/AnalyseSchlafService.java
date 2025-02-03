package com.echo.echo.service.analyse;

import java.time.LocalDate;
import java.util.List;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.service.körperlicherService.SchlafService;


public class AnalyseSchlafService {

    private final SchlafService schlafService;
    private Integer benutzerId = 1;

    public AnalyseSchlafService(SchlafService schlafService){
        this.schlafService = schlafService;
    }

    public List<SchlafDaten> getTageAnalyse(LocalDate heute, Integer anzahltage) {
        if (heute == null) {
            throw new IllegalArgumentException("Es wurde kein aktuelles Datum weitergegeben.");
        }
        try {
            List<SchlafDaten> woche = List.of(new SchlafDaten());

            int i = 0;
            do { 
                LocalDate datum = heute.minusDays(i);
                SchlafDaten tag = schlafService.getSchlaf(datum, benutzerId);
                woche.add(tag);
                i--; 
            } while (i < anzahltage); 

            return woche;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Daten abgerufen werden.");
        }
        
    }
    
}
