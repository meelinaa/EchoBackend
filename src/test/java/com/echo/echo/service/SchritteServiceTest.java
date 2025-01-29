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

import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.SchritteRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;
import com.echo.echo.service.körperlicherService.SchritteService;

import jakarta.persistence.EntityNotFoundException;

public class SchritteServiceTest {

    private SchritteService schritteService;
    private SchritteRepository schritteRepository;
    private BenutzerRepository benutzerRepository;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp() {
        schritteRepository = mock(SchritteRepository.class);
        benutzerRepository = mock(BenutzerRepository.class);
        schritteService = new SchritteService(schritteRepository, benutzerRepository);
    }

    @Nested
    @DisplayName("Tests für die Methode getSchritte()")
    public class GetSchritteTest {

        @Test
        void getSchritteMitKorrekterAusgabe() {
            SchritteDaten daten = new SchritteDaten();
            daten.setDatum(datum);
            daten.setSchritte(10000);
            daten.setMeter(7.5);

            when(schritteRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(daten);

            SchritteDaten result = schritteService.getSchritte(datum, benutzerId);

            assertEquals(daten, result);
        }

        @Test
        void getSchritteErrorAusgabeWennDatumGleichNull() {
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schritteService.getSchritte(datum, benutzerId);
            });

            assertEquals("Datum darf nicht null sein", exception.getMessage());
            verify(schritteRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getSchritteErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schritteService.getSchritte(datum, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
            verify(schritteRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getSchritteErrorAusgabeWennDatenGleichNull() {
            SchritteDaten daten = null;
            when(schritteRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(daten);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                schritteService.getSchritte(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(schritteRepository).getByDatumUndBenutzer(datum, benutzerId);
        }

        @Test
        void getSchritteErrorAusgabeBeiAllgemeinemFehler() {
            doThrow(new RuntimeException())
                .when(schritteRepository)
                .getByDatumUndBenutzer(datum, benutzerId);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                schritteService.getSchritte(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(schritteRepository).getByDatumUndBenutzer(datum, benutzerId);
        }
    }

    @Nested
    @DisplayName("Tests für die Methode putSchritte()")
    public class PutSchritteTest {

        @Test
        void putSchritteMitKorrekterAusgabeWennVorhandeneDatenExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            SchritteDaten daten = new SchritteDaten();
            daten.setDatum(datum);
            daten.setSchritte(10000);
            daten.setMeter(7.5);

            SchritteDaten vorhandeneDaten = spy(new SchritteDaten());
            vorhandeneDaten.setDatum(datum);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(schritteRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(vorhandeneDaten);

            schritteService.putSchritte(daten, benutzerId);

            verify(vorhandeneDaten).setSchritte(any());
            verify(vorhandeneDaten).setMeter(any());
            //verify(vorhandeneDaten).setDatum(any());
            verify(schritteRepository).save(vorhandeneDaten);
        }

        @Test
        void putSchritteMitKorrekterAusgabeWennVorhandeneDatenNichtExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            SchritteDaten daten = spy(new SchritteDaten());
            daten.setDatum(datum);
            daten.setSchritte(10000);
            daten.setMeter(7.5);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(schritteRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            schritteService.putSchritte(daten, benutzerId);

            verify(daten).setBenutzer(benutzer);
            verify(schritteRepository).save(daten);
        }

        @Test
        void putSchritteErrorAusgabeWennBenutzerNichtExistiert() {
            SchritteDaten daten = new SchritteDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                schritteService.putSchritte(daten, benutzerId);
            });

            assertEquals("Benutzer nicht gefunden", exception.getMessage());
        }

        @Test
        void putSchritteErrorAusgabeWennDatenGleichNull() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schritteService.putSchritte(null, benutzerId);
            });

            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
        }

        @Test
        void putSchritteErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            SchritteDaten daten = new SchritteDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schritteService.putSchritte(daten, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
        }

        @Test
        void putSchritteErrorAusgabeBeiAllgemeinemFehler() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            SchritteDaten daten = spy(new SchritteDaten());
            daten.setDatum(datum);
            daten.setSchritte(10000);
            daten.setMeter(7.5);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            doThrow(new RuntimeException())
                .when(schritteRepository)
                .getByDatumUndBenutzer(any(), any());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                schritteService.putSchritte(daten, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
        }
    }
}
