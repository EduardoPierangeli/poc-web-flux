package com.ciandt.pocwebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebFluxController {
    @GetMapping("/teste")
    public String existFile() {
        return "ok";
    }
}
