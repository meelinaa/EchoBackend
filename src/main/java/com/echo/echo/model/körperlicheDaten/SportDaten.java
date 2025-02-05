package com.echo.echo.model.körperlicheDaten;

import java.time.LocalDate;
import java.time.LocalTime;

import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public LocalDate datum;
    public LocalTime trainingsDauer;
    public String sportart;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "benutzer_id") 
    private Benutzer benutzer;
}
