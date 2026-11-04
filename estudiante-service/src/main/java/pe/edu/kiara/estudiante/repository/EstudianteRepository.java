package pe.edu.kiara.estudiante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.kiara.estudiante.model.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {}
