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
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.mentalerRepository.GemütRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;
import com.echo.echo.service.mentalerService.GemütService;

import jakarta.persistence.EntityNotFoundException;

public class GemütServiceTest {

    private GemütService gemütService;
    private GemütRepository gemütRepository;
    private BenutzerRepository benutzerRepository;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp() {
        gemütRepository = mock(GemütRepository.class);
        benutzerRepository = mock(BenutzerRepository.class);
        gemütService = new GemütService(gemütRepository, benutzerRepository);
    }

    @Nested
    @DisplayName("Tests für die Methode getGemüt()")
    public class GetGemütTest {

        @Test
        void getGemütMitKorrekterAusgabe() {
            GemütDaten daten = new GemütDaten();
            daten.setDatum(datum);
            daten.setGemütszustand(7);
            daten.setBeschreibung(Arrays.asList("Glücklich", "Zufrieden"));
            daten.setGrund(Arrays.asList("Gutes Wetter", "Produktiver Tag"));

            when(gemütRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(daten);

            GemütDaten result = gemütService.getGemüt(datum, benutzerId);

            assertEquals(daten, result);
        }

        @Test
        void getGemütErrorAusgabeWennDatumGleichNull() {
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gemütService.getGemüt(datum, benutzerId);
            });

            assertEquals("Datum darf nicht null sein", exception.getMessage());
            verify(gemütRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getGemütErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gemütService.getGemüt(datum, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
            verify(gemütRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getGemütErrorAusgabeWennDatenGleichNull() {
            when(gemütRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                gemütService.getGemüt(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(gemütRepository).getByDatumUndBenutzer(datum, benutzerId);
        }

        @Test
        void getGemütErrorAusgabeBeiAllgemeinemFehler() {
            doThrow(new RuntimeException())
                .when(gemütRepository)
                .getByDatumUndBenutzer(datum, benutzerId);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                gemütService.getGemüt(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(gemütRepository).getByDatumUndBenutzer(datum, benutzerId);
        }
    }

    @Nested
    @DisplayName("Tests für die Methode putGemüt()")
    public class PutGemütTest {

        @Test
        void putGemütMitKorrekterAusgabeWennVorhandeneDatenExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            GemütDaten daten = new GemütDaten();
            daten.setDatum(datum);
            daten.setGemütszustand(7);
            daten.setBeschreibung(Arrays.asList("Glücklich", "Zufrieden"));
            daten.setGrund(Arrays.asList("Gutes Wetter", "Produktiver Tag"));

            GemütDaten vorhandeneDaten = spy(new GemütDaten());
            vorhandeneDaten.setDatum(datum);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(gemütRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(vorhandeneDaten);

            gemütService.putGemüt(daten, benutzerId);

            verify(vorhandeneDaten).setGemütszustand(daten.getGemütszustand());
            verify(vorhandeneDaten).setBeschreibung(daten.getBeschreibung());
            verify(vorhandeneDaten).setGrund(daten.getGrund());
            //verify(vorhandeneDaten).setDatum(daten.getDatum());
            verify(gemütRepository).save(vorhandeneDaten);
        }

        @Test
        void putGemütMitKorrekterAusgabeWennVorhandeneDatenNichtExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            GemütDaten daten = spy(new GemütDaten());
            daten.setDatum(datum);
            daten.setGemütszustand(7);
            daten.setBeschreibung(Arrays.asList("Glücklich", "Zufrieden"));
            daten.setGrund(Arrays.asList("Gutes Wetter", "Produktiver Tag"));

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(gemütRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            gemütService.putGemüt(daten, benutzerId);

            verify(daten).setBenutzer(benutzer);
            verify(gemütRepository).save(daten);
        }

        @Test
        void putGemütErrorAusgabeWennBenutzerNichtExistiert() {
            GemütDaten daten = new GemütDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                gemütService.putGemüt(daten, benutzerId);
            });

            assertEquals("Benutzer mit ID " + benutzerId + " nicht gefunden", exception.getMessage());
        }

        @Test
        void putGemütErrorAusgabeWennDatenGleichNull() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gemütService.putGemüt(null, benutzerId);
            });

            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
        }

        @Test
        void putGemütErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            GemütDaten daten = new GemütDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                gemütService.putGemüt(daten, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
        }

        @Test
        void putGemütErrorAusgabeBeiAllgemeinemFehler() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            GemütDaten daten = spy(new GemütDaten());
            daten.setDatum(datum);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            doThrow(new RuntimeException())
                .when(gemütRepository)
                .getByDatumUndBenutzer(any(), any());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                gemütService.putGemüt(daten, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
        }
    }
}
