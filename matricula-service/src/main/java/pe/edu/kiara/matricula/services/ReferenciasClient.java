package pe.edu.kiara.matricula.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;
import pe.edu.kiara.matricula.exception.RecursoNoEncontradoException;

@Component
public class ReferenciasClient {
  private final RestClient client;
  private final String estudianteUrl;
  private final String cursoUrl;

  public ReferenciasClient(
      @Value("${servicios.estudiante-url}") String estudianteUrl,
      @Value("${servicios.curso-url}") String cursoUrl) {
    var factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(3000);
    factory.setReadTimeout(3000);
    this.client = RestClient.builder().requestFactory(factory).build();
    this.estudianteUrl = estudianteUrl;
    this.cursoUrl = cursoUrl;
  }

  public void validar(Long estudiante, Long curso) {
    verificar(estudianteUrl + "/api/estudiantes/" + estudiante, "Estudiante", estudiante);
    verificar(cursoUrl + "/api/cursos/" + curso, "Curso", curso);
  }

  private void verificar(String url, String tipo, Long id) {
    try {
      client.get().uri(url).retrieve().toBodilessEntity();
    } catch (HttpClientErrorException.NotFound ex) {
      throw new RecursoNoEncontradoException(tipo + " no encontrado: " + id);
    }
  }
}
