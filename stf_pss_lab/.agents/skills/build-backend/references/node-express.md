# Node and Express backend guidance

Use only for a selected or existing Node.js Express-style backend.

- Inspect `package.json`, lockfile, runtime declaration, module mode, TypeScript/build, routing, middleware, and tests.
- Keep route handlers thin; place application behavior in services/use cases and persistence/integrations behind adapters.
- Validate params, queries, headers, and bodies before use. Centralize async error propagation and response mapping.
- Avoid mutable process-global user state and unsafe singleton request data.
- Configure secrets, origins, endpoints, and timeouts externally. Redact sensitive log fields.
- Handle graceful shutdown and health checks when deployment requires them.
- Run configured tests, type checks, lint, and production build/startup checks.
- Consult Context7 or official Node/Express documentation for version-sensitive APIs. Do not upgrade runtime or dependencies without authorization.
