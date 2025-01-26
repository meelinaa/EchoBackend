package com.echo.echo.repository.mentalerRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.mentaleDaten.GemütDaten;

@Repository
public interface GemütRepository extends JpaRepository<GemütDaten, Integer>{
    
}