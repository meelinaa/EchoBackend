package com.echo.echo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "allgemeineDaten", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AllgemeineDaten> allgemein; 

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
    private List<GemütDaten> genmüt; 

}
