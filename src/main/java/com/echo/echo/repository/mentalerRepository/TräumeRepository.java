package com.echo.echo.repository.mentalerRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.mentaleDaten.TräumeDaten;

@Repository
public interface TräumeRepository extends JpaRepository<TräumeDaten, Integer>{
    
}