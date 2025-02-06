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

import com.echo.echo.controller.mentalerController.GedankenController;
import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.service.mentalerService.GedankenService;
import com.echo.echo.service.persönlicherService.BenutzerService;

public class GedankenControllerTest {
    
    private GedankenController gedankenController;
    private GedankenService gedankenService;
    private BenutzerService benutzerService;
    
    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");
    private String datumReactFormat = "2025-02-01";

    @BeforeEach
    void setUp(){
        gedankenService = mock(GedankenService.class);
        benutzerService = mock(BenutzerService.class);
        gedankenController = new GedankenController(gedankenService, benutzerService);
    }

    @Nested
    @DisplayName("Tests für die Methode getGedanken()")
    public class GetGedankenTest {

        @Test
        void getSchlafMitKorrekterAusgabe(){
            GedankenDaten daten = new GedankenDaten();
            daten.setDatum(datum);
            daten.setGedanken("Heute war ein produktiver Tag. Viel geschafft!");

            when(gedankenService.getGedanken(datum, benutzerId)).thenReturn(daten);

            ResponseEntity<GedankenDaten> response = gedankenController.getGedanken(datumReactFormat);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());

            verify(gedankenService).getGedanken(datum, benutzerId);            
        }    
        
        @Test
        void getSchlafMitErrorAusgabe(){
            when(gedankenService.getGedanken(datum, benutzerId)).thenThrow(new RuntimeException());

            ResponseEntity<GedankenDaten> response = gedankenController.getGedanken(datumReactFormat);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }  
        
        @Test
        void getSchlafTestMitDatumGleichNull(){
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gedankenController.getGedanken(datumReactFormat);
            });
            
            assertEquals("Datum darf nicht null sein", exception.getMessage());
            
            verify(gedankenService, never()).getGedanken(datum, benutzerId);
        }

    }

    @Nested
    @DisplayName("Tests für die Methode putGedanken()")
    public class PutGedankenTest {
    
        @Test
        void putSchlafMitKorrekterAusgabe(){
            GedankenDaten daten = new GedankenDaten();
            daten.setDatum(datum);
            daten.setGedanken("Heute war ein produktiver Tag. Viel geschafft!");

            ResponseEntity<Void> response = gedankenController.putGedanken(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void putSchlafMitErrorAusgabe(){
            GedankenDaten daten = new GedankenDaten();

            doThrow(new RuntimeException())
                .when(gedankenService)
                .putGedanken(daten, benutzerId);
          
            ResponseEntity<Void> response = gedankenController.putGedanken(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void putSchlafTestMitDatenGleichNull(){
            GedankenDaten daten = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gedankenController.putGedanken(daten);
            });
            
            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
            
            verify(gedankenService, never()).putGedanken(daten, benutzerId);
        }
        
    }
}
