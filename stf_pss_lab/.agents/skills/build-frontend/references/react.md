# React frontend guidance

Use only when the selected or existing frontend is React.

- Inspect manifests, lockfile, bundler/framework, router, rendering mode, TypeScript, state, and tests.
- Keep components small and behavior-focused; isolate API clients and domain/application behavior.
- Prefer local state first, focused hooks for reusable behavior, and context only for genuinely shared state.
- Preserve the existing routing and rendering model; do not introduce SSR, a meta-framework, or a state library without a requirement.
- Use controlled or established form patterns with accessible validation feedback.
- Test observable behavior with the configured tools, commonly Testing Library, and run the configured production build.
- Consult Context7 or official React/framework documentation for version-sensitive decisions. Do not upgrade dependencies without authorization.
