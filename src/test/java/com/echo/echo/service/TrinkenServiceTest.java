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

import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.TrinkenRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;
import com.echo.echo.service.körperlicherService.TrinkenService;

import jakarta.persistence.EntityNotFoundException;

public class TrinkenServiceTest {

    private TrinkenService trinkenService;
    private TrinkenRepository trinkenRepository;
    private BenutzerRepository benutzerRepository;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp() {
        trinkenRepository = mock(TrinkenRepository.class);
        benutzerRepository = mock(BenutzerRepository.class);
        trinkenService = new TrinkenService(trinkenRepository, benutzerRepository);
    }

    @Nested
    @DisplayName("Tests für die Methode getTrinken()")
    public class GetTrinkenTest {

        @Test
        void getTrinkenMitKorrekterAusgabe() {
            TrinkenDaten daten = new TrinkenDaten();
            daten.setDatum(datum);
            daten.setLiter(2.5);

            when(trinkenRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(daten);

            TrinkenDaten result = trinkenService.getTrinken(datum, benutzerId);

            assertEquals(daten, result);
        }

        @Test
        void getTrinkenErrorAusgabeWennDatumGleichNull() {
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                trinkenService.getTrinken(datum, benutzerId);
            });

            assertEquals("Datum darf nicht null sein", exception.getMessage());
            verify(trinkenRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getTrinkenErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                trinkenService.getTrinken(datum, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
            verify(trinkenRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getTrinkenErrorAusgabeWennDatenGleichNull() {
            when(trinkenRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                trinkenService.getTrinken(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(trinkenRepository).getByDatumUndBenutzer(datum, benutzerId);
        }

        @Test
        void getTrinkenErrorAusgabeBeiAllgemeinemFehler() {
            doThrow(new RuntimeException())
                .when(trinkenRepository)
                .getByDatumUndBenutzer(datum, benutzerId);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                trinkenService.getTrinken(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(trinkenRepository).getByDatumUndBenutzer(datum, benutzerId);
        }
    }

    @Nested
    @DisplayName("Tests für die Methode putTrinken()")
    public class PutTrinkenTest {

        @Test
        void putTrinkenMitKorrekterAusgabeWennVorhandeneDatenExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            TrinkenDaten daten = new TrinkenDaten();
            daten.setDatum(datum);
            daten.setLiter(2.5);

            TrinkenDaten vorhandeneDaten = spy(new TrinkenDaten());
            vorhandeneDaten.setDatum(datum);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(trinkenRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(vorhandeneDaten);

            trinkenService.putTrinken(daten, benutzerId);

            verify(vorhandeneDaten).setLiter(daten.getLiter());
            //verify(vorhandeneDaten).setDatum(daten.getDatum());
            verify(trinkenRepository).save(vorhandeneDaten);
        }

        @Test
        void putTrinkenMitKorrekterAusgabeWennVorhandeneDatenNichtExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            TrinkenDaten daten = spy(new TrinkenDaten());
            daten.setDatum(datum);
            daten.setLiter(2.5);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(trinkenRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            trinkenService.putTrinken(daten, benutzerId);

            verify(daten).setBenutzer(benutzer);
            verify(trinkenRepository).save(daten);
        }

        @Test
        void putTrinkenErrorAusgabeWennBenutzerNichtExistiert() {
            TrinkenDaten daten = new TrinkenDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                trinkenService.putTrinken(daten, benutzerId);
            });

            assertEquals("Benutzer mit ID " + benutzerId + " nicht gefunden", exception.getMessage());
        }

        @Test
        void putTrinkenErrorAusgabeWennDatenGleichNull() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                trinkenService.putTrinken(null, benutzerId);
            });

            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
        }

        @Test
        void putTrinkenErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            TrinkenDaten daten = new TrinkenDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                trinkenService.putTrinken(daten, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
        }

        @Test
        void putTrinkenErrorAusgabeBeiAllgemeinemFehler() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            TrinkenDaten daten = spy(new TrinkenDaten());
            daten.setDatum(datum);
            daten.setLiter(2.5);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            doThrow(new RuntimeException())
                .when(trinkenRepository)
                .getByDatumUndBenutzer(any(), any());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                trinkenService.putTrinken(daten, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
        }
    }
}
