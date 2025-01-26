package com.echo.echo.controller.mentalerController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.service.mentalerService.GedankenService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/allgemein")
@AllArgsConstructor
@NoArgsConstructor
public class GedankenController {
    private GedankenService gedankenService;
}
