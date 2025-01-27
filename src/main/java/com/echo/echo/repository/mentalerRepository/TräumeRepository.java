package com.echo.echo.repository.mentalerRepository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.mentaleDaten.TräumeDaten;

@Repository
public interface TräumeRepository extends JpaRepository<TräumeDaten, Integer>{

    @Query("SELECT daten FROM TräumeDaten daten WHERE daten.benutzer.id = :benutzerId AND daten.datum = :datum")
    TräumeDaten getByDatumUndBenutzer(@Param("datum") LocalDate datum, @Param("benutzerId") Integer benutzerId);

    
}