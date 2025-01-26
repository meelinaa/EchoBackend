package com.echo.echo.controller.persönlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.service.persönlicherService.BenutzerService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/benutzer")
@AllArgsConstructor
@NoArgsConstructor
public class BenutzerController {
    private BenutzerService benutzerService;
}
