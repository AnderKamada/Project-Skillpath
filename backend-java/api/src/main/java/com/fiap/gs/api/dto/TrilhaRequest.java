package com.fiap.gs.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TrilhaRequest(
        @NotBlank @Size(min=3, max=120) String nome,
        @Size(max=500) String descricao
) {}
