package pe.edu.kiara.curso.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pe.edu.kiara.curso.dto.*;
import pe.edu.kiara.curso.services.CursoService;

@RestController
@RequestMapping("/api/cursos")
@Validated
public class CursoController {
  private final CursoService service;

  public CursoController(CursoService service) {
    this.service = service;
  }

  @GetMapping
  public List<CursoResponse> listar() {
    return service.listar();
  }

  @GetMapping("/{id}")
  public CursoResponse buscar(@PathVariable @Positive Long id) {
    return service.buscar(id);
  }

  @PostMapping
  public ResponseEntity<CursoResponse> crear(@Valid @RequestBody CursoRequest request) {
    var response = service.crear(request);
    return ResponseEntity.created(URI.create("/api/cursos/" + response.id())).body(response);
  }

  @PutMapping("/{id}")
  public CursoResponse actualizar(
      @PathVariable @Positive Long id, @Valid @RequestBody CursoRequest request) {
    return service.actualizar(id, request);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable @Positive Long id) {
    service.eliminar(id);
    return ResponseEntity.noContent().build();
  }
}
