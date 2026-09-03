# Course Service Code Structure

This file is the source of truth for organizing code in `course-service`.
Every new change must preserve the layered architecture and MyBatis persistence conventions below.

## Base Package

Base Java package:

```text
course.courseservice
```

All Java source must stay under:

```text
src/main/java/course/courseservice
```

## Layer Rules

The service is split into four top-level layers:

```text
api
application
domain
infrastructure
```

Dependency direction:

```text
api -> application -> domain
infrastructure -> domain
```

Rules:

- `domain` must not depend on Spring, MyBatis, JDBC, database entities, controllers, DTOs, or infrastructure classes.
- `application` orchestrates use cases and depends on domain repositories/interfaces, not MyBatis repositories.
- `api` contains controllers, request DTOs, response DTOs, and API exception handling.
- `infrastructure` contains framework integrations, MyBatis, persistence entities, repository adapters, and persistence mappers.
- Domain repository interfaces stay in `domain.repository`.
- Repository implementations stay in `infrastructure.adapter`.

## Current Package Layout

```text
src/main/java/course/courseservice/
  CourseServiceApplication.java
  api/
    controller/
    dto/
      request/
      response/
    exception/
  application/
    command/
    dto/
    exception/
    usecase/
      category/
      course/
  domain/
    model/
      category/
        aggregate/
        enums/
      course/
        aggregate/
        entity/
        enums/
        exception/
        valueobject/
    repository/
  infrastructure/
    adapter/
    config/
    mapper/
    persistence/
      entity/
        enums/
      repository/
```

## Spring Boot Entry Point

`CourseServiceApplication` must scan MyBatis repositories only from infrastructure:

```java
@MapperScan("course.courseservice.infrastructure.persistence.repository")
```

Do not add `@MapperScan` for `domain`, `application`, `api`, or `infrastructure.mapper`.

## Domain Layer

Use domain classes for business behavior:

```text
domain/model/<aggregate>/
  aggregate/
  entity/
  enums/
  exception/
  valueobject/
```

Rules:

- Aggregates expose behavior and enforce invariants.
- Value objects validate their own values.
- Domain enums are separate from persistence enums.
- Domain models must not be annotated with `@Entity`, `@Table`, `@Mapper`, `@Component`, or other persistence/framework annotations.
- Domain repository interfaces define persistence needs using domain types.

Example:

```text
domain/repository/CourseRepository.java
domain/repository/CategoryRepository.java
```

## Application Layer

Application code lives in:

```text
application/
  command/
  dto/
  exception/
  usecase/
```

Rules:

- Use cases inject domain repository interfaces.
- Use cases return application DTOs or domain results mapped to application DTOs.
- Do not inject MyBatis repositories in application code.
- Do not write SQL in application code.
- Keep transaction boundaries in application/use case or adapter only when needed.

## API Layer

API code lives in:

```text
api/
  controller/
  dto/request/
  dto/response/
  exception/
```

Rules:

- Controllers call application use cases/handlers.
- Controllers must not call domain repository adapters directly.
- Controllers must not call MyBatis repositories.
- Request and response DTOs are API contracts, not domain models and not persistence entities.

## Infrastructure Layer

Infrastructure code must follow this structure:

```text
infrastructure/
  adapter/
    <Aggregate>RepositoryAdapter.java
  config/
    UuidTypeHandler.java
  mapper/
    <Aggregate>PersistenceMapper.java
  persistence/
    entity/
      <Aggregate>InfraEntity.java
    repository/
      <Aggregate>MybatisRepository.java
```

For `course-service`, current aggregate-level infrastructure classes include:

```text
infrastructure/adapter/CategoryRepositoryAdapter.java
infrastructure/adapter/CourseRepositoryAdapter.java
infrastructure/mapper/CategoryPersistenceMapper.java
infrastructure/mapper/CoursePersistenceMapper.java
infrastructure/persistence/repository/CategoryMybatisRepository.java
infrastructure/persistence/repository/CourseMybatisRepository.java
```

Persistence entities include aggregate roots and child table records:

```text
CategoryInfraEntity.java
CourseInfraEntity.java
CourseAssetInfraEntity.java
CourseCategoryInfraEntity.java
LessonInfraEntity.java
ModuleInfraEntity.java
```

## Repository Adapter Rules

Repository adapters live in:

```text
course.courseservice.infrastructure.adapter
```

Naming:

```text
<Aggregate>RepositoryAdapter.java
```

Required annotations:

```java
@Component
@RequiredArgsConstructor
```

Rules:

- Adapter implements the domain repository interface.
- Adapter injects the corresponding `<Aggregate>MybatisRepository`.
- Adapter injects the corresponding `<Aggregate>PersistenceMapper`.
- Adapter maps persistence entities to domain models before returning.
- Adapter maps domain models to persistence entities before saving.
- Do not put SQL in adapters.
- Do not return `*InfraEntity` from adapters.

## Persistence Mapper Rules

Persistence mappers live in:

```text
course.courseservice.infrastructure.mapper
```

Naming:

```text
<Aggregate>PersistenceMapper.java
```

Required annotation:

```java
@Component
```

Rules:

- Convert domain model to `*InfraEntity`.
- Convert `*InfraEntity` to domain model.
- Convert domain enums to persistence enums and back.
- Do not execute database calls here.
- Do not inject MyBatis repositories here unless there is a strong reason and the design is reviewed first.

## MyBatis Repository Rules

MyBatis repository interfaces live in:

```text
course.courseservice.infrastructure.persistence.repository
```

Naming:

```text
<Aggregate>MybatisRepository.java
```

Required annotation:

```java
@Mapper
```

Rules:

- Every method parameter must use `@Param`, including single parameters.
- Interface methods must match XML statement IDs exactly.
- Do not write SQL in Java annotations when an XML mapper exists.
- Do not place MyBatis interfaces in `domain`, `application`, `api`, or `infrastructure.mapper`.

Example:

```java
@Mapper
public interface CourseMybatisRepository {
    Optional<CourseInfraEntity> findById(@Param("id") UUID id);
}
```

## Persistence Entity Rules

Persistence entities live in:

```text
course.courseservice.infrastructure.persistence.entity
```

Naming:

```text
<Aggregate>InfraEntity.java
```

Required Lombok annotations:

```java
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
```

Rules:

- Persistence entities are database records only.
- Do not use persistence entities as domain models.
- Do not return persistence entities from API responses.
- Do not put business rules in persistence entities.
- Persistence-specific enums may stay under `infrastructure.persistence.entity.enums`.

## MyBatis XML Rules

XML mapper files live in:

```text
src/main/resources/mybatis
```

Naming:

```text
<Aggregate>MybatisRepositoryMapper.xml
```

Namespace must point to the Java MyBatis repository:

```xml
<mapper namespace="course.courseservice.infrastructure.persistence.repository.<Aggregate>MybatisRepository">
```

Rules:

- Each `<select>`, `<insert>`, `<update>`, and `<delete>` `id` must match a method name in the corresponding Java interface.
- Use `resultMap` for mapped entities.
- Do not rely on ad hoc `resultType` for persistence entities.
- UUID columns and UUID parameters must specify `UuidTypeHandler`.
- Keep SQL in XML mapper files, not Java annotations.

UUID mapping example:

```xml
<id property="id" column="id" typeHandler="course.courseservice.infrastructure.config.UuidTypeHandler"/>
```

UUID parameter example:

```xml
WHERE id = #{id, typeHandler=course.courseservice.infrastructure.config.UuidTypeHandler}
```

## Resource Layout

Required resource layout:

```text
src/main/resources/
  application.yaml
  mybatis-config.xml
  mybatis/
    CategoryMybatisRepositoryMapper.xml
    CourseMybatisRepositoryMapper.xml
```

Required MyBatis YAML:

```yaml
mybatis:
  mapper-locations: classpath*:mybatis/**/*.xml
  type-aliases-package: course.courseservice.infrastructure.persistence.entity
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

Current `mybatis-config.xml` follows the product-service style:

```xml
<typeHandlers>
    <typeHandler handler="course.courseservice.infrastructure.config.UuidTypeHandler" javaType="java.util.UUID"/>
</typeHandlers>
```

If the project later uses `mybatis.config-location`, wrap this content in a root `<configuration>...</configuration>`.

## Prohibited Patterns

Do not add:

- JPA repository implementations for this service.
- `@Entity`, `@Table`, or Spring Data repository classes for course persistence.
- SQL in Java annotations when XML mapper files exist.
- MyBatis imports in `domain`, `application`, or `api`.
- JDBC imports in `domain`, `application`, or `api`.
- Persistence entities in API response DTOs.
- Domain models in MyBatis XML result maps.
- Repository adapters under `infrastructure.persistence.adapter`.
- Domain/persistence mappers under `infrastructure.persistence.mapper`.

## Checklist Before Finishing a Change

Run these checks before considering a course-service code change done:

```powershell
.\mvnw.cmd test
.\mvnw.cmd package
```

Manual checklist:

- Main class has `@MapperScan("course.courseservice.infrastructure.persistence.repository")`.
- New MyBatis interface is under `infrastructure.persistence.repository`.
- New DB entity is named `*InfraEntity`.
- New domain-persistence mapper is under `infrastructure.mapper`.
- New repository adapter is under `infrastructure.adapter`.
- XML mapper is under `src/main/resources/mybatis`.
- XML namespace matches the MyBatis repository interface.
- XML statement IDs match Java method names.
- UUID fields and parameters use `UuidTypeHandler`.
- Domain layer has no MyBatis, JDBC, Spring, or infrastructure imports.
