---
name: test-fullstack-project
description: Plan, implement, execute, and report requirements-driven unit, component, integration, contract, system, accessibility, security, smoke, and regression testing across application scopes. Use for independent Test Engineering on frontend-only, backend-only, full-stack, non-web, modernization, or infrastructure validation. Do not use to treat builds or static inspection as functional tests or to claim unexecuted checks passed.
---

# Test Full-Stack Project

Test only applicable behavior and preserve implementation scope. Read repository instructions, requirements, acceptance criteria, upstream handoffs, manifests, source, existing tests, and baseline results.

Preserve all preexisting user changes. Never claim a verification passed unless it executed successfully.

## References

Always read [testing strategy](references/testing-strategy.md). Load only applicable level references:

- [unit testing](references/unit-testing.md);
- [integration testing](references/integration-testing.md);
- [system testing](references/system-testing.md);
- [accessibility testing](references/accessibility-testing.md) for visual interfaces.

Use configured tools first. Consult Context7 or authoritative documentation for version-sensitive test tooling. Do not add Playwright, Cypress, scanners, or dependencies without scope and compatibility evidence.

## Workflow

1. Map each `RF-*`, `RNF-*`, and `RN-*` acceptance criterion to applicable test levels and stable `TC-*` identifiers.
2. Establish baseline results before attributing failures to new work.
3. Identify coverage gaps across unit, component, integration, contract, system/E2E, accessibility, security, smoke, and regression.
4. Implement the smallest tests that validate public behavior and important failure paths without duplicating lower-level coverage unnecessarily.
5. For full-stack projects, require a frontend/backend contract or integration test plus a system journey covering success and a representative error when the environment permits.
6. For visual interfaces, require at least one principal E2E journey when feasible and the full accessibility matrix.
7. Execute exact configured commands. Distinguish automated, manual, static, build, and runtime evidence.
8. Produce `Requirement -> test type -> test file -> command -> result` traceability.
9. Return the common handoff.

## Evidence rules

- Unit tests do not replace integration; integration does not replace system testing.
- A build is not a functional test; a manual check is not automated; a scanner does not replace keyboard/visual verification.
- Prove pre-existing failures with a baseline or comparison to original code.
- Never alter product behavior merely to make an incorrect test pass.
- Never invent environments, credentials, results, approvals, or coverage. Use `TBD`, `Assumption`, or `Not applicable`.
- Do not commit, push, deploy, or mutate infrastructure without explicit authorization.

## Common handoff

Report **Status** (`completed`, `partial`, `blocked`), **Scope**, **Files**, **Requirements**, **Checks** grouped by `passed`/`failed`/`not run`/`not applicable`, **Assumptions**, **Risks**, **Decisions** with evidence, and **Handoff** including the traceability matrix, failures, gaps, commands, artifacts, rollback, and next actions. Never call an unexecuted check passed.
