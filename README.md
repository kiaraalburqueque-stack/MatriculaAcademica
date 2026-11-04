# Sistema de matrícula académica

Laboratorio 02 · Evaluación 03 · Desarrollo de aplicaciones Web Avanzado

**Estudiante:** Kiara Paola Alburqueque Arzapalo

**Sección:** completar antes de entregar.

Tres aplicaciones Spring Boot independientes permiten administrar estudiantes, cursos y matrículas. Cada una persiste exclusivamente en su propia base de datos. No se requiere interfaz web: la demostración se realiza con Postman.

## Requisitos

- JDK 17 o superior compatible con Spring Boot 3.5.14; se recomienda JDK 21 o 25.
- IntelliJ IDEA con soporte Java y Maven.
- XAMPP con MySQL iniciado. Las distribuciones de XAMPP que incluyen MariaDB también pueden utilizarse con el protocolo MySQL y el script incluido.
- Postman y acceso a Internet para descargar dependencias la primera vez.
- Maven Wrapper incluido: no es necesario instalar Maven por separado.

La base del proyecto y el Wrapper se obtuvieron de Spring Initializr (`https://start.spring.io/`) y se adaptaron a tres módulos Maven con Spring Boot 3.5.14 y Java 17. La versión se fija en el POM principal para que los tres servicios usen las mismas dependencias. No se necesita Lombok.

## Servicios

| Módulo | Clase principal | Puerto | Base de datos | Recurso |
|---|---|---|---|---|
| estudiante-service | EstudianteApplication | 8081 | bd_estudiante | /api/estudiantes |
| curso-service | CursoApplication | 8082 | bd_curso | /api/cursos |
| matricula-service | MatriculaApplication | 8083 | bd_matricula | /api/matriculas |

El puerto de MySQL es independiente del puerto HTTP: por defecto los tres servicios acceden al servidor MySQL en `localhost:3306`, cada uno con un nombre de base diferente.

## Preparar MySQL en XAMPP

1. Abre XAMPP Control Panel e inicia **MySQL**. Inicia también **Apache** para utilizar phpMyAdmin.
2. Abre `http://localhost/phpmyadmin`. Si Apache usa otro puerto, adapta la dirección.
3. Entra en **Importar**, selecciona `database/01_crear_bases_y_tablas.sql` y pulsa **Importar/Continuar**. También puedes pegar todo su contenido en la pestaña **SQL** del servidor y ejecutarlo.
4. Comprueba que aparezcan `bd_estudiante`, `bd_curso` y `bd_matricula`, con las tablas `estudiantes`, `cursos` y `matriculas` respectivamente.
5. El perfil `dev` usa `root` sin contraseña por defecto para un XAMPP local sin personalizar. Si tu instalación tiene otra configuración, establece `DB_USER`, `DB_PASSWORD` y, si corresponde, `DB_PORT` en cada configuración de ejecución de IntelliJ. No es necesario cambiar el código.

El script no borra datos y puede ejecutarse varias veces. `CREATE TABLE IF NOT EXISTS` no modifica tablas antiguas incompatibles: utiliza bases nuevas para este laboratorio. Hibernate tiene `ddl-auto: validate`: comprueba el esquema al arrancar y no crea ni borra tablas automáticamente.

## Abrir y ejecutar en IntelliJ

1. Selecciona **File → Open** y abre la carpeta que contiene este README y el `pom.xml` principal. Si IntelliJ pregunta, ábrelo como proyecto Maven.
2. Configura **Project SDK** con un JDK 17 o superior compatible. Si no tienes uno, IntelliJ permite descargarlo en **Project Structure → SDKs**. El proyecto se verificó con JDK 25.
3. Espera que Maven termine de descargar las dependencias; usa **Reload All Maven Projects** si es necesario.
4. Crea las bases y tablas con el script anterior.
5. Ejecuta `EstudianteApplication`, después `CursoApplication` y finalmente `MatriculaApplication` usando el triángulo verde junto a `main`. También se incluyen configuraciones compartidas en `.run`.
6. Mantén las tres aplicaciones funcionando simultáneamente. Comprueba que las consolas indiquen los puertos 8081, 8082 y 8083.
7. Abre `http://localhost:8081/api/estudiantes`, `http://localhost:8082/api/cursos` y `http://localhost:8083/api/matriculas`. Al inicio deben devolver `[]`.

La dirección raíz `/` no es una página web y puede devolver 404. Utiliza las rutas `/api/...` indicadas.

### Alternativa desde PowerShell

Desde la raíz del proyecto:

```powershell
.\mvnw.cmd clean verify
```

Después abre tres terminales, todas en la raíz, y ejecuta una orden en cada una:

```powershell
java -jar estudiante-service/target/estudiante-service-1.0.0.jar
java -jar curso-service/target/curso-service-1.0.0.jar
java -jar matricula-service/target/matricula-service-1.0.0.jar
```

Para detener cada servicio, usa el botón Stop de IntelliJ o Ctrl+C en su terminal. MySQL debe seguir iniciado mientras trabajas.

## Arquitectura del código

Cada servicio contiene los paquetes `model`, `dto`, `repository`, `services`, `controller` y `exception`.

- `model`: entidades JPA con ID autogenerado y columnas de su tabla.
- `dto`: records de solicitud con validaciones y respuestas independientes de las entidades.
- `repository`: interfaces que extienden `JpaRepository` para persistir y consultar.
- `services`: reglas, conversiones entre DTO y entidad y transacciones.
- `controller`: expone HTTP y devuelve 201 con cabecera Location al crear.
- `exception`: errores JSON uniformes mediante `@RestControllerAdvice`.

Matrícula guarda `idEstudiante` e `idCurso` como números. Su `ReferenciasClient` consulta por HTTP los otros servicios antes de registrar o actualizar. No existen `@ManyToOne` ni claves foráneas entre las bases de datos. Las llamadas tienen tiempos límite de conexión y lectura de 3 segundos.

Esta comprobación de existencia no es una transacción distribuida. Eliminar posteriormente un estudiante o curso no elimina sus matrículas; estas conservan los IDs. No se implementan reservas de vacantes, descuentos automáticos, bloqueo de duplicados de matrícula ni sincronización de eliminaciones, porque no forman parte de los requisitos mínimos. `vacantes` es un dato del catálogo. Para un sistema real se necesitarían reglas adicionales de consistencia.

## Endpoints

Se implementa CRUD completo en los tres servicios, incluyendo matrícula como complemento al registro y listado solicitados.

| Método | Ruta relativa a cada recurso | Resultado |
|---|---|---|
| GET | /api/estudiantes, /api/cursos o /api/matriculas | 200, lista |
| GET | /api/{recurso}/{id} | 200 o 404 |
| POST | /api/{recurso} | 201 y recurso creado |
| PUT | /api/{recurso}/{id} | 200, reemplazo de todos los campos editables |
| DELETE | /api/{recurso}/{id} | 204 sin cuerpo o 404 |

### Registrar estudiante

`POST http://localhost:8081/api/estudiantes`

```json
{
  "codigo": "EST001",
  "nombres": "Kiara Paola",
  "apellidos": "Alburqueque Arzapalo",
  "correo": "kiara@example.com",
  "estado": "ACTIVO"
}
```

### Registrar curso

`POST http://localhost:8082/api/cursos`

```json
{
  "codigo": "CUR001",
  "nombre": "Desarrollo de aplicaciones Web",
  "creditos": 4,
  "vacantes": 30
}
```

### Registrar matrícula

`POST http://localhost:8083/api/matriculas`

```json
{
  "idEstudiante": 1,
  "idCurso": 1,
  "fecha": "2026-11-04",
  "estado": "ACTIVA"
}
```

Sustituye los IDs por los realmente devueltos al crear estudiante y curso. Los IDs no tienen por qué empezar en 1. La fecha usa formato `AAAA-MM-DD`; utiliza la fecha correspondiente a tu prueba. Finalmente ejecuta `GET http://localhost:8083/api/matriculas`.

## Validaciones y errores

- Textos obligatorios con `@NotBlank` y límites de longitud.
- Correo con `@Email` y `@NotBlank`.
- Estudiante: estado `ACTIVO` o `INACTIVO` y código único.
- Curso: créditos enteros mayores que cero, vacantes enteras mayores o iguales a cero y código único.
- Matrícula: IDs positivos, fecha obligatoria y estado `ACTIVA` o `ANULADA`.
- En PUT se requieren todos los campos, igual que en POST.
- 400 para validaciones, fechas, IDs o JSON incorrectos.
- 404 para registros inexistentes o referencias inexistentes al matricular.
- 409 para códigos duplicados o restricciones de integridad.
- 503 si Matrícula no puede comunicarse con Estudiante o Curso.

Ejemplo de estructura de error (el mensaje de cada campo puede variar según el idioma del validador):

```json
{
  "timestamp": "2026-11-04T15:00:00Z",
  "status": 400,
  "mensaje": "Revise los campos enviados",
  "ruta": "/api/cursos",
  "campos": { "creditos": "must be greater than 0" }
}
```

## Perfiles dev y prod

| Valor | dev, predeterminado | prod |
|---|---|---|
| Conexión | localhost:3306 y base por servicio | variable DB_URL obligatoria por servicio |
| Usuario | DB_USER o root | DB_USER obligatorio |
| Contraseña | DB_PASSWORD o vacío | DB_PASSWORD obligatorio |
| Esquema | validate | validate |
| Consultas SQL en consola | activadas | desactivadas |
| Puerto HTTP | SERVER_PORT o puerto del servicio | SERVER_PORT o puerto del servicio |

Activa producción con `SPRING_PROFILES_ACTIVE=prod`. Cada proceso necesita su propia `DB_URL`, por ejemplo una conexión a `bd_estudiante` para Estudiante. Configura la conexión segura y los permisos apropiados para el servidor de destino. El perfil es una configuración preparada, no un despliegue realizado.

Variables adicionales de Matrícula: `ESTUDIANTE_URL` y `CURSO_URL`, por defecto `http://localhost:8081` y `http://localhost:8082`. Si cambias un puerto de esos servicios, actualiza también estas variables y las variables de la colección Postman.

No publiques archivos `.env`, contraseñas, capturas con credenciales ni datos personales de terceros. Las direcciones `example.com` del proyecto son datos de ejemplo.

## Postman y evidencias

1. Inicia MySQL y los tres servicios.
2. Importa `postman/MatriculaAcademica.postman_collection.json`.
3. Ejecuta la colección en orden con **Collection Runner**. Usa una iteración y, si repites, deja al menos un segundo entre ejecuciones para los códigos basados en fecha y hora.
4. Las variables de colección guardan automáticamente los IDs. No necesitas un environment separado.
5. Comprueba los resultados de las aserciones y toma las capturas señaladas en `docs/EVIDENCIAS.md`.

La colección registra estudiante y curso, crea y lista matrícula, consulta y actualiza los tres recursos, comprueba errores 400, 404 y 409 y elimina al final solamente los registros creados durante esa ejecución. Para fotografiar los datos en MySQL, ejecuta las solicitudes individualmente y toma las capturas antes de las tres eliminaciones finales.

Las pruebas automáticas (`mvnw.cmd test`) usan H2 en memoria y el perfil `test`, sin tocar MySQL. En Matrícula se simulan las respuestas de los otros servicios para comprobar el comportamiento HTTP. La colección Postman verifica la integración real de los tres servicios con MySQL y debe ejecutarse aparte.

## Entregables y sustentación

- Código fuente: los tres módulos Maven.
- Bases de datos: `database/01_crear_bases_y_tablas.sql`.
- Diagrama: `docs/ARQUITECTURA.md` y `docs/arquitectura.svg`.
- Pruebas: colección Postman y guía `docs/EVIDENCIAS.md`.
- Cinco conclusiones y preparación individual: `docs/SUSTENTACION.md`.

La sección y las capturas de tu ejecución deben completarse antes de entregar. No se incluyen capturas inventadas.

## Problemas frecuentes

- **Access denied for user**: revisa `DB_USER` y `DB_PASSWORD` en la configuración de ejecución.
- **Communications link failure**: inicia MySQL y comprueba `DB_PORT` en XAMPP.
- **Unknown database** o **Schema-validation missing table**: importa el script SQL completo.
- **Port already in use**: detén la ejecución anterior o cambia `SERVER_PORT` y las URLs correspondientes.
- **503 al matricular**: inicia Estudiante y Curso antes de Matrícula y revisa sus URLs.
- **404 al matricular**: utiliza los IDs reales de las respuestas de creación.
- **Errores de JDK o Maven**: revisa el Project SDK y el JDK del Maven Runner; ambos deben ser compatibles.
- **404 en el navegador al abrir localhost:8081/**: utiliza `/api/estudiantes`.

Referencia técnica: [requisitos de Spring Boot](https://docs.spring.io/spring-boot/3.5/system-requirements.html).
