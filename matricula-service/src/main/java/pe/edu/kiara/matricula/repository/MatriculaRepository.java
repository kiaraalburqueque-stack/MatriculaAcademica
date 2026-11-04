package pe.edu.kiara.matricula.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.kiara.matricula.model.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {}
