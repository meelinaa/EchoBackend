package com.echo.echo.repository.körperlicherRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.körperlicheDaten.SportDaten;

@Repository
public interface SportRepository extends JpaRepository<SportDaten, Integer>{
    
}