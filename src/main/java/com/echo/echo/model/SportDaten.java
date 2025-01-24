package com.echo.echo.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    public Benutzer benutzer;
}
