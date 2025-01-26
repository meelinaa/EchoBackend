package com.echo.echo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.Benutzer;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Integer>{
    
}