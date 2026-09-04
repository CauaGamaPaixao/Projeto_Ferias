# Spring Boot backend guidance

Use only for a selected or existing Spring Boot backend.

- Inspect `pom.xml`/Gradle files, wrapper, Java release, Spring Boot parent/plugin, configuration, and tests.
- Keep `@RestController` classes thin; use services/use cases for workflows and repositories/adapters for storage.
- Use Bean Validation at request boundaries and centralized exception handling for consistent errors.
- Prefer constructor injection. Separate DTOs from persistence entities when exposure or coupling is risky.
- Use externalized properties and placeholders; never commit credentials.
- Use configured HTTP clients with explicit connect/read timeouts and tested failure translation.
- Run configured Maven/Gradle tests, package/build, context/startup, and health checks.
- Consult Context7 or official Spring documentation for installed-version behavior. Do not upgrade Java, Spring Boot, Maven/Gradle, plugins, or dependencies without authorization.
