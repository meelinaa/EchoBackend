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
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.echo.echo.controller.mentalerController.GemütController;
import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.service.mentalerService.GemütService;
import com.echo.echo.service.persönlicherService.BenutzerService;

public class GemütControllerTest {
    
    private GemütController gemütController;
    private GemütService gemütService;
    private BenutzerService benutzerService;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp(){
        gemütService = mock(GemütService.class);
        benutzerService = mock(BenutzerService.class);
        gemütController = new GemütController(gemütService, benutzerService);
    }

    @Nested
    @DisplayName("Tests für die Methode getGemüt()")
    public class GetGemütTest {

        @Test
        void getSchlafMitKorrekterAusgabe(){
            GemütDaten daten = new GemütDaten();
            daten.setDatum(datum);
            daten.setBeschreibung(List.of("Motiviert", "Produktiv"));
            daten.setGemütszustand(7);
            daten.setGrund(List.of("Erfolgreiches Coding-Projekt", "Guter Schlaf"));

            when(gemütService.getGemüt(datum, benutzerId)).thenReturn(daten);

            ResponseEntity<GemütDaten> response = gemütController.getGemüt(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(gemütService).getGemüt(datum, benutzerId);            
        }    
        
        @Test
        void getSchlafMitErrorAusgabe(){
            when(gemütService.getGemüt(datum, benutzerId)).thenThrow(new RuntimeException());

            ResponseEntity<GemütDaten> response = gemütController.getGemüt(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }  
        
        @Test
        void getSchlafTestMitDatumGleichNull(){
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gemütController.getGemüt(datum);
            });
            
            assertEquals("Datum darf nicht null sein", exception.getMessage());
            verify(gemütService, never()).getGemüt(datum, benutzerId);
        }

    }

    @Nested
    @DisplayName("Tests für die Methode putGemüt()")
    public class PutGemütTest {
    
        @Test
        void putSchlafMitKorrekterAusgabe(){
            GemütDaten daten = new GemütDaten();
            daten.setDatum(datum);
            daten.setBeschreibung(List.of("Motiviert", "Produktiv"));
            daten.setGemütszustand(7);
            daten.setGrund(List.of("Erfolgreiches Coding-Projekt", "Guter Schlaf"));

            ResponseEntity<Void> response = gemütController.putGemüt(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void putSchlafMitErrorAusgabe(){
            GemütDaten daten = new GemütDaten();

            doThrow(new RuntimeException())
                .when(gemütService)
                .putGemüt(daten, benutzerId);
          
            ResponseEntity<Void> response = gemütController.putGemüt(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void putSchlafTestMitDatenGleichNull(){
            GemütDaten daten = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gemütController.putGemüt(daten);
            });
            
            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
            verify(gemütService, never()).putGemüt(daten, benutzerId);
        }
        
    }
}
