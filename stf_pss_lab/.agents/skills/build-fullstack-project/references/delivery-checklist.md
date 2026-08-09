# Delivery checklist

## Requirements

- Actors, journeys, business rules, data, integrations, and acceptance criteria are explicit.
- Assumptions and intentionally deferred scope are visible.

## Architecture and code

- Dependencies point inward toward domain/application behavior where practical.
- Controllers/components do not contain substantial business rules.
- Validation exists at trust boundaries.
- Errors are useful to users without exposing secrets or internals.
- Configuration is externalized and `.env.example` contains placeholders only.
- Database migrations and seed/demo data are deterministic when used.

## Frontend

- Layout works at narrow and wide widths.
- Forms have labels, validation feedback, and keyboard-usable controls.
- Loading, empty, success, and failure states exist where applicable.
- Repeated UI is componentized at the chosen framework's natural level.

## Verification

- Unit tests cover business rules.
- Integration/controller tests cover critical boundaries when feasible.
- Build, tests, lint, and formatting checks have explicit results.
- Core user journey has a smoke test or documented manual walkthrough.
- Acceptance criteria map to evidence.

## Repository hygiene

- No secrets, build output, IDE state, or duplicate source trees are added.
- `.gitignore` matches the selected toolchain.
- Setup, run, test, build, environment, and architecture notes are current.

## Handoff status vocabulary

- `passed`: command ran and returned success.
- `failed`: command ran and returned failure; include the cause.
- `not run`: environment, credential, time, or access prevented execution.
