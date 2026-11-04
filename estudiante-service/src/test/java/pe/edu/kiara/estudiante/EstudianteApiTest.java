package pe.edu.kiara.estudiante;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pe.edu.kiara.estudiante.services.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EstudianteApiTest {
  @Autowired MockMvc mvc;

  @Test
  void crudCompleto() throws Exception {
    String body =
        "{\"codigo\": \"E001\", \"nombres\": \"Kiara Paola\", \"apellidos\": \"Alburqueque"
            + " Arzapalo\", \"correo\": \"kiara@example.com\", \"estado\": \"ACTIVO\"}";
    mvc.perform(post("/api/estudiantes").contentType("application/json").content(body))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1));
    mvc.perform(get("/api/estudiantes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
    mvc.perform(get("/api/estudiantes/1")).andExpect(status().isOk());
    mvc.perform(put("/api/estudiantes/1").contentType("application/json").content(body))
        .andExpect(status().isOk());
    mvc.perform(delete("/api/estudiantes/1")).andExpect(status().isNoContent());
    mvc.perform(get("/api/estudiantes/1")).andExpect(status().isNotFound());
  }

  @Test
  void rechazaDatosInvalidos() throws Exception {
    mvc.perform(
            post("/api/estudiantes")
                .contentType("application/json")
                .content(
                    "{\"codigo\": \"\", \"nombres\": \"Kiara Paola\", \"apellidos\": \"Alburqueque"
                        + " Arzapalo\", \"correo\": \"kiara@example.com\", \"estado\":"
                        + " \"ACTIVO\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void recursoInexistente() throws Exception {
    mvc.perform(get("/api/estudiantes/999999")).andExpect(status().isNotFound());
  }

  @Test
  void jsonIncorrecto() throws Exception {
    mvc.perform(post("/api/estudiantes").contentType("application/json").content("{"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void codigoDuplicado() throws Exception {
    String body =
        "{\"codigo\": \"E001\", \"nombres\": \"Kiara Paola\", \"apellidos\": \"Alburqueque"
            + " Arzapalo\", \"correo\": \"kiara@example.com\", \"estado\": \"ACTIVO\"}";
    mvc.perform(post("/api/estudiantes").contentType("application/json").content(body))
        .andExpect(status().isCreated());
    mvc.perform(post("/api/estudiantes").contentType("application/json").content(body))
        .andExpect(status().isConflict());
  }
}
