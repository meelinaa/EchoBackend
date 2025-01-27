package com.echo.echo.repository.persönlicherRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;

@Repository
public interface AllgemeinRepository extends JpaRepository<AllgemeineDaten, Integer>{

    @Query("SELECT daten FROM AllgemeineDaten daten WHERE daten.id = :id")
    AllgemeineDaten findAllById(Integer id);

    @Query("SELECT daten FROM AllgemeineDaten daten WHERE daten.benutzer.id = :benutzerId")
    AllgemeineDaten findByBenutzerId(@Param("benutzerId") Integer benutzerId);

    
}
