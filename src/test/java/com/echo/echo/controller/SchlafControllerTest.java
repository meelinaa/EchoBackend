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

import com.echo.echo.controller.körperlicherController.SchlafController;
import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.service.analyse.AnalyseSchlafService;
import com.echo.echo.service.körperlicherService.SchlafService;

public class SchlafControllerTest {
    private SchlafController schlafController;
    private SchlafService schlafService;
    private AnalyseSchlafService analyseSchlafService;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp(){
        schlafService = mock(SchlafService.class);
        analyseSchlafService = mock(AnalyseSchlafService.class);
        schlafController = new SchlafController(schlafService, analyseSchlafService);
    }

    @Nested
    @DisplayName("Tests für die Methode getSchlaf()")
    public class GetSchlafTest {

        @Test
        void getSchlafMitKorrekterAusgabe(){
            SchlafDaten daten = new SchlafDaten();
            daten.setDatum(LocalDate.parse("2025-02-01"));
            daten.setSchlafenszeit(LocalTime.parse("23:15:00"));

            when(schlafService.getSchlaf(datum, benutzerId)).thenReturn(daten);

            ResponseEntity<SchlafDaten> response = schlafController.getSchlaf(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());

            verify(schlafService).getSchlaf(datum, benutzerId);            
        }    
        
        @Test
        void getSchlafMitErrorAusgabe(){
            when(schlafService.getSchlaf(datum, benutzerId)).thenThrow(new RuntimeException());

            ResponseEntity<SchlafDaten> response = schlafController.getSchlaf(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }  
        
        @Test
        void getSchlafTestMitDatumGleichNull(){
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schlafController.getSchlaf(datum);
            });
            
            assertEquals("Datum darf nicht null sein", exception.getMessage());
            
            verify(schlafService, never()).getSchlaf(datum, benutzerId);
        }

    }

    @Nested
    @DisplayName("Tests für die Methode putSchlaf()")
    public class PutSchlafTest {
    
        @Test
        void putSchlafMitKorrekterAusgabe(){
            SchlafDaten daten = new SchlafDaten();
            daten.setDatum(LocalDate.parse("2025-02-01"));
            daten.setSchlafenszeit(LocalTime.parse("23:15:00"));

            ResponseEntity<Void> response = schlafController.putSchlaf(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void putSchlafMitErrorAusgabe(){
            SchlafDaten daten = new SchlafDaten();

            doThrow(new RuntimeException())
                .when(schlafService)
                .putSchlaf(daten, benutzerId);
          
            ResponseEntity<Void> response = schlafController.putSchlaf(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void putSchlafTestMitDatenGleichNull(){
            SchlafDaten daten = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schlafController.putSchlaf(daten);
            });
            
            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
            
            verify(schlafService, never()).putSchlaf(daten, benutzerId);
        }
        
    }
}
