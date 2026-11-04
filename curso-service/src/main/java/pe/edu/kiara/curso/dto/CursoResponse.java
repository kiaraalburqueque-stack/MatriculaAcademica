package pe.edu.kiara.curso.dto;


public record CursoResponse(
    Long id, String codigo, String nombre, Integer creditos, Integer vacantes) {}
