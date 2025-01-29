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

import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.mentalerRepository.TräumeRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;
import com.echo.echo.service.mentalerService.TräumeService;

import jakarta.persistence.EntityNotFoundException;

public class TräumeServiceTest {

    private TräumeService träumeService;
    private TräumeRepository träumeRepository;
    private BenutzerRepository benutzerRepository;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp() {
        träumeRepository = mock(TräumeRepository.class);
        benutzerRepository = mock(BenutzerRepository.class);
        träumeService = new TräumeService(träumeRepository, benutzerRepository);
    }

    @Nested
    @DisplayName("Tests für die Methode getTraum()")
    public class GetTraumTest {

        @Test
        void getTraumMitKorrekterAusgabe() {
            TräumeDaten daten = new TräumeDaten();
            daten.setDatum(datum);
            daten.setBewertung(8);
            daten.setTraum("Ich bin durch den Wald gelaufen und habe einen Adler gesehen.");

            when(träumeRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(daten);

            TräumeDaten result = träumeService.getTraum(datum, benutzerId);

            assertEquals(daten, result);
        }

        @Test
        void getTraumErrorAusgabeWennDatumGleichNull() {
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                träumeService.getTraum(datum, benutzerId);
            });

            assertEquals("Datum darf nicht null sein", exception.getMessage());
            verify(träumeRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getTraumErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                träumeService.getTraum(datum, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
            verify(träumeRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getTraumErrorAusgabeWennDatenGleichNull() {
            when(träumeRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                träumeService.getTraum(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(träumeRepository).getByDatumUndBenutzer(datum, benutzerId);
        }

        @Test
        void getTraumErrorAusgabeBeiAllgemeinemFehler() {
            doThrow(new RuntimeException())
                .when(träumeRepository)
                .getByDatumUndBenutzer(datum, benutzerId);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                träumeService.getTraum(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(träumeRepository).getByDatumUndBenutzer(datum, benutzerId);
        }
    }

    @Nested
    @DisplayName("Tests für die Methode putTraum()")
    public class PutTraumTest {

        @Test
        void putTraumMitKorrekterAusgabeWennVorhandeneDatenExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            TräumeDaten daten = new TräumeDaten();
            daten.setDatum(datum);
            daten.setBewertung(7);
            daten.setTraum("Ich bin durch einen wunderschönen Garten gelaufen.");

            TräumeDaten vorhandeneDaten = spy(new TräumeDaten());
            vorhandeneDaten.setDatum(datum);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(träumeRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(vorhandeneDaten);

            träumeService.putTraum(daten, benutzerId);

            verify(vorhandeneDaten).setBewertung(daten.getBewertung());
            verify(vorhandeneDaten).setTraum(daten.getTraum());
            //verify(vorhandeneDaten).setDatum(daten.getDatum());
            verify(träumeRepository).save(vorhandeneDaten);
        }

        @Test
        void putTraumMitKorrekterAusgabeWennVorhandeneDatenNichtExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            TräumeDaten daten = spy(new TräumeDaten());
            daten.setDatum(datum);
            daten.setBewertung(7);
            daten.setTraum("Ich bin durch einen wunderschönen Garten gelaufen.");

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(träumeRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            träumeService.putTraum(daten, benutzerId);

            verify(daten).setBenutzer(benutzer);
            verify(träumeRepository).save(daten);
        }

        @Test
        void putTraumErrorAusgabeWennBenutzerNichtExistiert() {
            TräumeDaten daten = new TräumeDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                träumeService.putTraum(daten, benutzerId);
            });

            assertEquals("Benutzer mit ID " + benutzerId + " nicht gefunden", exception.getMessage());
        }

        @Test
        void putTraumErrorAusgabeWennDatenGleichNull() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                träumeService.putTraum(null, benutzerId);
            });

            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
        }

        @Test
        void putTraumErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            TräumeDaten daten = new TräumeDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                träumeService.putTraum(daten, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
        }

        @Test
        void putTraumErrorAusgabeBeiAllgemeinemFehler() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            TräumeDaten daten = spy(new TräumeDaten());
            daten.setDatum(datum);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            doThrow(new RuntimeException())
                .when(träumeRepository)
                .getByDatumUndBenutzer(any(), any());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                träumeService.putTraum(daten, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
        }
    }
}
