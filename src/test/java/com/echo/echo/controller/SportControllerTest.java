package com.echo.echo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.echo.echo.controller.körperlicherController.SportController;
import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.service.analyse.AnalyseSportService;
import com.echo.echo.service.körperlicherService.SportService;

public class SportControllerTest {

    private SportController sportController;
    private SportService sportService;
    private AnalyseSportService analyseSportService;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp(){
        sportService = mock(SportService.class);
        analyseSportService = mock(AnalyseSportService.class);
        sportController = new SportController(sportService, analyseSportService);
    }

    @Nested
    @DisplayName("Tests für die Methode getSport()")
    public class GetSportTest {
    
        @Test
        void getSportMitKorrekterAusgabe(){
            SportDaten daten = new SportDaten();
            daten.setSportart("laufen");
            daten.setTrauningsDauer(LocalTime.parse("01:30:00"));
            daten.setDatum(datum);
            
            when(sportService.getSport(datum, benutzerId)).thenReturn(daten);

            ResponseEntity<SportDaten> response = sportController.getSport(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());

            verify(sportService).getSport(datum, benutzerId); 
        }

         @Test
        void getSportMitErrorAusgabe(){
            when(sportService.getSport(datum, benutzerId)).thenThrow(new RuntimeException());

            ResponseEntity<SportDaten> response = sportController.getSport(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }  
        
        @Test
        void getSportTestMitDatumGleichNull(){
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                sportController.getSport(datum);
            });
            
            assertEquals("Datum darf nicht null sein", exception.getMessage());
            verify(sportService, never()).getSport(datum, benutzerId);
        }
    }

    
    @Nested
    @DisplayName("Tests für die Methode putSport()")
    public class putSportTest {
    
        @Test
        void putSchlafMitKorrekterAusgabe(){
            SportDaten daten = new SportDaten();
            daten.setSportart("laufen");
            daten.setTrauningsDauer(LocalTime.parse("01:30:00"));
            daten.setDatum(datum);

            ResponseEntity<Void> response = sportController.putSport(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void putSchlafMitErrorAusgabe(){
            SportDaten daten = new SportDaten();

            doThrow(new RuntimeException())
                .when(sportService)
                .putSport(daten, benutzerId);
          
            ResponseEntity<Void> response = sportController.putSport(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void putSchlafTestMitDatenGleichNull(){
            SportDaten daten = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                sportController.putSport(daten);
            });
            
            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
            verify(sportService, never()).putSport(daten, benutzerId);
        }
    }
    
}
