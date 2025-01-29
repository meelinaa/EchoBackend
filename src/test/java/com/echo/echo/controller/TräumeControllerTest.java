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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.echo.echo.controller.mentalerController.TräumeController;
import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.service.mentalerService.TräumeService;

public class TräumeControllerTest {

    private TräumeController träumeController;
    private TräumeService träumeService;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp(){
        träumeService = mock(TräumeService.class);
        träumeController = new TräumeController(träumeService);
    }

    @Nested
    @DisplayName("Tests für die Methode getTraum()")
    public class GetTraumTest {

        @Test
        void getSchlafMitKorrekterAusgabe(){
            TräumeDaten daten = new TräumeDaten();
            daten.setDatum(datum);
            daten.setBewertung(7);
            daten.setTraum("Ich bin durch eine futuristische Stadt mit fliegenden Autos gelaufen.");

            when(träumeService.getTraum(datum, benutzerId)).thenReturn(daten);

            ResponseEntity<TräumeDaten> response = träumeController.getTraum(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());

            verify(träumeService).getTraum(datum, benutzerId);            
        }    
        
        @Test
        void getSchlafMitErrorAusgabe(){
            when(träumeService.getTraum(datum, benutzerId)).thenThrow(new RuntimeException());

            ResponseEntity<TräumeDaten> response = träumeController.getTraum(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }  
        
        @Test
        void getSchlafTestMitDatumGleichNull(){
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                träumeController.getTraum(datum);
            });
            
            assertEquals("Datum darf nicht null sein", exception.getMessage());
            
            verify(träumeService, never()).getTraum(datum, benutzerId);
        }

    }

    @Nested
    @DisplayName("Tests für die Methode putTraum()")
    public class PutTraumTest {
    
        @Test
        void putSchlafMitKorrekterAusgabe(){
            TräumeDaten daten = new TräumeDaten();
            daten.setDatum(datum);
            daten.setBewertung(7);
            daten.setTraum("Ich bin durch eine futuristische Stadt mit fliegenden Autos gelaufen.");

            ResponseEntity<Void> response = träumeController.putTraum(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void putSchlafMitErrorAusgabe(){
            TräumeDaten daten = new TräumeDaten();

            doThrow(new RuntimeException())
                .when(träumeService)
                .putTraum(daten, benutzerId);
          
            ResponseEntity<Void> response = träumeController.putTraum(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void putSchlafTestMitDatenGleichNull(){
            TräumeDaten daten = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                träumeController.putTraum(daten);
            });
            
            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
            
            verify(träumeService, never()).putTraum(daten, benutzerId);
        }
        
    }
    
}
