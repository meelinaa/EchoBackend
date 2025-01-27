package com.echo.echo.repository.körperlicherRepository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.körperlicheDaten.SportDaten;

@Repository
public interface SportRepository extends JpaRepository<SportDaten, Integer>{

    @Query("SELECT daten FROM SportDaten daten WHERE daten.benutzer.id = :benutzerId AND daten.datum = :datum")
    SportDaten getByDatumUndBenutzer(@Param("datum") LocalDate datum, @Param("benutzerId") Integer benutzerId);

    
}