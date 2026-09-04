---
name: build-frontend
description: Design, implement, and verify accessible frontend presentation, routes, forms, state, API consumption, responsive states, and frontend builds. Use for a visual web frontend or frontend slice selected by the orchestrator. Do not use for backend business rules, server persistence decisions, authentication enforcement, invented endpoints, or projects without a visual interface.
---

# Build Frontend

Implement only the selected frontend scope. Read repository instructions, the orchestrator handoff, executable configuration, source, and tests before editing. Preserve user changes and domain agnosticism.

Preserve all preexisting user changes. Never claim a verification passed unless it executed successfully.

## References

Always read [frontend architecture](references/frontend-architecture.md) and [accessible UI foundation](references/accessibility-ui.md).

Read exactly one stack reference matching executable configuration or an explicit requirement:

- [Angular](references/angular.md)
- [React](references/react.md)
- [Vue](references/vue.md)
- [Vanilla](references/vanilla.md)

For another stack, consult Context7 or authoritative documentation and record evidence. Never load or mix framework references without an explicit multi-framework requirement. Never pin or upgrade versions without checking manifests, lockfiles, compatibility, and current documentation.

## Workflow

1. Confirm `RF-*`, `RNF-*`, `RN-*`, journeys, screens, API contract, states, exclusions, and acceptance criteria from the handoff.
2. Inspect frontend structure, manifests, routing, styling, state, API clients, and tests.
3. Plan thin pages/components, reusable presentation, state ownership, route behavior, forms, validation, and loading, empty, success, and error states.
4. Implement vertical slices without moving business rules from backend/application services into components.
5. Consume only agreed API routes, methods, requests, responses, errors, and authentication semantics. Report a contract gap instead of inventing an endpoint.
6. Implement the complete accessibility foundation, responsiveness, keyboard behavior, focus, contrast, preference persistence, and reduced motion.
7. Add component tests related to implemented frontend behavior. Coordinate broader strategy and E2E coverage with `test-fullstack-project`.
8. Run configured build, tests, lint, format, and applicable accessibility checks. Never call inspection an executed test.
9. Return the common handoff.

## Boundaries

- Keep components and route guards thin.
- Keep secrets out of browser code and logs.
- Treat frontend route protection as usability only; require backend authorization for protected resources.
- Do not choose server persistence, implement central backend rules, or modify backend code.
- Do not add frameworks, state libraries, design systems, SSR, or dependencies without a requirement.
- Mark unknown requirements, endpoints, environments, approvals, dates, or credentials as `TBD`, `Assumption`, or `Not applicable`.
- Do not commit, push, deploy, or mutate infrastructure without explicit authorization.

## Common handoff

Report:

- **Status:** `completed`, `partial`, or `blocked`.
- **Scope:** responsibility executed and not executed.
- **Files:** created, altered, and removed files.
- **Requirements:** satisfied, pending, and `not applicable` identifiers.
- **Checks:** `passed`, `failed`, `not run`, and `not applicable`, with commands/evidence.
- **Assumptions:** adopted assumptions and validation needs.
- **Risks:** remaining risks and limitations.
- **Decisions:** technical decisions and evidence, using `ADR-*` when recorded.
- **Handoff:** contracts, artifacts, gaps, and next-skill instructions.

Never report a check as `passed` unless it executed successfully. Include rollback guidance, changed files, and traceability from requirement to component and tests.
