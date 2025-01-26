package com.echo.echo.controller.persönlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.service.persönlicherService.AllgemeinerService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/allgemein")
@AllArgsConstructor
@NoArgsConstructor
public class AllgemeinController {

    private AllgemeinerService allgemeinerService;
    
}


