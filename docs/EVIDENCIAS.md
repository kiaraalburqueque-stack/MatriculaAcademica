# Registro de evidencias

Estudiante: Kiara Paola Alburqueque Arzapalo. Completar la sección y adjuntar capturas reales después de ejecutar el proyecto.

## Capturas que se deben obtener

| Evidencia | Acción | Qué debe verse |
|---|---|---|
| 01 | Iniciar tres servicios | Consolas con puertos diferentes |
| 02 | Revisar phpMyAdmin | Tres bases y sus tablas |
| 03 | Crear estudiante | URL, cuerpo enviado, respuesta 201 e ID |
| 04 | Crear curso | URL, cuerpo enviado, respuesta 201 e ID |
| 05 | Crear matrícula | IDs reales, fecha, estado y respuesta 201 |
| 06 | Listar matrículas | Respuesta 200 con matrícula registrada |
| 07 | Enviar curso con créditos 0 | Respuesta 400 y campo creditos |
| 08 | Consultar estudiante inexistente | Respuesta 404 y mensaje |
| 09 | Repetir código de estudiante | Respuesta 409 |
| 10 | Ejecutar colección completa | Resumen de aserciones aprobadas |

Las solicitudes de actualización y eliminación también están incluidas en Postman. Si necesitas evidencia visual de cada operación, captura sus respuestas 200 y 204. Para demostrar persistencia, registra datos, reinicia el servicio y consulta el mismo ID: debe seguir existiendo en MySQL.

Para verificar 503, detén temporalmente Curso, intenta crear una matrícula con un estudiante existente y un curso previamente creado, y comprueba la respuesta. Vuelve a iniciar Curso después de la prueba.

No muestres contraseñas ni variables con credenciales. No confundas las pruebas automáticas en H2 con evidencia de persistencia en MySQL. Guarda las capturas en una subcarpeta `evidencias/` si el docente las solicita; la colección permite reproducir las pruebas.
