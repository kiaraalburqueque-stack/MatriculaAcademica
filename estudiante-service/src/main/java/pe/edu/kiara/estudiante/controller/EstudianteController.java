package pe.edu.kiara.estudiante.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.edu.kiara.estudiante.dto.*;
import pe.edu.kiara.estudiante.services.EstudianteService;

@RestController
@RequestMapping("/api/estudiantes")
@Validated
public class EstudianteController {
  private final EstudianteService service;

  public EstudianteController(EstudianteService service) {
    this.service = service;
  }

  @GetMapping
  public List<EstudianteResponse> listar() {
    return service.listar();
  }

  @GetMapping("/{id}")
  public EstudianteResponse buscar(@PathVariable @Positive Long id) {
    return service.buscar(id);
  }

  @PostMapping
  public ResponseEntity<EstudianteResponse> crear(@Valid @RequestBody EstudianteRequest request) {
    var response = service.crear(request);
    return ResponseEntity.created(URI.create("/api/estudiantes/" + response.id())).body(response);
  }

  @PutMapping("/{id}")
  public EstudianteResponse actualizar(
      @PathVariable @Positive Long id, @Valid @RequestBody EstudianteRequest request) {
    return service.actualizar(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable @Positive Long id) {
    service.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}
