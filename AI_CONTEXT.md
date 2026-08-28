# CourseHub Backend - AI Coding Context

## Project Overview
CourseHub Backend is a Spring Boot Maven multi-module backend using a microservices architecture.
Services are intended to register through Eureka and receive external traffic through the API Gateway.

Root modules:
- infra/api-gateway
- infra/eureka-server
- services/communication-service
- services/course-service
- services/enrollment-service
- services/learning-service
- services/payment-service
- services/promotion-service
- services/search-service
- services/user-service

## Architecture Direction
When adding new business code, follow DDD with Clean Architecture / Hexagonal Architecture.

Dependency direction:
- domain: pure Java, no Spring/MyBatis/framework dependency.
- application: depends on domain, contains use cases and application services.
- infrastructure: depends on application/domain, contains adapters such as web, persistence, messaging, and external integrations.

## Persistence Technology
Use MyBatis for persistence. Do not use JPA unless explicitly requested.

Persistence rules:
- Do not create JPA entities with @Entity, @Table, @Column, or JpaRepository.
- MyBatis mapper interfaces belong in infrastructure/persistence/mapper/.
- Persistence models belong in infrastructure/persistence/model/.
- Repository adapters belong in infrastructure/persistence/repository/ and call MyBatis mapper interfaces.
- Domain models remain pure Java and must not be used as MyBatis persistence models.
- MyBatis XML files, when used, should live under src/main/resources/mapper/ unless a service config says otherwise.

## Lombok Usage
Lombok is allowed, but usage depends on the layer.

- infrastructure/persistence/model/
  - Lombok can be used freely.
  - Allowed examples: @Getter, @Setter, @Builder, @NoArgsConstructor, @AllArgsConstructor.

- infrastructure/web/
  - Lombok can be used freely for request/response DTOs.
  - Allowed examples: @Getter, @Setter, @Builder, @NoArgsConstructor, @AllArgsConstructor.

- application/
  - Command/Query objects may use Lombok when useful.
  - Prefer @Getter and @Builder for immutable input objects.
  - Avoid putting business logic in Lombok-generated setters.

- domain/
  - Minimize Lombok usage.
  - If Lombok is used, only @Getter is allowed by default.
  - Do not use @Setter in domain models.
  - Do not use @Data in domain models.
  - Domain objects should be immutable where practical.
  - State changes must go through meaningful business methods.
  - Prefer explicit constructors and static factory methods over @Builder.
  - Example: Course.create(...)
  - Constructors/factories must enforce domain validation and invariants.

## Service Package Structure
Each service should follow this package convention when new code is added:

- domain/
  - Pure Java Entity/Aggregate
  - Value Object
  - Domain Service
  - Repository interface/port
  - Must not import Spring or MyBatis annotations

- application/
  - Use case
  - Application service
  - Command/Query objects when useful
  - Orchestrates domain logic and calls ports

- infrastructure/persistence/model/
  - MyBatis persistence model
  - Maps database schema exactly from migration files
  - Not the same class as the domain model

- infrastructure/persistence/repository/
  - Persistence adapter implementing domain repository ports
  - Calls MyBatis mapper interfaces

- infrastructure/persistence/mapper/
  - MyBatis mapper interfaces
  - Domain <-> persistence model mapper classes when needed

- infrastructure/web/
  - Controller
  - Request/response DTO
  - Web mapper when needed

## Mandatory Rules
- Do not put Spring/MyBatis annotations in domain/.
- Do not use one class as both Domain Model and MyBatis Persistence Model.
- Repository interface/port belongs in domain/.
- Repository implementation/adapter belongs in infrastructure/persistence/.
- MyBatis mapper interface belongs in infrastructure/persistence/mapper/.
- Persistence model belongs in infrastructure/persistence/model/.
- Domain <-> persistence model conversion must use a separate mapper.
- Database names use snake_case.
- Java fields/methods use camelCase.
- Java classes use PascalCase.
- When creating persistence models from migrations, map only columns that exist in the migration.
- Do not invent additional fields, columns, relationships, or constraints.
- Do not create a domain model if the task only asks for persistence models.
- Do not edit unrelated services or unrelated layers unless explicitly requested.

## Migration Rules
Migration files currently live in:

```text
src/main/resources/db.migration
```

When generating code from migrations:
- Read the migration first.
- Create MyBatis persistence models and mapper SQL matching table names, column names, nullability, and basic constraints.
- Do not guess relationships unless the migration clearly defines the foreign key.
- Enums must match CHECK constraints or seed data.
- UUID, timestamp, and boolean columns must be mapped to suitable Java types.
- Keep database snake_case to Java camelCase mapping explicit in MyBatis result maps or SQL aliases.

## Current Repository Notes
The repository currently has the clearest persistence structure in services/user-service.
Most other services are still scaffold-level modules.

When adding new code, create packages and layers according to the conventions above instead of copying incomplete scaffold code blindly.
