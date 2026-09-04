# Frontend architecture

## Responsibility boundaries

- Keep pages focused on orchestration and components focused on presentation and interaction.
- Keep domain rules in application/domain services; do not embed them in templates or UI event handlers.
- Isolate HTTP and external clients from components.
- Centralize shared state only when multiple consumers require it; prefer local state otherwise.
- Model routes, forms, DTOs, errors, and UI states explicitly.

## Required states

Implement applicable loading, empty, success, validation, recoverable error, fatal error, disabled, and unauthorized/forbidden presentation. Do not communicate state only with color.

## API contract

Use the orchestrator-approved contract. Record route, method, request, response, validation, errors, authentication, and version assumptions. Do not silently reinterpret backend fields or invent endpoints.

## Quality

Use semantic HTML, responsive layout, relative units, stable keys, safe rendering, and deterministic state transitions. Test public behavior rather than implementation details. Build using commands from executable configuration.
