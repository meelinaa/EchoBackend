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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.echo.echo.controller.persönlicherController.AllgemeinController;
import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.service.persönlicherService.AllgemeinerService;

public class AllgemeinControllerTest {

    private AllgemeinController allgemeinController;
    private AllgemeinerService allgemeinerService;
    private Integer benutzerId = 1;

    @BeforeEach
    void setUp(){
        allgemeinerService = mock(AllgemeinerService.class);
        allgemeinController = new AllgemeinController(allgemeinerService);
    }
    
    @Nested
    @DisplayName("Tests für die Methode getAllgemeineDaten()")
    public class GetAllgemeineDatenTest {

        @Test
        void getAllgemeineDatenMitKorrekterAusgabe(){
            AllgemeineDaten daten = new AllgemeineDaten();
            daten.setAlter(18);
            daten.setBmi(27.);
            daten.setGeschlecht("weiblich");
            daten.setGewicht(65.5);
            daten.setGröße(175.6);

            when(allgemeinerService.getAllgemeinenDaten(benutzerId)).thenReturn(daten);

            ResponseEntity<AllgemeineDaten> response = allgemeinController.getAllgemeineDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(allgemeinerService).getAllgemeinenDaten(benutzerId); 
        }

        @Test
        void getAllgemeineDatenMitFehlerausgabe(){
            when(allgemeinerService.getAllgemeinenDaten(benutzerId)).thenThrow(new RuntimeException());

            ResponseEntity<AllgemeineDaten> response = allgemeinController.getAllgemeineDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode putAlleAllgemeinenDaten()")
    public class PutAlleAllgemeinenDatenTest {

        @Test
        void putAlleAllgemeinenDatenMitKorrekterAusgabe(){
            AllgemeineDaten daten = new AllgemeineDaten();
            daten.setAlter(18);
            daten.setBmi(27.);
            daten.setGeschlecht("weiblich");
            daten.setGewicht(65.5);
            daten.setGröße(175.6);

            ResponseEntity<Void> response = allgemeinController.putAlleAllgemeinenDaten(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void putAlleAllgemeinenDatenErrorAusgabeWennDatenGleichNull(){
            AllgemeineDaten daten = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                allgemeinController.putAlleAllgemeinenDaten(daten);
            });

            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
            
            verify(allgemeinerService, never()).putAlleAllgemeinenDaten(benutzerId, daten);
        }

        @Test
        void putAlleAllgemeinenDatenErrorAusgabe(){
            AllgemeineDaten daten = new AllgemeineDaten();

            doThrow(new RuntimeException())
                .when(allgemeinerService)
                .putAlleAllgemeinenDaten(benutzerId, daten);

            ResponseEntity<Void> response = allgemeinController.putAlleAllgemeinenDaten(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    }
}
