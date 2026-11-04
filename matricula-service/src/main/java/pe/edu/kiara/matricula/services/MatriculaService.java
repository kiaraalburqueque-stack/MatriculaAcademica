package pe.edu.kiara.matricula.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.kiara.matricula.dto.*;
import pe.edu.kiara.matricula.exception.RecursoNoEncontradoException;
import pe.edu.kiara.matricula.model.Matricula;
import pe.edu.kiara.matricula.repository.MatriculaRepository;

@Service
public class MatriculaService {
  private final MatriculaRepository repository;
  private final ReferenciasClient referencias;

  public MatriculaService(MatriculaRepository repository, ReferenciasClient referencias) {
    this.repository = repository;
    this.referencias = referencias;
  }

  @Transactional(readOnly = true)
  public List<MatriculaResponse> listar() {
    return repository.findAll().stream().map(this::response).toList();
  }

  @Transactional(readOnly = true)
  public MatriculaResponse buscar(Long id) {
    return response(encontrar(id));
  }

  @Transactional
  public MatriculaResponse crear(MatriculaRequest request) {
    referencias.validar(request.idEstudiante(), request.idCurso());
    var entity = new Matricula();
    copiar(entity, request);
    return response(repository.saveAndFlush(entity));
  }

  @Transactional
  public MatriculaResponse actualizar(Long id, MatriculaRequest request) {
    var entity = encontrar(id);
    referencias.validar(request.idEstudiante(), request.idCurso());
    copiar(entity, request);
    return response(repository.saveAndFlush(entity));
  }

  @Transactional
  public void eliminar(Long id) {
    repository.delete(encontrar(id));
    repository.flush();
  }

  private Matricula encontrar(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("Matricula no encontrado: " + id));
  }

  private void copiar(Matricula entity, MatriculaRequest request) {
    entity.setIdEstudiante(request.idEstudiante());
    entity.setIdCurso(request.idCurso());
    entity.setFecha(request.fecha());
    entity.setEstado(request.estado());
  }

  private MatriculaResponse response(Matricula entity) {
    return new MatriculaResponse(
        entity.getId(),
        entity.getIdEstudiante(),
        entity.getIdCurso(),
        entity.getFecha(),
        entity.getEstado());
  }
}
