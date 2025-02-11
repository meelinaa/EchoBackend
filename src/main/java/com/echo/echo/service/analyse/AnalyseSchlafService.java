package com.echo.echo.service.analyse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.service.körperlicherService.SchlafService;

@Service
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
            List<SchlafDaten> woche = new ArrayList<>();

            for (int i = 0; i < anzahltage; i++) {
                LocalDate datum = heute.minusDays(i);
                SchlafDaten tag = schlafService.getSchlaf(datum, benutzerId);
                woche.add(tag);
            }

            return woche;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Daten abgerufen werden.");
        }
    }

    public LocalTime getDurchschnittZeit(LocalDate heute, Integer anzahltage) {
        List<SchlafDaten> woche = getTageAnalyse(heute, anzahltage);

        int summe = 0;
        for (SchlafDaten tag : woche) {
            summe += tag.getSchlafenszeit().toSecondOfDay();
        }

        LocalTime durchschnitt = LocalTime.ofSecondOfDay(summe / anzahltage);

        return durchschnitt ;
    }

    public SchlafDaten getLängsterSchlaf(LocalDate heute, Integer anzahltage) {
        List<SchlafDaten> woche = getTageAnalyse(heute, anzahltage);

        SchlafDaten längsterSchlaf = null;
        LocalTime time = LocalTime.MIN;

        for (SchlafDaten tag : woche) {
            if (tag.getSchlafenszeit().isAfter(time)) {
                längsterSchlaf = tag;
                time = tag.getSchlafenszeit();
            }
        }

        return längsterSchlaf;
    }

    public SchlafDaten getKürzesterSchlaf(LocalDate heute, Integer anzahltage) {
        List<SchlafDaten> woche = getTageAnalyse(heute, anzahltage);

        SchlafDaten kürzesterSchlaf = null;
        LocalTime time = LocalTime.MAX;

        for (SchlafDaten tag : woche) {
            if (tag.getSchlafenszeit().isBefore(time)) {
                kürzesterSchlaf = tag;
                time = tag.getSchlafenszeit();
            }
        }

        return kürzesterSchlaf;
    }
    
}
