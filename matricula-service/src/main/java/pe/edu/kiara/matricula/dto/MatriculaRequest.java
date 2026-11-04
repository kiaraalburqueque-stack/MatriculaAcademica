package pe.edu.kiara.matricula.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record MatriculaRequest(
    @NotNull @Positive Long idEstudiante,
    @NotNull @Positive Long idCurso,
    @NotNull LocalDate fecha,
    @NotBlank @Pattern(regexp = "ACTIVA|ANULADA") String estado) {}
