package com.echo.echo.model.persönlicheDaten;

import java.time.LocalDate;
import java.util.List;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.model.mentaleDaten.TräumeDaten;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Benutzer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String name;

    @OneToOne(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    private AllgemeineDaten allgemein; 

    @OneToMany(mappedBy = "träumeDaten", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TräumeDaten> träume; 

    @OneToMany(mappedBy = "schlafDaten", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchlafDaten> schlaf; 

    @OneToMany(mappedBy = "schritteDaten", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchritteDaten> schritte; 

    @OneToMany(mappedBy = "gedankenDaten", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GedankenDaten> gedanken; 

    @OneToMany(mappedBy = "sportDaten", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SportDaten> sport; 

    @OneToMany(mappedBy = "trinkenDaten", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrinkenDaten> trinken; 

    @OneToMany(mappedBy = "gemütDaten", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GemütDaten> gemüt;



    public TräumeDaten getTräume(LocalDate datum) {
        for (TräumeDaten traum : träume) { 
            if (traum.getDatum().equals(datum)) {
                return traum; 
            }
        }
        return null; 
    }

    public GemütDaten getGemüt(LocalDate datum) {
        for (GemütDaten gemüt : gemüt) { 
            if (gemüt.getDatum().equals(datum)) {
                return gemüt; 
            }
        }
        return null; 
    }
    

}
