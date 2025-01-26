package com.echo.echo.repository.persönlicherRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.persönlicheDaten.Benutzer;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Integer>{
    
}