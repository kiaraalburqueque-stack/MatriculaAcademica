package pe.edu.kiara.estudiante.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.kiara.estudiante.dto.*;
import pe.edu.kiara.estudiante.exception.RecursoNoEncontradoException;
import pe.edu.kiara.estudiante.model.Estudiante;
import pe.edu.kiara.estudiante.repository.EstudianteRepository;

@Service
public class EstudianteService {
  private final EstudianteRepository repository;

  public EstudianteService(EstudianteRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public List<EstudianteResponse> listar() {
    return repository.findAll().stream().map(this::response).toList();
  }

  @Transactional(readOnly = true)
  public EstudianteResponse buscar(Long id) {
    return response(encontrar(id));
  }

  @Transactional
  public EstudianteResponse crear(EstudianteRequest request) {
    var entity = new Estudiante();
    copiar(entity, request);
    return response(repository.saveAndFlush(entity));
  }

  @Transactional
  public EstudianteResponse actualizar(Long id, EstudianteRequest request) {
    var entity = encontrar(id);
    copiar(entity, request);
    return response(repository.saveAndFlush(entity));
  }

  @Transactional
  public void eliminar(Long id) {
    repository.delete(encontrar(id));
    repository.flush();
  }

  private Estudiante encontrar(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado: " + id));
  }

  private void copiar(Estudiante entity, EstudianteRequest request) {
    entity.setCodigo(request.codigo());
    entity.setNombres(request.nombres());
    entity.setApellidos(request.apellidos());
    entity.setCorreo(request.correo());
    entity.setEstado(request.estado());
  }

  private EstudianteResponse response(Estudiante entity) {
    return new EstudianteResponse(
        entity.getId(),
        entity.getCodigo(),
        entity.getNombres(),
        entity.getApellidos(),
        entity.getCorreo(),
        entity.getEstado());
  }
}
