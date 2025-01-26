package com.echo.echo.model.körperlicheDaten;

import java.time.LocalDateTime;

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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TrinkenDaten {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public LocalDateTime datum;
    public Double liter;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "benutzer_id") 
    private Benutzer benutzer;
}
