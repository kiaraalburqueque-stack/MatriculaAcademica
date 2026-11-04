package pe.edu.kiara.estudiante.dto;

import jakarta.validation.constraints.*;

public record EstudianteRequest(
    @NotBlank @Size(max = 30) String codigo,
    @NotBlank @Size(max = 100) String nombres,
    @NotBlank @Size(max = 100) String apellidos,
    @NotBlank @Email @Size(max = 150) String correo,
    @NotBlank @Pattern(regexp = "ACTIVO|INACTIVO") String estado) {}
