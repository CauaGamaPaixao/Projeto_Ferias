# Angular modernization guidance

## Evidence to collect

- Angular core, CLI, build tooling, TypeScript, RxJS, Zone.js, Node.js, and test-runner versions.
- Workspace layout, builders, routing, modules or standalone components, forms, HTTP services, guards, interceptors, and styling.
- Lockfile state, peer-dependency conflicts, deprecated APIs, lint configuration, and build warnings.
- Unit, integration, component, and end-to-end test coverage.

## Planning rules

- Verify supported Node.js and TypeScript ranges for the chosen Angular target.
- Follow the official sequential upgrade path for major versions when required.
- Upgrade framework packages as a compatible set; do not mix arbitrary Angular major versions.
- Run framework migrations and inspect every generated change.
- Keep conversion to standalone components, state-management changes, design-system replacement, and framework upgrades as separate decisions unless tightly coupled.
- Preserve routes, HTTP contracts, form validation, accessibility, and visible user behavior unless changes are approved.
- Treat removal of deprecated APIs as a dedicated, tested slice.

## Verification

- Clean dependency installation from the lockfile.
- TypeScript compilation and production build.
- Unit tests in non-watch mode.
- Lint or static checks when configured.
- Critical route, form, HTTP-error, and responsive-layout smoke tests.
- Bundle or performance comparison when modernization claims performance benefit.

Do not introduce a new state library, component library, SSR mode, or build system solely because it is modern.
