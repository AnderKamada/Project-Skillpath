package com.fiap.gs.api.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/hello")
@Tag(name = "Hello", description = "Endpoints de saúde/diagnóstico")
public class HelloController {

    @GetMapping
    public Map<String, String> hello() {
        return Map.of("message", "API funcionando!", "env", "local");
    }
}
