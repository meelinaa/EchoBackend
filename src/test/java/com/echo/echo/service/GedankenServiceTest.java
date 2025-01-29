package com.echo.echo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.mentalerRepository.GedankenRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;
import com.echo.echo.service.mentalerService.GedankenService;

import jakarta.persistence.EntityNotFoundException;

public class GedankenServiceTest {

    private GedankenService gedankenService;
    private GedankenRepository gedankenRepository;
    private BenutzerRepository benutzerRepository;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp() {
        gedankenRepository = mock(GedankenRepository.class);
        benutzerRepository = mock(BenutzerRepository.class);
        gedankenService = new GedankenService(gedankenRepository, benutzerRepository);
    }

    @Nested
    @DisplayName("Tests für die Methode getGedanken()")
    public class GetGedankenTest {

        @Test
        void getGedankenMitKorrekterAusgabe() {
            GedankenDaten daten = new GedankenDaten();
            daten.setDatum(datum);
            daten.setGedanken("Heute war ein guter Tag.");

            when(gedankenRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(daten);

            GedankenDaten result = gedankenService.getGedanken(datum, benutzerId);

            assertEquals(daten, result);
        }

        @Test
        void getGedankenErrorAusgabeWennDatumGleichNull() {
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gedankenService.getGedanken(datum, benutzerId);
            });

            assertEquals("Datum darf nicht null sein", exception.getMessage());
            verify(gedankenRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getGedankenErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gedankenService.getGedanken(datum, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
            verify(gedankenRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getGedankenErrorAusgabeWennDatenGleichNull() {
            when(gedankenRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                gedankenService.getGedanken(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(gedankenRepository).getByDatumUndBenutzer(datum, benutzerId);
        }

        @Test
        void getGedankenErrorAusgabeBeiAllgemeinemFehler() {
            doThrow(new RuntimeException())
                .when(gedankenRepository)
                .getByDatumUndBenutzer(datum, benutzerId);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                gedankenService.getGedanken(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(gedankenRepository).getByDatumUndBenutzer(datum, benutzerId);
        }
    }

    @Nested
    @DisplayName("Tests für die Methode putGedanken()")
    public class PutGedankenTest {

        @Test
        void putGedankenMitKorrekterAusgabeWennVorhandeneDatenExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            GedankenDaten daten = new GedankenDaten();
            daten.setDatum(datum);
            daten.setGedanken("Heute war ein sehr produktiver Tag.");

            GedankenDaten vorhandeneDaten = spy(new GedankenDaten());
            vorhandeneDaten.setDatum(datum);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(gedankenRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(vorhandeneDaten);

            gedankenService.putGedanken(daten, benutzerId);

            verify(vorhandeneDaten).setGedanken(daten.getGedanken());
            //verify(vorhandeneDaten).setDatum(daten.getDatum());
            verify(gedankenRepository).save(vorhandeneDaten);
        }

        @Test
        void putGedankenMitKorrekterAusgabeWennVorhandeneDatenNichtExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            GedankenDaten daten = spy(new GedankenDaten());
            daten.setDatum(datum);
            daten.setGedanken("Heute war ein sehr produktiver Tag.");

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(gedankenRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            gedankenService.putGedanken(daten, benutzerId);

            verify(daten).setBenutzer(benutzer);
            verify(gedankenRepository).save(daten);
        }

        @Test
        void putGedankenErrorAusgabeWennBenutzerNichtExistiert() {
            GedankenDaten daten = new GedankenDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                gedankenService.putGedanken(daten, benutzerId);
            });

            assertEquals("Benutzer mit ID " + benutzerId + " nicht gefunden", exception.getMessage());
        }

        @Test
        void putGedankenErrorAusgabeWennDatenGleichNull() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gedankenService.putGedanken(null, benutzerId);
            });

            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
        }

        @Test
        void putGedankenErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            GedankenDaten daten = new GedankenDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gedankenService.putGedanken(daten, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
        }

        @Test
        void putGedankenErrorAusgabeBeiAllgemeinemFehler() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            GedankenDaten daten = spy(new GedankenDaten());
            daten.setDatum(datum);
            daten.setGedanken("Heute war ein sehr produktiver Tag.");

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            doThrow(new RuntimeException("Test-Fehler"))
                .when(gedankenRepository)
                .getByDatumUndBenutzer(any(), any());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                gedankenService.putGedanken(daten, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten: Test-Fehler", exception.getMessage());
        }
    }
}
