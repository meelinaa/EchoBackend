package com.echo.echo.service.persönlicherService;

import java.util.List;
import java.util.Optional;

import com.echo.echo.model.körperlicheDaten.SchlafDaten;
import com.echo.echo.model.körperlicheDaten.SchritteDaten;
import com.echo.echo.model.körperlicheDaten.SportDaten;
import com.echo.echo.model.körperlicheDaten.TrinkenDaten;
import com.echo.echo.model.mentaleDaten.GedankenDaten;
import com.echo.echo.model.mentaleDaten.GemütDaten;
import com.echo.echo.model.mentaleDaten.TräumeDaten;
import com.echo.echo.model.persönlicheDaten.AllgemeineDaten;
import com.echo.echo.model.persönlicheDaten.Benutzer;
import com.echo.echo.repository.persönlicherRepository.BenutzerRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BenutzerService {

    private BenutzerRepository benutzerRepository;

    Integer id = 1;

    public Optional<Benutzer> getAlleBenutzerInfos() {
        return benutzerRepository.findById(id);
    }

    public List<AllgemeineDaten> getAlleAllgemeinenDaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAlleAllgemeinenDaten'");
    }

    public List<TräumeDaten> getAlleTräumeDaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAlleTräumeDaten'");
    }

    public List<SchlafDaten> getAlleSchlafDaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAlleSchlafDaten'");
    }

    public List<SchritteDaten> getAlleSchritteDaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAlleSchritteDaten'");
    }

    public List<GedankenDaten> getAlleGedankenDaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAlleGedankenDaten'");
    }

    public List<SportDaten> getAlleSportDaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAlleSportDaten'");
    }

    public List<TrinkenDaten> getAlleTrinkenDaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAlleTrinkenDaten'");
    }

    public List<GemütDaten> getAlleGemütDaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAlleGemütDaten'");
    }
    
}
