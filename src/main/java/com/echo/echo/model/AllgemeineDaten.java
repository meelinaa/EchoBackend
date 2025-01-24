package com.echo.echo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AllgemeineDaten {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public Double größe;
    public Double gewicht;
    public List<String> krankheiten;
    public Integer alter;
    public String geschlecht;
    public Double bmi;

    @ManyToOne()
    public Benutzer benutzer;
}
