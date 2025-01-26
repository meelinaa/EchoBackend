package com.echo.echo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.GedankenDaten;

@Repository
public interface GedankenRepository extends JpaRepository<GedankenDaten, Integer>{
    
}