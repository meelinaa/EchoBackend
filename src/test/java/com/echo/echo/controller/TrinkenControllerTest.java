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

import com.echo.echo.controller.körperlicherController.TrinkenController;
import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.service.körperlicherService.TrinkenService;

public class TrinkenControllerTest {

    private TrinkenController trinkenController;
    private TrinkenService trinkenService;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp(){
        trinkenService = mock(TrinkenService.class);
        trinkenController = new TrinkenController(trinkenService);
    }
    
    @Nested
    @DisplayName("Tests für die Methode getTrinken()")
    public class getTrinkenTest {

        @Test
        void getTrinkenMitKorrekterAusgabe(){
            TrinkenDaten daten = new TrinkenDaten();
            daten.setDatum(datum);
            daten.setLiter(2.5);

            when(trinkenService.getTrinken(datum, benutzerId)).thenReturn(daten);

            ResponseEntity<TrinkenDaten> response = trinkenController.getTrinken(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(trinkenService).getTrinken(datum, benutzerId);            
        }    
        
        @Test
        void getSchlafMitErrorAusgabe(){
            when(trinkenService.getTrinken(datum, benutzerId)).thenThrow(new RuntimeException());

            ResponseEntity<TrinkenDaten> response = trinkenController.getTrinken(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }  
        
        @Test
        void getSchlafTestMitDatumGleichNull(){
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                trinkenController.getTrinken(datum);
            });
            
            assertEquals("Datum darf nicht null sein", exception.getMessage());
            verify(trinkenService, never()).getTrinken(datum, benutzerId);
        }
    }

    @Nested
    @DisplayName("Tests für die Methode putTrinken()")
    public class PutTrinkenTest {
    
        @Test
        void putSchlafMitKorrekterAusgabe(){
            TrinkenDaten daten = new TrinkenDaten();
            daten.setDatum(datum);
            daten.setLiter(2.5);

            ResponseEntity<Void> response = trinkenController.putTrinken(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void putSchlafMitErrorAusgabe(){
            TrinkenDaten daten = new TrinkenDaten();

            doThrow(new RuntimeException())
                .when(trinkenService)
                .putTrinken(daten, benutzerId);
          
            ResponseEntity<Void> response = trinkenController.putTrinken(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void putSchlafTestMitDatenGleichNull(){
            TrinkenDaten daten = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                trinkenController.putTrinken(daten);
            });
            
            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
            verify(trinkenService, never()).putTrinken(daten, benutzerId);
        }
    }
    
}
