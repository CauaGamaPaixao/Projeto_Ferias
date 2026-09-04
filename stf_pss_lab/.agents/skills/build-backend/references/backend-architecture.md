# Backend architecture

## Layers and flow

Use proportionate boundaries:

```text
transport/controller -> application service/use case -> domain
                                      -> repository/integration ports -> adapters
```

- Parse transport input and map output in controllers/handlers.
- Enforce workflows in application services and invariants in domain behavior.
- Hide persistence and external services behind explicit boundaries when they may vary or require testing.
- Keep framework types from spreading through domain code when practical.

## Contracts and errors

Implement the orchestrator-approved routes, methods, request/response schemas, status codes, validation, error format, authentication, and compatibility rules. Do not change a contract silently. Use stable error codes when consumers need them and avoid internal stack traces in responses.

## Integrations and operations

Configure endpoints, credentials, and timeouts externally. Validate upstream responses, limit retries, avoid logging secrets/personal data, and translate failures safely. Add health checks only when operationally meaningful; distinguish liveness from dependency readiness.
