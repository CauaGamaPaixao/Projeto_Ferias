# Vue frontend guidance

Use only when the selected or existing frontend is Vue.

- Inspect manifests, lockfile, Vue version, build tooling, router, store/composables, TypeScript, and tests.
- Follow the established Options or Composition API style; do not convert patterns incidentally.
- Keep components presentational, place reusable behavior in focused composables/services, and centralize state only when required.
- Keep API clients outside components and use explicit contract types when supported.
- Preserve routing and build conventions. Do not introduce Nuxt, a store library, or new tooling without a requirement.
- Test public component behavior with the configured runner and run the production build.
- Consult Context7 or official Vue documentation for version-sensitive APIs. Do not upgrade packages without authorization.
