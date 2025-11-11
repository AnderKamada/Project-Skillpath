package com.fiap.gs.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModuloRequest(
        @NotNull Long trilhaId,
        @NotBlank @Size(min=3, max=120) String titulo
) {}

