# Docker guidance

Use only when reproducible container deployment/development is required or existing architecture uses containers.

- Select a supported base compatible with executable runtime configuration; pin intentionally and consult current authoritative image/runtime documentation.
- Use multi-stage builds when they materially reduce runtime contents.
- Run as non-root where feasible, minimize packages, avoid secrets in layers/build args, and use `.dockerignore`.
- Keep environment configuration external and define writable paths, health, signals, graceful shutdown, resource needs, and persistent volumes explicitly.
- Do not bake source credentials, development tools, installed host dependencies, or generated secrets into images.
- Validate Dockerfile syntax/build when permitted and scan when tooling exists. Never publish or deploy an image without authorization.
