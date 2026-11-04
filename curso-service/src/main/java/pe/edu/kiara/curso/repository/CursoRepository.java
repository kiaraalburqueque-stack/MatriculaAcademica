package pe.edu.kiara.curso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.kiara.curso.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {}
