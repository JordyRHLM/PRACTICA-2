# 🎓 Sistema de Registro Universitario - API REST

## 📌 Descripción
API REST para el sistema de registro universitario con autenticación JWT y control de acceso basado en roles. Documentada con Swagger/OpenAPI.

---

## 🛠 Tecnologías

- **Spring Boot 3.4.3**
- **PostgreSQL** (Base de datos)
- **Spring Security** (Autenticación y autorización)
- **JWT** (JSON Web Tokens)
- **SpringDoc OpenAPI** (Documentación)
- **Lombok** (Reducción de código boilerplate)
- **Spring Data JPA** (Acceso a datos)
- **Spring Session** (Gestión de sesiones)

---

## 🔧 Configuración

### Requisitos

- Java 21
- PostgreSQL 15+
- Maven 3.9+

### Archivo `application.properties`

```properties
# Puerto del servidor
server.port=8086

# Configuración de PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/universidad
spring.datasource.username=postgres
spring.datasource.password=POSTGRES
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración Swagger/OpenAPI
springdoc.api-docs.enabled=true
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.operationsSorter=alpha
springdoc.swagger-ui.filter=true

🔐 Autenticación y Roles
Roles del sistema
Rol	Descripción
Administrador	Acceso completo al sistema. Gestiona usuarios y roles.
Docente	Visualiza estudiantes inscritos y puede participar en evaluaciones.
Estudiante	Puede inscribirse en materias y ver su información académica.

Acceso a Endpoints
🔓 Públicos (sin autenticación)
/swagger-ui/** - Interfaz Swagger UI

/v3/api-docs/** - Documentación OpenAPI (JSON)

/api/auth/** - Autenticación (login, registro)

/api/public/** - Recursos públicos

🔐 Solo ADMIN
/api/admin/** - Funcionalidades administrativas

👨‍🏫 ADMIN y DOCENTE
/api/docentes/** - Gestión de docentes

/api/inscripciones/** - Gestión de inscripciones

👨‍🎓 ADMIN, DOCENTE y ESTUDIANTE
/api/estudiantes/** - Información de estudiantes

/api/materias/** - Consulta de materias (creación solo ADMIN)
