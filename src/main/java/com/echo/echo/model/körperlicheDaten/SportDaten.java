package com.echo.echo.model.körperlicheDaten;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportDaten {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public LocalDateTime tag;
    public LocalTime trauningsDauer;
    public String sportart;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "benutzer_id") 
    private Benutzer benutzer;
}
