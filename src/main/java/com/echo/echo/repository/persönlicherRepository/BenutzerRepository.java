package com.echo.echo.repository.persönlicherRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;

@Repository
public interface BenutzerRepository extends JpaRepository<Benutzer, Integer>{



}