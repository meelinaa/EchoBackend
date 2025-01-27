package com.echo.echo.repository.mentalerRepository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.mentaleDaten.GedankenDaten;

@Repository
public interface GedankenRepository extends JpaRepository<GedankenDaten, Integer>{

    @Query("SELECT daten FROM GedankenDaten daten WHERE daten.benutzer.id = :benutzerId AND daten.datum = :datum")
    GedankenDaten getByDatumUndBenutzer(@Param("datum") LocalDate datum, @Param("benutzerId") Integer benutzerId);

    
}