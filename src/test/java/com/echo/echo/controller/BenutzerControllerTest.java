package com.echo.echo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.echo.echo.controller.persönlicherController.BenutzerController;
import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.service.persönlicherService.BenutzerService;

public class BenutzerControllerTest {
    private BenutzerController benutzerController;
    private BenutzerService benutzerService;

    private LocalDate datum = LocalDate.parse("2025-02-01");


    @BeforeEach
    void setUp(){
        benutzerService = mock(BenutzerService.class);
        benutzerController = new BenutzerController(benutzerService);
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleAllgemeinenDaten()")
    public class getAlleAllgemeinenDaten {

        @Test
        void getAlleAllgemeinenDatenMitKorrekterAusgabe(){
            AllgemeineDaten daten = new AllgemeineDaten();
            daten.setAlter(18);
            daten.setBmi(27.);
            daten.setGeschlecht("weiblich");
            daten.setGewicht(65.5);
            daten.setGröße(175.6);

            when(benutzerService.getAlleAllgemeinenDaten()).thenReturn(daten);

            ResponseEntity<AllgemeineDaten> response = benutzerController.getAlleAllgemeinenDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(benutzerService).getAlleAllgemeinenDaten(); 
        }

        @Test
        void getAlleAllgemeinenDatenMitErrorAusgabe(){
            when(benutzerService.getAlleAllgemeinenDaten()).thenThrow(new RuntimeException());

            ResponseEntity<AllgemeineDaten> response = benutzerController.getAlleAllgemeinenDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    }


    @Nested
    @DisplayName("Tests für die Methode getAlleTräumeDaten()")
    class GetAlleTräumeDatenTests {
        @Test
        void getAlleTräumeDatenMitKorrekterAusgabe() {
            List<TräumeDaten> daten = new ArrayList<>();
            TräumeDaten traum = new TräumeDaten();
            traum.setDatum(datum);
            traum.setBewertung(7);
            traum.setTraum("Traum");
            daten.add(traum);

            when(benutzerService.getAlleTräumeDaten()).thenReturn(daten);

            ResponseEntity<List<TräumeDaten>> response = benutzerController.getAlleTräumeDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(benutzerService).getAlleTräumeDaten();
        }

        @Test
        void getAlleTräumeDatenMitErrorAusgabe() {
            when(benutzerService.getAlleTräumeDaten()).thenThrow(new RuntimeException());

            ResponseEntity<List<TräumeDaten>> response = benutzerController.getAlleTräumeDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleSchlafDaten()")
    class GetAlleSchlafDatenTests {
        @Test
        void getAlleSchlafDatenMitKorrekterAusgabe() {
            List<SchlafDaten> daten = new ArrayList<>();
            SchlafDaten schlaf = new SchlafDaten();
            schlaf.setDatum(datum);
            schlaf.setSchlafBewertung(3);
            schlaf.setSchlafenszeit(LocalTime.parse("01:20:00"));
            daten.add(schlaf);

            when(benutzerService.getAlleSchlafDaten()).thenReturn(daten);

            ResponseEntity<List<SchlafDaten>> response = benutzerController.getAlleSchlafDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(benutzerService).getAlleSchlafDaten();
        }

        @Test
        void getAlleSchlafDatenMitErrorAusgabe() {
            when(benutzerService.getAlleSchlafDaten()).thenThrow(new RuntimeException());

            ResponseEntity<List<SchlafDaten>> response = benutzerController.getAlleSchlafDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleSchritteDaten()")
    class GetAlleSchritteDatenTests {
        @Test
        void getAlleSchritteDatenMitKorrekterAusgabe() {
            List<SchritteDaten> daten = new ArrayList<>();
            SchritteDaten schritte = new SchritteDaten();
            schritte.setDatum(datum);
            schritte.setMeter(4.);
            schritte.setSchritte(654);
            daten.add(schritte);

            when(benutzerService.getAlleSchritteDaten()).thenReturn(daten);

            ResponseEntity<List<SchritteDaten>> response = benutzerController.getAlleSchritteDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(benutzerService).getAlleSchritteDaten();
        }

        @Test
        void getAlleSchritteDatenMitErrorAusgabe() {
            when(benutzerService.getAlleSchritteDaten()).thenThrow(new RuntimeException());

            ResponseEntity<List<SchritteDaten>> response = benutzerController.getAlleSchritteDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleGedankenDaten()")
    class GetAlleGedankenDatenTests {
        @Test
        void getAlleGedankenDatenMitKorrekterAusgabe() {
            List<GedankenDaten> daten = new ArrayList<>();
            GedankenDaten gedanken = new GedankenDaten();
            gedanken.setGedanken("Gute Gedanken");
            gedanken.setDatum(datum);
            daten.add(gedanken);

            when(benutzerService.getAlleGedankenDaten()).thenReturn(daten);

            ResponseEntity<List<GedankenDaten>> response = benutzerController.getAlleGedankenDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(benutzerService).getAlleGedankenDaten();
        }

        @Test
        void getAlleGedankenDatenMitErrorAusgabe() {
            when(benutzerService.getAlleGedankenDaten()).thenThrow(new RuntimeException());

            ResponseEntity<List<GedankenDaten>> response = benutzerController.getAlleGedankenDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleSportDaten()")
    class GetAlleSportDatenTests {
        @Test
        void getAlleSportDatenMitKorrekterAusgabe() {
            List<SportDaten> daten = new ArrayList<>();
            daten.add(new SportDaten(1, null, null, "Joggen", null));

            when(benutzerService.getAlleSportDaten()).thenReturn(daten);

            ResponseEntity<List<SportDaten>> response = benutzerController.getAlleSportDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(benutzerService).getAlleSportDaten();
        }

        @Test
        void getAlleSportDatenMitErrorAusgabe() {
            when(benutzerService.getAlleSportDaten()).thenThrow(new RuntimeException());

            ResponseEntity<List<SportDaten>> response = benutzerController.getAlleSportDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleTrinkenDaten()")
    class GetAlleTrinkenDatenTests {
        @Test
        void getAlleTrinkenDatenMitKorrekterAusgabe() {
            List<TrinkenDaten> daten = new ArrayList<>();
            daten.add(new TrinkenDaten(1, null, 2.0, null));

            when(benutzerService.getAlleTrinkenDaten()).thenReturn(daten);

            ResponseEntity<List<TrinkenDaten>> response = benutzerController.getAlleTrinkenDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(benutzerService).getAlleTrinkenDaten();
        }

        @Test
        void getAlleTrinkenDatenMitErrorAusgabe() {
            when(benutzerService.getAlleTrinkenDaten()).thenThrow(new RuntimeException());

            ResponseEntity<List<TrinkenDaten>> response = benutzerController.getAlleTrinkenDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleGemütDaten()")
    public class GetAlleGemütDatenTests {

        @Test
        void getAlleGemütDatenMitKorrekterAusgabe() {
            List<GemütDaten> daten = new ArrayList<>();
            GemütDaten gemüt = new GemütDaten();
            gemüt.setDatum(datum);
            gemüt.setBeschreibung(List.of("Motiviert", "Produktiv"));
            gemüt.setGemütszustand(7);
            gemüt.setGrund(List.of("Erfolgreiches Coding-Projekt", "Guter Schlaf"));

            daten.add(gemüt);

            when(benutzerService.getAlleGemütDaten()).thenReturn(daten);

            ResponseEntity<List<GemütDaten>> response = benutzerController.getAlleGemütDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
            verify(benutzerService).getAlleGemütDaten();
        }

        @Test
        void getAlleGemütDatenMitErrorAusgabe() {
            when(benutzerService.getAlleGemütDaten()).thenThrow(new RuntimeException());

            ResponseEntity<List<GemütDaten>> response = benutzerController.getAlleGemütDaten();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    }
}
