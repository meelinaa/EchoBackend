package com.echo.echo.repository.körperlicherRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.körperlicheDaten.SchritteDaten;

@Repository
public interface SchritteRepository extends JpaRepository<SchritteDaten, Integer>{
    
}