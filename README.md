Sistema de Registro Universitario - API REST
📌 Descripción
API REST para el sistema de registro universitario con autenticación JWT y control de acceso basado en roles. 

🛠 Tecnologías
Spring Boot 3.4.3

PostgreSQL (Base de datos)

Spring Security (Autenticación y autorización)

JWT (JSON Web Tokens)

SpringDoc OpenAPI (Documentación)

Lombok (Reducción de código boilerplate)

Spring Data JPA (Acceso a datos)

Spring Session (Gestión de sesiones)

🔧 Configuración
Requisitos
Java 21

PostgreSQL 15+

Maven 3.9+

Archivo application.properties
properties
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

bash
mvn spring-boot:run

Accede a la documentación:

Interfaz Swagger UI: http://localhost:8086/swagger-ui.html

OpenAPI JSON: http://localhost:8086/v3/api-docs

Autenticación:

Primero obtén un token JWT con:

POST /api/auth/login
Body: {"username":"admin", "password":"admin123"}
Usa el token en requests protegidas con el header:

Authorization: Bearer <tu_token_jwt>
📚 Dependencias Principales (pom.xml)
xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- PostgreSQL -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    
    <!-- SpringDoc OpenAPI -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.8.8</version>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
📝 Configuración OpenAPI
java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
            .info(new Info()
                .title("API Registro Universitario")
                .version("1.0")
                .description("Documentación de la API"))
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            );
    }
}
