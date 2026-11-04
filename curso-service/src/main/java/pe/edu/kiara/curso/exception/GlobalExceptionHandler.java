package pe.edu.kiara.curso.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private ResponseEntity<ApiError> error(
      int status, String message, HttpServletRequest req, Map<String, String> fields) {
    return ResponseEntity.status(status)
        .body(new ApiError(Instant.now(), status, message, req.getRequestURI(), fields));
  }

  @ExceptionHandler(RecursoNoEncontradoException.class)
  public ResponseEntity<ApiError> missing(RecursoNoEncontradoException ex, HttpServletRequest req) {
    return error(404, ex.getMessage(), req, Map.of());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> invalid(
      MethodArgumentNotValidException ex, HttpServletRequest req) {
    Map<String, String> fields = new LinkedHashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(e -> fields.put(e.getField(), e.getDefaultMessage()));
    return error(400, "Revise los campos enviados", req, fields);
  }

  @ExceptionHandler({
    HttpMessageNotReadableException.class,
    MethodArgumentTypeMismatchException.class,
    ConstraintViolationException.class
  })
  public ResponseEntity<ApiError> malformed(Exception ex, HttpServletRequest req) {
    return error(400, "JSON, tipo de dato, fecha o identificador inválido", req, Map.of());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> conflict(
      DataIntegrityViolationException ex, HttpServletRequest req) {
    return error(
        409, "El código ya existe o el registro incumple una restricción de datos", req, Map.of());
  }

  @ExceptionHandler(RestClientException.class)
  public ResponseEntity<ApiError> unavailable(RestClientException ex, HttpServletRequest req) {
    return error(
        503,
        "No se pudo verificar el estudiante o curso. Compruebe los otros servicios",
        req,
        Map.of());
  }
}
