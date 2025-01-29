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
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.SportRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;
import com.echo.echo.service.körperlicherService.SportService;

import jakarta.persistence.EntityNotFoundException;

public class SportServiceTest {

    private SportService sportService;
    private SportRepository sportRepository;
    private BenutzerRepository benutzerRepository;

    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp() {
        sportRepository = mock(SportRepository.class);
        benutzerRepository = mock(BenutzerRepository.class);
        sportService = new SportService(sportRepository, benutzerRepository);
    }

    @Nested
    @DisplayName("Tests für die Methode getSport()")
    public class GetSportTest {

        @Test
        void getSportMitKorrekterAusgabe() {
            SportDaten daten = new SportDaten();
            daten.setDatum(datum);
            daten.setSportart("Joggen");
            daten.setTrauningsDauer(LocalTime.of(1, 30));

            when(sportRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(daten);

            SportDaten result = sportService.getSport(datum, benutzerId);

            assertEquals(daten, result);
        }

        @Test
        void getSportErrorAusgabeWennDatumGleichNull() {
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                sportService.getSport(datum, benutzerId);
            });

            assertEquals("Datum darf nicht null sein", exception.getMessage());
            verify(sportRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getSportErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                sportService.getSport(datum, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
            verify(sportRepository, never()).getByDatumUndBenutzer(any(), any());
        }

        @Test
        void getSportErrorAusgabeWennDatenGleichNull() {
            when(sportRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                sportService.getSport(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten" , exception.getMessage());
            verify(sportRepository).getByDatumUndBenutzer(datum, benutzerId);
        }

        @Test
        void getSportErrorAusgabeBeiAllgemeinemFehler() {
            doThrow(new RuntimeException())
                .when(sportRepository)
                .getByDatumUndBenutzer(datum, benutzerId);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                sportService.getSport(datum, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(sportRepository).getByDatumUndBenutzer(datum, benutzerId);
        }
    }

    @Nested
    @DisplayName("Tests für die Methode putSport()")
    public class PutSportTest {

        @Test
        void putSportMitKorrekterAusgabeWennVorhandeneDatenExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            SportDaten daten = new SportDaten();
            daten.setDatum(datum);
            daten.setSportart("Joggen");
            daten.setTrauningsDauer(LocalTime.of(1, 30));

            SportDaten vorhandeneDaten = spy(new SportDaten());
            vorhandeneDaten.setDatum(datum);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(sportRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(vorhandeneDaten);

            sportService.putSport(daten, benutzerId);

            verify(vorhandeneDaten).setSportart(daten.getSportart());
            verify(vorhandeneDaten).setTrauningsDauer(daten.getTrauningsDauer());
            // verify(vorhandeneDaten).setDatum(daten.getDatum());
            verify(sportRepository).save(vorhandeneDaten);
        }

        @Test
        void putSportMitKorrekterAusgabeWennVorhandeneDatenNichtExistieren() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            SportDaten daten = spy(new SportDaten());
            daten.setDatum(datum);
            daten.setSportart("Joggen");
            daten.setTrauningsDauer(LocalTime.of(1, 30));

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(sportRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            sportService.putSport(daten, benutzerId);

            verify(daten).setBenutzer(benutzer);
            verify(sportRepository).save(daten);
        }

        @Test
        void putSportErrorAusgabeWennBenutzerNichtExistiert() {
            SportDaten daten = new SportDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                sportService.putSport(daten, benutzerId);
            });

            assertEquals("Benutzer mit ID " + benutzerId + " nicht gefunden", exception.getMessage());
        }

        @Test
        void putSportErrorAusgabeWennDatenGleichNull() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                sportService.putSport(null, benutzerId);
            });

            assertEquals("Daten dürfen nicht null sein", exception.getMessage());
        }

        @Test
        void putSportErrorAusgabeWennBenutzerIdGleichNull() {
            benutzerId = null;

            SportDaten daten = new SportDaten();
            daten.setDatum(datum);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                sportService.putSport(daten, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
        }

        @Test
        void putSportErrorAusgabeBeiAllgemeinemFehler() {
            Benutzer benutzer = new Benutzer();
            benutzer.setId(benutzerId);

            SportDaten daten = spy(new SportDaten());
            daten.setDatum(datum);
            daten.setSportart("Joggen");
            daten.setTrauningsDauer(LocalTime.of(1, 30));

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            doThrow(new RuntimeException("Test-Fehler"))
                .when(sportRepository)
                .getByDatumUndBenutzer(any(), any());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                sportService.putSport(daten, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten: Test-Fehler", exception.getMessage());
        }
    }
}
