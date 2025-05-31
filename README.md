# EntrenaSync Worker Microservice

## Descripción

Este microservicio forma parte del ecosistema **EntrenaSync** y se encarga de la gestión completa de trabajadores del sistema. Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para el manejo de trabajadores y sus tipos asociados, incluyendo información personal, profesional y de servicios.

## Tecnologías Utilizadas

-   **Kotlin** - Lenguaje de programación principal
-   **Spring Boot 3.x** - Framework de aplicación
-   **Spring Web** - Para la creación de API REST
-   **Spring Data MongoDB** - Integración con base de datos MongoDB
-   **MongoDB** - Base de datos NoSQL para persistencia
-   **Jakarta Validation** - Validación de datos de entrada
-   **Maven** - Gestión de dependencias y construcción del proyecto

## Características Principales

### Gestión de Trabajadores

-   ✅ Creación de nuevos trabajadores
-   ✅ Consulta de trabajadores por ID y nombre
-   ✅ Listado paginado de trabajadores
-   ✅ Actualización de información de trabajadores
-   ✅ Eliminación de trabajadores
-   ✅ Validación completa de datos de entrada
-   ✅ Manejo global de excepciones

### Modelos de Datos

-   **Worker**: Información completa del trabajador (datos personales, profesionales, servicios)
-   **WorkerType**: Categorización de tipos de trabajadores

## Estructura del Proyecto

```
src/main/kotlin/entrenasync/dev/entrenasyncworkermicroservice/
├── Controllers/
│   └── WorkerController.kt          # Endpoints REST
├── Dto/
│   ├── WorkerCreateRequest.kt       # DTO para creación
│   ├── WorkerUpdateRequest.kt       # DTO para actualización
│   └── WorkerResponse.kt            # DTO de respuesta
├── Models/
│   ├── Worker.kt                    # Entidad principal
│   └── WorkerType.kt               # Entidad de tipo de trabajador
├── Services/
│   ├── IWorkerService.kt           # Interfaz del servicio
│   └── WorkerService.kt            # Implementación del servicio
├── Repositories/
│   ├── IWorkerRepository.kt        # Repositorio de trabajadores
│   └── IWorkerTypeRepository.kt    # Repositorio de tipos
├── Mappers/
│   └── WorkerMappers.kt            # Transformadores de datos
├── Exceptions/
│   └── WorkerExceptions.kt         # Excepciones personalizadas
└── ExceptionHandler/
    └── GlobalExceptionHandler.kt   # Manejo global de errores
```

## API Endpoints

### Workers

| Método   | Endpoint               | Descripción                               |
| -------- | ---------------------- | ----------------------------------------- |
| `GET`    | `/workers`             | Obtener todos los trabajadores (paginado) |
| `GET`    | `/workers/{id}`        | Obtener trabajador por ID                 |
| `GET`    | `/workers/name/{name}` | Buscar trabajador por nombre              |
| `POST`   | `/workers`             | Crear nuevo trabajador                    |
| `PUT`    | `/workers/{id}`        | Actualizar trabajador existente           |
| `DELETE` | `/workers/{id}`        | Eliminar trabajador                       |

## Configuración

### Variables de Entorno

#### Desarrollo (application-dev.properties)

-   `MONGO_HOST`: Host de MongoDB (default: worker-microservice-mongo-db)
-   `MONGO_PORT`: Puerto de MongoDB (default: 27017)
-   `MONGO_DB`: Nombre de la base de datos (default: worker)
-   `DATABASE_USER`: Usuario de MongoDB (default: admin)
-   `DATABASE_PASSWORD`: Contraseña de MongoDB (default: adminPassword123)

#### Producción (application-prod.properties)

-   `MONGO_URI`: URI de conexión a MongoDB Atlas
-   `MONGO_DB`: Nombre de la base de datos (default: worker)

### Profiles de Spring

-   **dev**: Configuración para desarrollo local
-   **prod**: Configuración para producción

## Instalación y Ejecución

### Prerrequisitos

-   Java 17 o superior
-   Maven 3.6+
-   MongoDB (local o remoto)

## Soporte

Para soporte técnico o preguntas, contacta con el equipo de desarrollo de EntrenaSync.
