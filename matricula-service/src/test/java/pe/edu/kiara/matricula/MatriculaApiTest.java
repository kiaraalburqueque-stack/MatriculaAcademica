package pe.edu.kiara.matricula;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pe.edu.kiara.matricula.services.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MatriculaApiTest {
  @Autowired MockMvc mvc;
  @org.springframework.test.context.bean.override.mockito.MockitoBean ReferenciasClient referencias;

  @Test
  void crudCompleto() throws Exception {
    String body =
        "{\"idEstudiante\": 1, \"idCurso\": 1, \"fecha\": \"2026-11-04\", \"estado\": \"ACTIVA\"}";
    mvc.perform(post("/api/matriculas").contentType("application/json").content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1));
    mvc.perform(get("/api/matriculas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
    mvc.perform(get("/api/matriculas/1")).andExpect(status().isOk());
    mvc.perform(put("/api/matriculas/1").contentType("application/json").content(body))
        .andExpect(status().isOk());
    mvc.perform(delete("/api/matriculas/1")).andExpect(status().isNoContent());
    mvc.perform(get("/api/matriculas/1")).andExpect(status().isNotFound());
  }

  @Test
  void rechazaDatosInvalidos() throws Exception {
    mvc.perform(
            post("/api/matriculas")
                .contentType("application/json")
                .content(
                    "{\"idEstudiante\": 0, \"idCurso\": 1, \"fecha\": \"2026-11-04\", \"estado\":"
                        + " \"ACTIVA\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void recursoInexistente() throws Exception {
    mvc.perform(get("/api/matriculas/999999")).andExpect(status().isNotFound());
  }

  @Test
  void jsonIncorrecto() throws Exception {
    mvc.perform(post("/api/matriculas").contentType("application/json").content("{"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void referenciaInexistente() throws Exception {
    org.mockito.Mockito.doThrow(
            new pe.edu.kiara.matricula.exception.RecursoNoEncontradoException(
                "Estudiante no encontrado"))
        .when(referencias)
        .validar(1L, 1L);
    mvc.perform(
            post("/api/matriculas")
                .contentType("application/json")
                .content(
                    "{\"idEstudiante\": 1, \"idCurso\": 1, \"fecha\": \"2026-11-04\", \"estado\":"
                        + " \"ACTIVA\"}"))
        .andExpect(status().isNotFound());
  }

  @Test
  void servicioNoDisponible() throws Exception {
    org.mockito.Mockito.doThrow(
            new org.springframework.web.client.ResourceAccessException("Sin conexión"))
        .when(referencias)
        .validar(1L, 1L);
    mvc.perform(
            post("/api/matriculas")
                .contentType("application/json")
                .content(
                    "{\"idEstudiante\": 1, \"idCurso\": 1, \"fecha\": \"2026-11-04\", \"estado\":"
                        + " \"ACTIVA\"}"))
        .andExpect(status().isServiceUnavailable());
  }
}
