# Verificación del proyecto

El proyecto se compiló con JDK 25 y Maven 3.9.11 sobre Windows.

La ejecución automatizada de los tres módulos aprobó 16 pruebas sin errores:

- Estudiante: CRUD completo, datos inválidos, recurso inexistente, JSON incorrecto y código duplicado.
- Curso: CRUD completo, créditos no positivos, recurso inexistente, JSON incorrecto y código duplicado.
- Matrícula: CRUD completo, IDs inválidos, recurso inexistente, JSON incorrecto, referencia inexistente y servicio remoto no disponible.

Las pruebas anteriores usan bases H2 aisladas en memoria; en Matrícula se simula el cliente de referencias.

También se ejecutó una prueba real con MariaDB 10.4.32 incluido en XAMPP, usando una instancia temporal aislada en el puerto 33307 y los tres servicios en los puertos 18081, 18082 y 18083. Se importó el mismo script SQL entregado y se utilizó el perfil dev con sus conexiones adaptadas mediante variables de entorno.

Se aprobaron 20 operaciones HTTP: el flujo estudiante → curso → matrícula → listado; consultas y actualizaciones; errores 400, 404 y 409; conservación de la matrícula después de reiniciar su servicio; respuesta 503 al detener Curso; y eliminación de los registros creados. Las solicitudes y respuestas reales están en `resultado-integracion.json`. Las instancias temporales se detuvieron al finalizar y no se modificaron las bases existentes del usuario.

Esta verificación se realizó con un cliente HTTP automatizado, no desde la interfaz de Postman. La colección de Postman y las capturas para sustentación se deben ejecutar después de importar el SQL en la instancia habitual de XAMPP e iniciar las tres aplicaciones.

Para repetir la verificación:

```powershell
.\mvnw.cmd clean verify
```

Los reportes se generan en `target/surefire-reports` dentro de cada módulo y no se publican en Git.
