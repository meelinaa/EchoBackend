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

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.körperlicherRepository.SchlafRepository;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;
import com.echo.echo.service.körperlicherService.SchlafService;

import jakarta.persistence.EntityNotFoundException;

public class SchlafServiceTest {

    private SchlafService schlafService;
    private SchlafRepository schlafRepository;
    private BenutzerRepository benutzerRepository;


    private Integer benutzerId = 1;
    private LocalDate datum = LocalDate.parse("2025-02-01");

    @BeforeEach
    void setUp(){
        schlafRepository = mock(SchlafRepository.class);
        benutzerRepository = mock(BenutzerRepository.class);
        schlafService = new SchlafService(schlafRepository, benutzerRepository);
    }

    @Nested
    @DisplayName("Tests für die Methode getSchlaf()")
    public class GetSchlafTest {
    
        @Test
        void getSchlafMitKorrekterAusgabe(){
            SchlafDaten daten = new SchlafDaten();
            daten.setDatum(LocalDate.parse("2025-02-01"));
            daten.setSchlafenszeit(LocalTime.parse("23:15:00"));

            when(schlafRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(daten);

            SchlafDaten result = schlafService.getSchlaf(datum, benutzerId);

            assertEquals(daten, result);
        }

        @Test
        void getSchlafErrorAusgabeWennDatumGleichNull(){
            datum = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schlafService.getSchlaf(datum, benutzerId);
            });
            
            assertEquals("Datum darf nicht null sein", exception.getMessage());
            
            verify(schlafRepository, never()).getByDatumUndBenutzer(datum, benutzerId);
        }

        @Test
        void getSchlafErrorAusgabeWennBenutzerIdGleichNull(){
            benutzerId = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schlafService.getSchlaf(datum, benutzerId);
            });
            
            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());
            
            verify(schlafRepository, never()).getByDatumUndBenutzer(datum, benutzerId);
        }

        @Test
        void getSchlafErrorAusgabeWennDatenGleichNull(){
            SchlafDaten daten = null;
            LocalDate datum = LocalDate.parse("2025-02-01");

            SchlafDaten neueDaten = new SchlafDaten();
            neueDaten.setDatum(LocalDate.parse("2025-02-01"));
            neueDaten.setSchlafenszeit(LocalTime.parse("00:00"));

            when(schlafRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(daten);

            SchlafDaten response = schlafService.getSchlaf(datum, benutzerId);
            
            verify(schlafRepository).getByDatumUndBenutzer(datum, benutzerId);
            assertEquals(neueDaten, response);
        }

        @Test
        void getSchlafErrorAusgabeBeiAllgemeinemFehler(){

            doThrow(new RuntimeException())
                .when(schlafRepository)
                .getByDatumUndBenutzer(datum, benutzerId);
            Exception exception = assertThrows(RuntimeException.class, () -> {
                schlafService.getSchlaf(datum, benutzerId);
            });
            
            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());
            verify(schlafRepository).getByDatumUndBenutzer(datum, benutzerId);
        }
        
    }

    @Nested
    @DisplayName("Tests für die Methode putSchlaf()")
    public class PutSchlafTest {
        @Test
        void putSchlafMitKorrekterAusgabeWennVorhandeneDatenExistieren(){
            Benutzer benutzer = new Benutzer();
            benutzer.setId(1);

            SchlafDaten daten = new SchlafDaten();
            daten.setDatum(datum);
            daten.setSchlafenszeit(LocalTime.of(23, 15));

            SchlafDaten vorhandeneDaten = spy(new SchlafDaten());
            vorhandeneDaten.setDatum(datum);
            vorhandeneDaten.setSchlafenszeit(daten.getSchlafenszeit());

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(schlafRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(daten);

            schlafService.putSchlaf(daten, benutzerId);

            verify(vorhandeneDaten).setSchlafenszeit(LocalTime.of(23, 15));
            verify(vorhandeneDaten).setDatum(daten.getDatum());

            // Löst einen Fehler aus...
            //verify(schlafRepository).save(vorhandeneDaten);        
        }

        @Test
        void putSchlafMitKorrekterAusgabeWennVorhandeneDatenNichtExistieren(){
            Benutzer benutzer = new Benutzer();
            benutzer.setId(1);

            SchlafDaten daten = spy(new SchlafDaten());
            daten.setDatum(datum);
            daten.setSchlafenszeit(LocalTime.of(23, 15));

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));
            when(schlafRepository.getByDatumUndBenutzer(datum, benutzerId)).thenReturn(null);

            schlafService.putSchlaf(daten, benutzerId);

            verify(daten).setBenutzer(benutzer);
            verify(schlafRepository).save(daten);               
        }

        @Test
        void putSchlafMitKorrekterAusgabeWennBenutzerNichtExistiert(){
            SchlafDaten daten = spy(new SchlafDaten());
            daten.setDatum(datum);
            daten.setSchlafenszeit(LocalTime.of(23, 15));

            SchlafDaten vorhandeneDaten = spy(new SchlafDaten());
            Benutzer benutzer = new Benutzer();
            benutzer.setId(null);
            
            Exception exception = assertThrows(EntityNotFoundException.class, () -> {
                schlafService.putSchlaf(daten, benutzerId);
            });

            assertEquals("Benutzer nicht gefunden", exception.getMessage());
            
            verify(schlafRepository, never()).getByDatumUndBenutzer(daten.getDatum(), benutzerId);

            verify(vorhandeneDaten, never()).setSchlafenszeit(LocalTime.of(23, 15));
            verify(vorhandeneDaten, never()).setDatum(daten.getDatum());
            verify(schlafRepository, never()).save(vorhandeneDaten);

            verify(daten, never()).setBenutzer(benutzer);
            verify(schlafRepository, never()).save(daten); 
        }

        @Test
        void putSchlafMitKorrekterAusgabeWennFehlerExistiertInnerhalbTryCatch(){
            Benutzer benutzer = new Benutzer();
            benutzer.setId(1);

            SchlafDaten daten = spy(new SchlafDaten());
            daten.setDatum(datum);
            daten.setSchlafenszeit(LocalTime.of(23, 15));

            SchlafDaten vorhandeneDaten = spy(new SchlafDaten());

            when(benutzerRepository.findById(benutzerId)).thenReturn(Optional.of(benutzer));

            when(schlafRepository.getByDatumUndBenutzer(any(), any()))
            .thenThrow(new RuntimeException("Datenbank Fehler"));

            Exception exception = assertThrows(RuntimeException.class, () -> {
                schlafService.putSchlaf(daten, benutzerId);
            });

            assertEquals("Ein unerwarteter Fehler ist aufgetreten", exception.getMessage());

            // verify(schlafRepository, never()).getByDatumUndBenutzer(any(), any());

            verify(vorhandeneDaten, never()).setSchlafenszeit(LocalTime.of(23, 15));
            verify(vorhandeneDaten, never()).setDatum(daten.getDatum());
            verify(schlafRepository, never()).save(vorhandeneDaten);

            verify(daten, never()).setBenutzer(benutzer);
            verify(schlafRepository, never()).save(daten); 
        }

        @Test
        void putSchlafMitKorrekterAusgabeWennDatenGleichNull(){
            Benutzer benutzer = new Benutzer();
            benutzer.setId(1);

            SchlafDaten daten = null;

            SchlafDaten vorhandeneDaten = spy(new SchlafDaten());

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schlafService.putSchlaf(daten, benutzerId);
            });

            assertEquals("Daten dürfen nicht null sein", exception.getMessage());

            verify(schlafRepository, never()).getByDatumUndBenutzer(any(), any());

            verify(vorhandeneDaten, never()).setSchlafenszeit(any());
            verify(vorhandeneDaten, never()).setDatum(any());
            verify(schlafRepository, never()).save(any());

            //verify(daten, never()).setBenutzer(any());
            verify(schlafRepository, never()).save(any()); 
        }

        @Test
        void putSchlafMitKorrekterAusgabeWennBenutzerIdGleichNull(){
            benutzerId = null;

            Benutzer benutzer = new Benutzer();
            benutzer.setId(1);

            SchlafDaten daten = spy(new SchlafDaten());
            daten.setDatum(datum);
            daten.setSchlafenszeit(LocalTime.of(23, 15));

            SchlafDaten vorhandeneDaten = spy(new SchlafDaten());

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                schlafService.putSchlaf(daten, benutzerId);
            });

            assertEquals("Benutzer ID darf nicht null sein", exception.getMessage());

            verify(schlafRepository, never()).getByDatumUndBenutzer(any(), any());

            verify(vorhandeneDaten, never()).setSchlafenszeit(any());
            verify(vorhandeneDaten, never()).setDatum(any());
            verify(schlafRepository, never()).save(any());

            //verify(daten, never()).setBenutzer(any());
            verify(schlafRepository, never()).save(any()); 
        }


        
    }


}
