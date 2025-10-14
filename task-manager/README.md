# Task Manager (Java + Swing) — Creado por Juan David Ocaño Huertas

## Resumen
Aplicación de escritorio **Java (Swing)** para gestionar tareas con prioridad y persistencia en **MySQL** ejecutado con **Docker Compose**. Ideal para entregar a un docente: incluye código listo, Docker, SQL de ejemplo y un README detallado.

## Contenido del repositorio
- `docker-compose.yml` — Levanta MySQL 8.0.
- `pom.xml` — Proyecto Maven (Java 17).
- `src/main/java/...` — Código fuente Java (GUI con Swing, DAO y conexión JDBC).
- `src/main/resources/db.properties` — Configuración de conexión a la BD.
- `sql/schema.sql` — Script SQL con la estructura de la tabla `tasks`.
- `README.md` — Este archivo.

## Requisitos
- Docker y Docker Compose instalados.
- Java JDK 17 o superior.
- Maven 3.6+.

## Cómo ejecutar (paso a paso)
1. Abrir terminal en la raíz del proyecto (`task-manager`).
2. Levantar MySQL en Docker:
   ```bash
   docker compose up -d
   ```
3. (Opcional) Verificar contenedor:
   ```bash
   docker ps
   ```
4. Compilar el proyecto:
   ```bash
   mvn clean compile
   ```
5. Ejecutar la aplicación:
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.taskmanager.App"
   ```
   - También puedes ejecutar el `jar` si lo empaquetas.

## Uso de la aplicación
La interfaz es simple y responde a acciones:
- **Agregar tarea**: introduce título, descripción y prioridad.
- **Marcar como completada**: selecciona una tarea y pulsa "Marcar completada".
- **Eliminar tarea**: selecciona y pulsa "Eliminar".
- **Refrescar**: recarga la lista desde la base de datos.

## Configuración de la BD
Archivo: `src/main/resources/db.properties`
```properties
db.url=jdbc:mysql://localhost:3306/taskdb?serverTimezone=UTC&useSSL=false
db.user=user
db.password=user123
```

## SQL (esquema)
`sql/schema.sql` contiene:
```sql
CREATE DATABASE IF NOT EXISTS taskdb;
USE taskdb;

CREATE TABLE IF NOT EXISTS tasks (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(150) NOT NULL,
  description TEXT,
  priority VARCHAR(20),
  completed BOOLEAN DEFAULT FALSE
);
```

## Notas para el profesor
- El proyecto crea la tabla automáticamente al arrancar si no existe.
- Si el puerto `3306` está en uso en la máquina del evaluador, cambiar el mapeo de puertos en `docker-compose.yml` o `db.properties` a la IP/puerto correspondiente.

## Extensiones sugeridas (puntos extra)
- Añadir búsqueda y filtrado por prioridad.
- Exportar tareas a CSV.
- Re-escribir como API REST con Spring Boot.

## Autor
**Juan David Ocaño Huertas**
