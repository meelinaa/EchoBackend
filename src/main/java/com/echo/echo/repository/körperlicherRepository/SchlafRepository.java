package com.echo.echo.repository.körperlicherRepository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;

@Repository
public interface SchlafRepository extends JpaRepository<SchlafDaten, Integer>{

    @Query("SELECT daten FROM SchlafDaten daten WHERE daten.benutzer.id = :benutzerId AND daten.datum = :datum")
    SchlafDaten getByDatumUndBenutzer(@Param("datum") LocalDate datum, @Param("benutzerId") Integer benutzerId);

    
}