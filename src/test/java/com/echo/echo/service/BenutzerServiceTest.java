package com.echo.echo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;
import com.echo.echo.service.persönlicherService.BenutzerService;

import jakarta.persistence.EntityNotFoundException;

public class BenutzerServiceTest {
    
    private BenutzerService benutzerService;
    private BenutzerRepository benutzerRepository;

    private Integer benutzerId = 1;

    @BeforeEach
    void setUp(){
        benutzerRepository = mock(BenutzerRepository.class);
        benutzerService = new BenutzerService(benutzerRepository);
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleAllgemeinenDaten()")
    public class GetAlleAllgemeinenDatenTest {
    
        @Test
        void getAlleAllgemeinenDatenMitKorrekterAusgabe(){
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);

            AllgemeineDaten daten = new AllgemeineDaten();

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getAllgemein()).thenReturn(daten);

            AllgemeineDaten result = benutzerService.getAlleAllgemeinenDaten();

            assertEquals(daten, result);
        }

        @Test
        void getAlleAllgemeinenDatenMitErrorAusgabeWeilDatenGleichNull(){
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);

            AllgemeineDaten daten = null;

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getAllgemein()).thenReturn(daten);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleAllgemeinenDaten();
            });

            assertEquals("AllgemeineDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.", exception.getMessage());
        }

        @Test
        void getAlleAllgemeinenDatenMitErrorAusgabeWennBenutzerNichtGefundenWird(){
            Benutzer benutzer = spy(new Benutzer());
            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.empty()); // hier war der Fehler!

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleAllgemeinenDaten();
            });

            assertEquals("Benutzer mit ID" + benutzerId + "nicht gefunden", exception.getMessage());
            verify(benutzer, never()).getAllgemein();
        }

        
    }
@Nested
    @DisplayName("Tests für die Methode getAlleTräumeDaten()")
    public class GetAlleTräumeDatenTest {
        @Test
        void getAlleTräumeDatenMitKorrekterAusgabe() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);
            List<TräumeDaten> daten = List.of(new TräumeDaten());

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getTräume()).thenReturn(daten);

            assertEquals(daten, benutzerService.getAlleTräumeDaten());
        }

        @Test
        void getAlleTräumeDatenMitErrorAusgabeWennDatenGleichNull() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getTräume()).thenReturn(null);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleTräumeDaten();
            });

            assertEquals("TräumeDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.", exception.getMessage());
        }

        @Test
        void getAlleTräumeDatenMitErrorAusgabeWennBenutzerNichtGefundenWird() {
            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.empty());

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleTräumeDaten();
            });

            assertEquals("Benutzer mit ID" + benutzerId + "nicht gefunden", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleGedankenDaten()")
    public class GetAlleGedankenDatenTest {
        @Test
        void getAlleGedankenDatenMitKorrekterAusgabe() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);
            List<GedankenDaten> daten = List.of(new GedankenDaten());

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getGedanken()).thenReturn(daten);

            assertEquals(daten, benutzerService.getAlleGedankenDaten());
        }

        @Test
        void getAlleGedankenDatenMitErrorAusgabeWennDatenGleichNull() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getGedanken()).thenReturn(null);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleGedankenDaten();
            });

            assertEquals("GedankenDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.", exception.getMessage());
        }

        @Test
        void getAlleGedankenDatenMitErrorAusgabeWennBenutzerNichtGefundenWird() {
            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.empty());

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleGedankenDaten();
            });

            assertEquals("Benutzer mit ID" + benutzerId + "nicht gefunden", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleSportDaten()")
    public class GetAlleSportDatenTest {
        @Test
        void getAlleSportDatenMitKorrekterAusgabe() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);
            List<SportDaten> daten = List.of(new SportDaten());

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getSport()).thenReturn(daten);

            assertEquals(daten, benutzerService.getAlleSportDaten());
        }

        @Test
        void getAlleSportDatenMitErrorAusgabeWennDatenGleichNull() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getSport()).thenReturn(null);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleSportDaten();
            });

            assertEquals("SportDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.", exception.getMessage());
        }

        @Test
        void getAlleSportDatenMitErrorAusgabeWennBenutzerNichtGefundenWird() {
            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.empty());

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleSportDaten();
            });

            assertEquals("Benutzer mit ID" + benutzerId + "nicht gefunden", exception.getMessage());
        }
    }

        @Nested
    @DisplayName("Tests für die Methode getAlleTrinkenDaten()")
    public class GetAlleTrinkenDatenTest {
        
        @Test
        void getAlleTrinkenDatenMitKorrekterAusgabe() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);
            List<TrinkenDaten> daten = List.of(new TrinkenDaten());

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getTrinken()).thenReturn(daten);

            assertEquals(daten, benutzerService.getAlleTrinkenDaten());
        }

        @Test
        void getAlleTrinkenDatenMitErrorAusgabeWennDatenGleichNull() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getTrinken()).thenReturn(null);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleTrinkenDaten();
            });

            assertEquals("TrinkenDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.", exception.getMessage());
        }

        @Test
        void getAlleTrinkenDatenMitErrorAusgabeWennBenutzerNichtGefundenWird() {
            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.empty());

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleTrinkenDaten();
            });

            assertEquals("Benutzer mit ID" + benutzerId + "nicht gefunden", exception.getMessage());
        }
    }


    @Nested
    @DisplayName("Tests für die Methode getAlleGemütDaten()")
    public class GetAlleGemütDatenTest {
        @Test
        void getAlleGemütDatenMitKorrekterAusgabe() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);
            List<GemütDaten> daten = List.of(new GemütDaten());

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getGemüt()).thenReturn(daten);

            assertEquals(daten, benutzerService.getAlleGemütDaten());
        }

        @Test
        void getAlleGemütDatenMitErrorAusgabeWennDatenGleichNull() {
            Benutzer benutzer = spy(new Benutzer());
            benutzer.setId(benutzerId);

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(benutzer.getGemüt()).thenReturn(null);

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleGemütDaten();
            });

            assertEquals("GemütDaten für Benutzer mit ID " + benutzerId + " nicht gefunden.", exception.getMessage());
        }

        @Test
        void getAlleGemütDatenMitErrorAusgabeWennBenutzerNichtGefundenWird() {
            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.empty());

            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                benutzerService.getAlleGemütDaten();
            });

            assertEquals("Benutzer mit ID" + benutzerId + "nicht gefunden", exception.getMessage());
        }
    }
    
}
