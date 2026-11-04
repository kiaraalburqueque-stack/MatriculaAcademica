package pe.edu.kiara.curso.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.kiara.curso.dto.*;
import pe.edu.kiara.curso.exception.RecursoNoEncontradoException;
import pe.edu.kiara.curso.model.Curso;
import pe.edu.kiara.curso.repository.CursoRepository;

@Service
public class CursoService {
  private final CursoRepository repository;

  public CursoService(CursoRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public List<CursoResponse> listar() {
    return repository.findAll().stream().map(this::response).toList();
  }

  @Transactional(readOnly = true)
  public CursoResponse buscar(Long id) {
    return response(encontrar(id));
  }

  @Transactional
  public CursoResponse crear(CursoRequest request) {
    var entity = new Curso();
    copiar(entity, request);
    return response(repository.saveAndFlush(entity));
  }

  @Transactional
  public CursoResponse actualizar(Long id, CursoRequest request) {
    var entity = encontrar(id);
    copiar(entity, request);
    return response(repository.saveAndFlush(entity));
  }

  @Transactional
  public void eliminar(Long id) {
    repository.delete(encontrar(id));
    repository.flush();
  }

  private Curso encontrar(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado: " + id));
  }

  private void copiar(Curso entity, CursoRequest request) {
    entity.setCodigo(request.codigo());
    entity.setNombre(request.nombre());
    entity.setCreditos(request.creditos());
    entity.setVacantes(request.vacantes());
  }

  private CursoResponse response(Curso entity) {
    return new CursoResponse(
        entity.getId(),
        entity.getCodigo(),
        entity.getNombre(),
        entity.getCreditos(),
        entity.getVacantes());
  }
}
