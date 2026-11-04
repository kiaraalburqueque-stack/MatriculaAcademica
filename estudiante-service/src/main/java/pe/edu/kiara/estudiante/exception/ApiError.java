package pe.edu.kiara.estudiante.exception;

import java.time.Instant;
import java.util.Map;

public record ApiError(
    Instant timestamp, int status, String mensaje, String ruta, Map<String, String> campos) {}
