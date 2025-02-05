package com.echo.echo.model.persönlicheDaten;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AllgemeineDaten {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String name;
    public Double größe;
    public Double gewicht;
    public Integer alter;
    public String geschlecht;
    public Double bmi;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "benutzer_id") 
    private Benutzer benutzer;
}
