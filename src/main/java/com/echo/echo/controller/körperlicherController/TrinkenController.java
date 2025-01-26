package com.echo.echo.controller.körperlicherController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.echo.echo.service.körperlicherService.TrinkenService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/allgemein")
@AllArgsConstructor
@NoArgsConstructor
public class TrinkenController {
    private TrinkenService trinkenService;
}
