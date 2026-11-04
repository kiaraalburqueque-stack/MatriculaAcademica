package pe.edu.kiara.curso.dto;

import jakarta.validation.constraints.*;

public record CursoRequest(
    @NotBlank @Size(max = 30) String codigo,
    @NotBlank @Size(max = 150) String nombre,
    @NotNull @Positive Integer creditos,
    @NotNull @PositiveOrZero Integer vacantes) {}
