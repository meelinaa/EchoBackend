package com.echo.echo.model.persönlicheDaten;

import java.util.List;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @OneToOne(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private AllgemeineDaten allgemein; 

    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TräumeDaten> träume; 

    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SchlafDaten> schlaf; 

    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SchritteDaten> schritte; 

    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GedankenDaten> gedanken; 

    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SportDaten> sport; 

    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TrinkenDaten> trinken; 

    @OneToMany(mappedBy = "benutzer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GemütDaten> gemüt;    

}
