package com.echo.echo.service.analyse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.service.körperlicherService.SchritteService;

@Service
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
            List<SchritteDaten> woche = new ArrayList<>();

            for (int i = 0; i < anzahltage; i++) {
                LocalDate datum = heute.minusDays(i);
                SchritteDaten tag = schritteService.getSchritte(datum, benutzerId);
                woche.add(tag);
            }

            return woche;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Daten abgerufen werden.");
        }
    }

    public Integer getDurchschnittSchritte(LocalDate heute, Integer anzahltage) {
        List<SchritteDaten> woche = getTageAnalyse(heute, anzahltage);

        int summe = 0;
        for (SchritteDaten tag : woche) {
            summe += tag.getSchritte();
        }

        return summe;
    }

    public Double getDurchschnittMeter(LocalDate heute, Integer anzahltage) {
        List<SchritteDaten> woche = getTageAnalyse(heute, anzahltage);

        Double summe = 0.;
        for (SchritteDaten tag : woche) {
            summe += tag.getMeter();
        }

        return summe;
    }

    public SchritteDaten getLängsteSchritte(LocalDate heute, Integer anzahltage) {
        List<SchritteDaten> woche = getTageAnalyse(heute, anzahltage);

        SchritteDaten längsteSchritte = null;
        Integer schritteRef = 0;

        for (SchritteDaten tag : woche) {
            if (tag.getSchritte() > schritteRef) {
                längsteSchritte = tag;
                schritteRef = tag.getSchritte();
            }
        }

        return längsteSchritte;
    }

    public SchritteDaten getKürzereSchritte(LocalDate heute, Integer anzahltage) {
        List<SchritteDaten> woche = getTageAnalyse(heute, anzahltage);

        SchritteDaten kürzereSchritte = null;
        Integer schritteRef = 999999999;

        for (SchritteDaten tag : woche) {
            if (tag.getSchritte() < schritteRef) {
                kürzereSchritte = tag;
                schritteRef = tag.getSchritte();
            }
        }

        return kürzereSchritte;
    }
    

    
    public SchritteDaten getLängsteMeter(LocalDate heute, Integer anzahltage) {
        List<SchritteDaten> woche = getTageAnalyse(heute, anzahltage);

        SchritteDaten längsteSchritte = null;
        Double schritteRef = 0.;

        for (SchritteDaten tag : woche) {
            if (tag.getSchritte() > schritteRef) {
                längsteSchritte = tag;
                schritteRef = tag.getMeter();
            }
        }

        return längsteSchritte;
    }

    public SchritteDaten getKürzereMeter(LocalDate heute, Integer anzahltage) {
        List<SchritteDaten> woche = getTageAnalyse(heute, anzahltage);

        SchritteDaten kürzereMeter = null;
        Double schritteRef = 9999999999.9;

        for (SchritteDaten tag : woche) {
            if (tag.getSchritte() < schritteRef) {
                kürzereMeter = tag;
                schritteRef = tag.getMeter();
            }
        }

        return kürzereMeter;
    }
}
