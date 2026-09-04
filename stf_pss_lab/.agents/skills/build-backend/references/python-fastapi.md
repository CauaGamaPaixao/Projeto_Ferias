# Python and FastAPI backend guidance

Use only for a selected or existing FastAPI backend.

- Inspect Python/runtime files, dependency lock/configuration, app factory/entry point, routers, models, dependencies, and tests.
- Keep routers thin; place workflows in services/use cases and persistence/integrations behind adapters.
- Use request/response schemas for boundary validation and do not expose persistence models unintentionally.
- Use dependency injection appropriate to the installed FastAPI version and project conventions.
- Centralize exception mapping, configure external calls with timeouts, and redact secrets from logs.
- Distinguish sync and async work correctly; do not block the event loop with unsupported operations.
- Run configured unit/integration tests, type/static checks, package/build, and startup/health checks.
- Consult Context7 or official FastAPI/Pydantic documentation for installed-version behavior. Do not upgrade Python or dependencies without authorization.
