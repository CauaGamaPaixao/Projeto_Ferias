# Integration and contract testing

- Test real wiring across important boundaries: controller/service, repository/database, serializer/schema, API client/server contract, external adapter/fake, authentication/authorization.
- Use the selected engine or a justified compatible substitute when engine semantics matter.
- Validate request/response schemas, status/error formats, ownership, transactions, timeouts, and unavailable dependencies.
- Prefer consumer/provider contract evidence or OpenAPI validation when frontend and backend are separate.
- Keep fixtures isolated and cleanup deterministic; never target production data.
- Record unavailable databases, credentials, services, or containers as `not run`, not passed.
- Integration success does not replace a user-facing system journey.
