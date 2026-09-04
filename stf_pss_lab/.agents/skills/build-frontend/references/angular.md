# Angular frontend guidance

Use only when the selected or existing frontend is Angular.

- Inspect `angular.json`, `package.json`, lockfile, TypeScript configuration, bootstrap style, router, and tests before choosing patterns.
- Match the repository's module or standalone-component convention; do not migrate architecture incidentally.
- Keep components thin; place API communication and shared application behavior in injectable services.
- Use typed models for contracts, Angular forms appropriate to existing conventions, and router guards only as frontend navigation controls.
- Prefer constructor or supported functional injection consistently with the installed version.
- Test services and component behavior with the configured runner. Run the configured production build.
- Consult Context7 or official Angular documentation for version-sensitive APIs and compatibility. Do not upgrade Angular, TypeScript, RxJS, Node, builders, or test tooling unless explicitly authorized.
