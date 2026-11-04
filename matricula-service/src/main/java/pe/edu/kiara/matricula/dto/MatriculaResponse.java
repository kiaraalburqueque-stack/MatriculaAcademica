package pe.edu.kiara.matricula.dto;

import java.time.LocalDate;

public record MatriculaResponse(
    Long id, Long idEstudiante, Long idCurso, LocalDate fecha, String estado) {}
