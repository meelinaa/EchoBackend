package com.echo.echo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.persönlicherRepository.AllgemeinRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;
import com.echo.echo.service.persönlicherService.AllgemeinerService;

import jakarta.persistence.EntityNotFoundException;

public class AllgemeinerServiceTest {

    private AllgemeinerService allgemeinerService;
    private AllgemeinRepository allgemeinRepository;
    private BenutzerRepository benutzerRepository;

    private Integer benutzerId = 1;

    @BeforeEach
    void setUp() {
        allgemeinRepository = mock(AllgemeinRepository.class);
        benutzerRepository = mock(BenutzerRepository.class);
        allgemeinerService = new AllgemeinerService(allgemeinRepository, benutzerRepository);
    }

    @Nested
    @DisplayName("Tests für die Methode getAllgemeinenDaten()")
    public class GetAllgemeinenDatenTest {

        @Test
        void getAllgemeinenDatenMitKorrekterAusgabe() {
            AllgemeineDaten daten = new AllgemeineDaten();
            daten.setAlter(25);
            daten.setBmi(24.5);
            daten.setGeschlecht("männlich");
            daten.setGewicht(80.0);
            daten.setGröße(180.0);

            when(allgemeinRepository.findAllById(benutzerId)).thenReturn(daten);

            AllgemeineDaten result = allgemeinerService.getAllgemeinenDaten(benutzerId);

            assertEquals(daten, result);
        }

        @Test
        void getAllgemeinenDatenErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                allgemeinerService.getAllgemeinenDaten(benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
            verify(allgemeinRepository, never()).findById(any());
        }

        @Test
        void getAllgemeinenDatenErrorAusgabeWennDatenGleichNull() {
            when(allgemeinRepository.findAllById(benutzerId)).thenReturn(null);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                allgemeinerService.getAllgemeinenDaten(benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(allgemeinRepository).findAllById(benutzerId);
        }

        @Test
        void getAllgemeinenDatenErrorAusgabeBeiAllgemeinemFehler() {
            doThrow(new RuntimeException("Test-Fehler"))
                .when(allgemeinRepository)
                .findAllById(benutzerId);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                allgemeinerService.getAllgemeinenDaten(benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(allgemeinRepository).findAllById(benutzerId);
        }
    }

    @Nested
    @DisplayName("Tests für die Methode putAlleAllgemeinenDaten()")
    public class PutAlleAllgemeinenDatenTest {

        @Test
        void putAlleAllgemeinenDatenMitKorrekterAusgabeWennVorhandeneDatenExistieren() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);

            AllgemeineDaten daten = new AllgemeineDaten();
            daten.setAlter(25);
            daten.setBmi(24.5);
            daten.setGeschlecht("männlich");
            daten.setGewicht(80.0);
            daten.setGröße(180.0);

            AllgemeineDaten vorhandeneDaten = spy(new AllgemeineDaten()); 
            vorhandeneDaten.setAlter(65);
            vorhandeneDaten.setBmi(26.5);
            vorhandeneDaten.setGeschlecht("männlich");
            vorhandeneDaten.setGewicht(70.0);
            vorhandeneDaten.setGröße(190.0);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getAllgemein()).thenReturn(vorhandeneDaten); 

            allgemeinerService.putAlleAllgemeinenDaten(benutzerId, daten);

            verify(benutzerRepository).findById(benutzerId);
            verify(benutzer).getAllgemein();
            verify(vorhandeneDaten).setGröße(daten.getGröße());
            verify(vorhandeneDaten).setGewicht(daten.getGewicht());
            verify(vorhandeneDaten).setAlter(daten.getAlter());
            verify(vorhandeneDaten, times(2)).setGeschlecht(daten.getGeschlecht()); // da daten und vorhandeneDaten gleichen Inhalt haben, wird das zweimal aufgerufen. Löst aber ansich keinen Fehler aus in der normalen Anwendung.
            verify(vorhandeneDaten).setBmi(daten.getBmi());
            verify(allgemeinRepository).save(vorhandeneDaten);
        }

        @Test
        void putAlleAllgemeinenDatenMitKorrekterAusgabeWennVorhandeneDatenNichtExistieren() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);

            AllgemeineDaten daten = spy(new AllgemeineDaten());
            daten.setAlter(25);
            daten.setBmi(24.5);
            daten.setGeschlecht("männlich");
            daten.setGewicht(80.0);
            daten.setGröße(180.0);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getAllgemein()).thenReturn(null); 

            allgemeinerService.putAlleAllgemeinenDaten(benutzerId, daten);

            verify(daten).setBenutzer(benutzer);
            verify(allgemeinRepository).save(daten);
        }

        @Test
        void putAlleAllgemeinenDatenErrorAusgabeWennBenutzerNichtExistiert() {
            AllgemeineDaten daten = new AllgemeineDaten();
            daten.setAlter(25);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                allgemeinerService.putAlleAllgemeinenDaten(benutzerId, daten);
            });

            assertEquals("Benutzer mit ID " + benutzerId + " nicht gefunden", exception.getMessage());
        }

        @Test
        void putAlleAllgemeinenDatenErrorAusgabeWennDatenGleichNull() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                allgemeinerService.putAlleAllgemeinenDaten(benutzerId, null);
            });

            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
        }

        @Test
        void putAlleAllgemeinenDatenErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            AllgemeineDaten daten = new AllgemeineDaten();
            daten.setAlter(25);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                allgemeinerService.putAlleAllgemeinenDaten(benutzerId, daten);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
        }

        @Test
        void putAlleAllgemeinenDatenErrorAusgabeBeiAllgemeinemFehler() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            AllgemeineDaten daten = spy(new AllgemeineDaten());
            daten.setAlter(25);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            doThrow(new RuntimeException("Test-Fehler"))
                .when(allgemeinRepository)
                .save(any());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                allgemeinerService.putAlleAllgemeinenDaten(benutzerId, daten);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
        }
    }
}
