# Testing strategy

## Selection

Choose levels from risk and architecture:

| Scope | Minimum applicable coverage |
|---|---|
| Frontend-only | unit/component, build, principal journey when feasible, accessibility for visual UI |
| Backend-only | unit, controller/handler, integration/contract, startup or smoke |
| Full-stack | unit/component, API contract/integration, system success and representative error, accessibility for visual UI |
| Non-web | unit plus integration/system behavior suitable to the interface |
| Modernization | baseline/characterization, affected levels, regression, compatibility and rollback checks |
| Infrastructure | static validation, policy/config tests, plan/diff review; no deployment without authorization |

Prioritize business rules, trust boundaries, ownership, validation, error handling, persistence, authentication, external failures, and critical journeys.

## Evidence matrix

| Requirement | Test type | Test file/case | Command | Result/evidence |
|---|---|---|---|---|
| RF-001 | [unit/integration/system/etc.] | TC-001 / TBD | TBD | not run |

Use only `passed`, `failed`, `not run`, or `not applicable`. Explain all non-passed states.
