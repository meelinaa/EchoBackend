package com.echo.echo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.AllgemeineDaten;

@Repository
public interface AllgemeineDatenRepository extends JpaRepository<AllgemeineDaten, Integer>{
    
}
