package pe.edu.kiara.matricula.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.edu.kiara.matricula.dto.*;
import pe.edu.kiara.matricula.services.MatriculaService;

@RestController
@RequestMapping("/api/matriculas")
@Validated
public class MatriculaController {
  private final MatriculaService service;

  public MatriculaController(MatriculaService service) {
    this.service = service;
  }

  @GetMapping
  public List<MatriculaResponse> listar() {
    return service.listar();
  }

  @GetMapping("/{id}")
  public MatriculaResponse buscar(@PathVariable @Positive Long id) {
    return service.buscar(id);
  }

  @PostMapping
  public ResponseEntity<MatriculaResponse> crear(@Valid @RequestBody MatriculaRequest request) {
    var response = service.crear(request);
    return ResponseEntity.created(URI.create("/api/matriculas/" + response.id())).body(response);
  }

  @PutMapping("/{id}")
  public MatriculaResponse actualizar(
      @PathVariable @Positive Long id, @Valid @RequestBody MatriculaRequest request) {
    return service.actualizar(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable @Positive Long id) {
    service.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}
