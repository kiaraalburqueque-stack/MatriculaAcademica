package pe.edu.kiara.curso.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class Curso {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 150)
  private String codigo;

  @Column(nullable = false, length = 150)
  private String nombre;

  @Column(nullable = false)
  private Integer creditos;

  @Column(nullable = false)
  private Integer vacantes;

  public Long getId() {
    return id;
  }

  public void setId(Long value) {
    this.id = value;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String value) {
    this.codigo = value;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String value) {
    this.nombre = value;
  }

  public Integer getCreditos() {
    return creditos;
  }

  public void setCreditos(Integer value) {
    this.creditos = value;
  }

  public Integer getVacantes() {
    return vacantes;
  }

  public void setVacantes(Integer value) {
    this.vacantes = value;
  }
}
