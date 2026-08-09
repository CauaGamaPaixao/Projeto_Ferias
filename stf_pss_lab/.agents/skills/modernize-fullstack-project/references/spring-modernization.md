# Spring Boot modernization guidance

## Evidence to collect

- Java release, Spring Boot parent or plugin, Maven or Gradle version, dependency management, packaging, and deployment runtime.
- Web, validation, persistence, security, session, integration, logging, and observability dependencies.
- Controllers, DTOs, services, repositories, configuration, exception handling, tests, and database migrations.
- Use of `javax.*` versus `jakarta.*`, deprecated configuration properties, removed APIs, and custom auto-configuration.

## Planning rules

- Verify the Java version range supported by the target Spring Boot release.
- Use a supported migration path and review release notes for every crossed major version.
- Let Spring Boot dependency management control managed versions unless a documented reason requires an override.
- Separate runtime upgrades, Spring Boot upgrades, persistence changes, Spring Security redesign, and API-contract changes where possible.
- Add characterization tests around authentication, authorization, validation, serialization, error responses, transactions, and external integrations before changing them.
- Use database migrations for schema changes and define backward compatibility plus rollback.
- Preserve external configuration names or provide an explicit migration path.

## Verification

- Maven or Gradle clean test and package.
- Application-context startup test.
- Controller and validation behavior.
- Repository and migration tests against the selected database.
- Authentication and authorization tests when security exists.
- Integration failure and timeout behavior.
- Startup smoke test using production-like configuration without real secrets.

Do not call a Java or Spring Boot version unsupported merely because it is not the newest. Confirm vendor support and organizational requirements.
