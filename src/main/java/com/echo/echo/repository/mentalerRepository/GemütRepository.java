package com.echo.echo.repository.mentalerRepository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.mentaleDaten.GemütDaten;

@Repository
public interface GemütRepository extends JpaRepository<GemütDaten, Integer>{

    @Query("SELECT daten FROM GemütDaten daten WHERE daten.benutzer.id = :benutzerId AND daten.datum = :datum")
    GemütDaten getByDatumUndBenutzer(@Param("datum") LocalDate datum, @Param("benutzerId") Integer benutzerId);

    
}