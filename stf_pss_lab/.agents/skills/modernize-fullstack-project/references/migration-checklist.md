# Migration checklist

## Before changes

- Confirm scope, owner, success criteria, excluded work, and maintenance window if relevant.
- Capture Git state and establish a recoverable checkpoint.
- Record versions from executable configuration.
- Run baseline tests, builds, and smoke flows.
- Add characterization tests for behavior that must remain stable.
- Identify consumers of APIs, events, database schemas, and configuration.
- Confirm backup and rollback procedures for stateful changes.

## During each phase

- Change one coherent concern.
- Keep dependency and lockfile changes reviewable.
- Review automated migrations before accepting them.
- Update deprecated APIs and configuration deliberately.
- Run the narrowest relevant checks after each step.
- Record unexpected behavior and stop when scope materially expands.
- Avoid mixing formatting-only changes with behavioral migrations.

## Phase acceptance

- Acceptance criteria mapped to evidence.
- Tests and build results recorded as `passed`, `failed`, or `not run`.
- Public API, UI, data, and configuration compatibility assessed.
- Security and authorization behavior checked.
- Documentation and runbooks updated.
- Rollback remains feasible and documented.
- No secrets, build output, installed dependencies, or unrelated changes added.

## Completion

- Run full relevant test suites and production builds.
- Perform critical user-journey smoke tests.
- Compare behavior, errors, performance, and operational signals with baseline.
- Document changed contracts and consumer actions.
- Record remaining risks, deferred work, and the next recommended phase.

Modernization is complete only for the approved scope. Keep unrelated backlog items visible instead of implying the entire system is modernized.
