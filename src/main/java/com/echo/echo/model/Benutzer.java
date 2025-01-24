package com.echo.echo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
}
