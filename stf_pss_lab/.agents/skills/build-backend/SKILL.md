---
name: build-backend
description: Design, implement, and verify layered backend APIs, application services, validation, errors, repositories, external integrations, configuration, safe logging, health checks, and backend builds. Use for backend-only or backend slices selected by the orchestrator. Do not use to choose persistence independently, implement complete authentication without coordination, modify frontend code, or introduce unjustified distributed infrastructure.
---

# Build Backend

Implement only the selected backend scope from agreed requirements and contracts. Read repository instructions, executable configuration, source, tests, and upstream handoffs before editing.

Preserve all preexisting user changes. Never claim a verification passed unless it executed successfully.

## References

Always read [backend architecture](references/backend-architecture.md). Read exactly one applicable stack reference:

- [Spring Boot](references/spring-boot.md)
- [Node and Express](references/node-express.md)
- [Python and FastAPI](references/python-fastapi.md)

For another stack, consult Context7 or authoritative documentation. Do not load unrelated stack references or pin/upgrade versions without executable evidence and compatibility research.

## Workflow

1. Confirm `RF-*`, `RNF-*`, `RN-*`, API contract, errors, authentication semantics, persistence handoff, integrations, and exclusions.
2. Inspect manifests, controllers/routes, services/use cases, domain, repositories, configuration, exception handling, logs, and tests.
3. Design thin controllers/handlers, application services, domain rules, DTOs, repository ports, adapters, validation, and consistent errors.
4. Implement input validation at trust boundaries and authorization hooks supplied by `implement-authentication` when applicable.
5. Coordinate storage choices, transactions, migrations, and isolation with `design-data-persistence`; do not choose a database silently.
6. Isolate external services behind adapters with configured timeouts, response validation, safe errors, and tests.
7. Add implementation-related unit, controller, repository, and integration tests; coordinate system coverage with `test-fullstack-project`.
8. Run configured test, package/build, lint, format, startup, and health checks as applicable.
9. Return the common handoff.

## Boundaries

- Keep business behavior out of transport and persistence code.
- Use constructor injection when supported.
- Separate transport DTOs from persistence entities when appropriate.
- Keep HTTP errors consistent and avoid leaking internals or secrets.
- Load secrets through external configuration; never add real credentials or tokens to code/logs.
- Never store user-specific state globally.
- Do not modify frontend, introduce queues/microservices without justification, commit, push, deploy, or mutate infrastructure without explicit authorization.
- Mark unknown facts `TBD`, `Assumption`, or `Not applicable`.

## Common handoff

Report **Status** (`completed`, `partial`, `blocked`), **Scope** executed/not executed, **Files** created/altered/removed, **Requirements** satisfied/pending/not applicable, **Checks** grouped by `passed`/`failed`/`not run`/`not applicable`, **Assumptions**, **Risks**, **Decisions** with evidence, and **Handoff** contracts/gaps for the next skill. Include rollback guidance and requirement-to-component-to-test traceability. Never claim an unexecuted check passed.
