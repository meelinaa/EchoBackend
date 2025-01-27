package com.echo.echo.repository.körperlicherRepository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.körperlicheDaten.SchritteDaten;

@Repository
public interface SchritteRepository extends JpaRepository<SchritteDaten, Integer>{

    @Query("SELECT daten FROM SchritteDaten daten WHERE daten.benutzer.id = :benutzerId AND daten.datum = :datum")
    SchritteDaten getByDatumUndBenutzer(@Param("datum") LocalDate datum, @Param("benutzerId") Integer benutzerId);

    
}