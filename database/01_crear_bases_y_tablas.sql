-- Ejecutar en phpMyAdmin, pestaña SQL, con MySQL de XAMPP iniciado.
-- No elimina datos existentes. Cada servicio usa una base independiente.
CREATE DATABASE IF NOT EXISTS bd_estudiante CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_curso CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS bd_matricula CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_estudiante;
CREATE TABLE IF NOT EXISTS estudiantes (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 codigo VARCHAR(150) NOT NULL UNIQUE,
 nombres VARCHAR(150) NOT NULL,
 apellidos VARCHAR(150) NOT NULL,
 correo VARCHAR(150) NOT NULL,
 estado VARCHAR(150) NOT NULL
) ENGINE=InnoDB;
USE bd_curso;
CREATE TABLE IF NOT EXISTS cursos (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 codigo VARCHAR(150) NOT NULL UNIQUE,
 nombre VARCHAR(150) NOT NULL,
 creditos INT NOT NULL,
 vacantes INT NOT NULL,
 CONSTRAINT chk_creditos CHECK (creditos > 0),
 CONSTRAINT chk_vacantes CHECK (vacantes >= 0)
) ENGINE=InnoDB;
USE bd_matricula;
CREATE TABLE IF NOT EXISTS matriculas (
 id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 id_estudiante BIGINT NOT NULL,
 id_curso BIGINT NOT NULL,
 fecha DATE NOT NULL,
 estado VARCHAR(150) NOT NULL
) ENGINE=InnoDB;
-- Sin claves foráneas entre bases: las referencias se verifican por API.
