# Preparación para la sustentación individual

Kiara Paola Alburqueque Arzapalo · Laboratorio 02

## Cinco conclusiones propuestas

Revisa estas conclusiones después de ejecutar el laboratorio y exprésalas con tus propias palabras.

1. Separar Estudiante, Curso y Matrícula permite asignar una responsabilidad clara a cada aplicación y ejecutar sus operaciones desde puertos diferentes.
2. Spring Data JPA permite persistir entidades en MySQL mediante repositorios, mientras cada microservicio conserva la propiedad de su base de datos.
3. Los DTO permiten definir los datos aceptados por la API y aplicar validaciones antes de ejecutar las operaciones de persistencia.
4. Las respuestas HTTP 400, 404, 409 y 503 permiten distinguir datos inválidos, recursos inexistentes, conflictos y servicios no disponibles.
5. Los perfiles dev y prod permiten cambiar conexiones y registros de consola sin modificar las clases Java; Postman permite demostrar las operaciones y repetir el flujo de aceptación.

## Dificultad que debes explicar

Un problema de diseño abordado en este proyecto fue relacionar la matrícula con datos que pertenecen a otras bases sin compartir sus tablas. La solución fue almacenar `idEstudiante` e `idCurso` y consultar las APIs correspondientes antes de guardar. Cuando una referencia no existe se devuelve 404; cuando no se puede consultar el servicio se devuelve 503.

Esto describe una decisión del proyecto. Añade también una dificultad que realmente encuentres durante tu ejecución, su causa y la solución que aplicaste; no afirmes haber experimentado problemas que no ocurrieron.

## Guion de 5 a 8 minutos

1. **Un minuto:** presenta el problema y el diagrama con los tres servicios y bases.
2. **Un minuto:** muestra una entidad, su ID autogenerado, un repositorio y la conexión MySQL.
3. **Un minuto:** explica el DTO, las validaciones, el service y el controller.
4. **Dos minutos:** registra estudiante y curso, crea matrícula y lista las matrículas en Postman.
5. **Un minuto:** demuestra créditos inválidos e ID inexistente; explica el manejo de errores.
6. **Un minuto:** muestra los perfiles y explica una conclusión y una dificultad.

## Respuesta modelo a la pregunta del docente

Separé el sistema en Estudiante, Curso y Matrícula porque cada servicio tiene una responsabilidad propia. Estudiante administra los datos de los alumnos; Curso administra el catálogo académico; Matrícula registra la asociación entre ambos. Cada aplicación tiene su puerto y base de datos.

Los datos se guardan en MySQL mediante entidades JPA y repositorios que extienden JpaRepository. El service aplica las operaciones y utiliza transacciones locales. Matrícula conserva identificadores y verifica sus referencias mediante HTTP, sin consultar directamente las tablas de los otros servicios.

Cuando llega información inválida, el controller valida el DTO con @Valid y el manejador global devuelve 400 con los campos observados. Si no existe un registro, devuelve 404. Un código duplicado produce 409. Si la validación remota no puede realizarse porque otro servicio está detenido, devuelve 503.

## Cambios pequeños que puedes practicar

- Cambiar las vacantes de un curso mediante PUT.
- Añadir un límite de longitud a un campo del DTO.
- Cambiar un puerto y actualizar la URL usada por Matrícula y Postman.
- Explicar por qué GET por ID inexistente devuelve 404.
- Localizar dónde se decide la unicidad del código.

No hay descuento automático de vacantes ni transacciones distribuidas. Explica este alcance con claridad si el docente pregunta por esas reglas.
