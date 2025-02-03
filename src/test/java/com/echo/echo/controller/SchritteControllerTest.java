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

import com.echo.echo.controller.körperlicherController.SchritteController;
import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.service.analyse.AnalyseSchritteService;
import com.echo.echo.service.körperlicherService.SchritteService;

public class SchritteControllerTest {
    
    SchritteService schritteService;
    SchritteController schritteController;
    AnalyseSchritteService analyseSchritteService;

    private LocalDate datum = LocalDate.parse("2025-02-01");
    private Integer benutzerId = 1;

    @BeforeEach
    void setUp(){
        schritteService = mock(SchritteService.class);
        analyseSchritteService = mock(AnalyseSchritteService.class);
        schritteController = new SchritteController(schritteService, analyseSchritteService);
    }

    @Nested
    @DisplayName("Tests für die Methode getSchritte()")
    public class GetSchritteTest {
        
        @Test
        void getSchritteMitKorrekterAusgabe(){
            SchritteDaten daten = new SchritteDaten();
            daten.setDatum(datum);
            daten.setMeter(6.4);
            daten.setSchritte(6987);

            when(schritteService.getSchritte(datum, benutzerId)).thenReturn(daten);

            ResponseEntity<SchritteDaten> response = schritteController.getSchritte(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());

            verify(schritteService).getSchritte(datum, benutzerId);  
        }

        @Test
        void getSchritteMitErrorAusgabe(){
            when(schritteService.getSchritte(datum, benutzerId)).thenThrow(new RuntimeException());

            ResponseEntity<SchritteDaten> response = schritteController.getSchritte(datum);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void getSchlafTestMitDatumGleichNull(){
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schritteController.getSchritte(datum);
            });
            
            assertEquals("Datum darf nicht null sein", exception.getMessage());
            
            verify(schritteService, never()).getSchritte(datum, benutzerId);
        }
        
    }

    
    @Nested
    @DisplayName("Tests für die Methode putSchritte()")
    public class PutSchlafTest {
    
        @Test
        void putSchritteMitKorrekterAusgabe(){
            SchritteDaten daten = new SchritteDaten();
            daten.setDatum(datum);
            daten.setMeter(6.4);
            daten.setSchritte(6987);

            ResponseEntity<Void> response = schritteController.putSchritte(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void putSchritteMitErrorAusgabe(){
            SchritteDaten daten = new SchritteDaten();

            doThrow(new RuntimeException())
                .when(schritteService)
                .putSchritte(daten, benutzerId);
          
            ResponseEntity<Void> response = schritteController.putSchritte(daten);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void putSchritteTestMitDatenGleichNull(){
            SchritteDaten daten = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schritteController.putSchritte(daten);
            });
            
            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
            
            verify(schritteService, never()).putSchritte(daten, benutzerId);
        }
        
    }

}
