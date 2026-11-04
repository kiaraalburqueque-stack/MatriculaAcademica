package pe.edu.kiara.matricula.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "matriculas")
public class Matricula {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long idEstudiante;

  @Column(nullable = false)
  private Long idCurso;

  @Column(nullable = false)
  private LocalDate fecha;

  @Column(nullable = false, length = 150)
  private String estado;

  public Long getId() {
    return id;
  }

  public void setId(Long value) {
    this.id = value;
  }

  public Long getIdEstudiante() {
    return idEstudiante;
  }

  public void setIdEstudiante(Long value) {
    this.idEstudiante = value;
  }

  public Long getIdCurso() {
    return idCurso;
  }

  public void setIdCurso(Long value) {
    this.idCurso = value;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate value) {
    this.fecha = value;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String value) {
    this.estado = value;
  }
}
