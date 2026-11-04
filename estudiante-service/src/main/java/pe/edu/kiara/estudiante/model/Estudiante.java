package pe.edu.kiara.estudiante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiantes")
public class Estudiante {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 150)
  private String codigo;

  @Column(nullable = false, length = 150)
  private String nombres;

  @Column(nullable = false, length = 150)
  private String apellidos;

  @Column(nullable = false, length = 150)
  private String correo;

  @Column(nullable = false, length = 150)
  private String estado;

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

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String value) {
    this.nombres = value;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String value) {
    this.apellidos = value;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String value) {
    this.correo = value;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String value) {
    this.estado = value;
  }
}
