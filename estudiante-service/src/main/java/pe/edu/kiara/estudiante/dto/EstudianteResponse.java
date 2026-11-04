package pe.edu.kiara.estudiante.dto;


public record EstudianteResponse(
    Long id, String codigo, String nombres, String apellidos, String correo, String estado) {}
