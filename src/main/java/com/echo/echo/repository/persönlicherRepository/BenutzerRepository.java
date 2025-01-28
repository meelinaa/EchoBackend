package com.echo.echo.repository.persönlicherRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.persönlicheDaten.Benutzer;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Integer>{

    @Query("SELECT b FROM Benutzer b " +
           "LEFT JOIN FETCH b.träume " +
           "LEFT JOIN FETCH b.schlaf " +
           "LEFT JOIN FETCH b.schritte " +
           "LEFT JOIN FETCH b.gedanken " +
           "LEFT JOIN FETCH b.sport " +
           "LEFT JOIN FETCH b.trinken " +
           "LEFT JOIN FETCH b.gemüt " +
           "LEFT JOIN FETCH b.allgemein " +
           "WHERE b.id = :id")
    Benutzer findByIdWithAllData(@Param("id") Integer id);

}