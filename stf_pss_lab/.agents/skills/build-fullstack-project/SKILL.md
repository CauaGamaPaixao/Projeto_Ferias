---
name: build-fullstack-project
description: Orchestrate requirements-to-delivery for new frontend, backend, full-stack, non-web, or modernization projects by classifying scope, selecting specialist skills, defining contracts, coordinating checkpoints, requiring tests and documentation, and consolidating evidence. Use for application generation, major scaffolding, or coordinated modernization delivery. Do not use for isolated specialist work that has a narrower skill or to implement every technical concern directly.
---

# Build Full-Stack Project Orchestrator

Coordinate an Application Engineering workflow from briefing to verified handoff. Keep specialist implementation in specialist skills. Preserve domain agnosticism and use Projeto_Ferias as engineering evidence, never as a business template.

Preserve all preexisting user changes. Never claim a verification passed unless it executed successfully.

## Operating modes

- **Plan:** inspect, classify, define contracts, selected skills, sequence, checkpoints, risks, and validation plan; do not modify files.
- **Execute:** execute the selected specialist skills, consolidate validation and documentation, and report changes.

For modernization, delegate operating-mode semantics to `modernize-fullstack-project`, which supports **Assessment**, **Plan**, and **Execute**. Never turn assessment or planning into implementation.

## Baseline and required references

Before planning or implementation:

1. Read repository `AGENTS.md` and run `git status --short`.
2. Inspect repository structure, manifests, lockfiles, executable configuration, source, tests, CI, documentation, and generated directories.
3. Read [Projeto_Ferias patterns](references/projeto-ferias-patterns.md), [stack selection](references/stack-selection.md), and [delivery checklist](references/delivery-checklist.md).
4. Treat executable configuration and source as stronger current-state evidence than stale documentation; report divergences.
5. Preserve user changes and stop on conflicting edits in selected files.

Do not copy marketplace entities, Brazil-themed identity, products, carts, users, package names, versions, Gemini, ViaCEP, demo credentials, API paths, or accidental limitations. Reuse only responsibility boundaries, contracts, configuration hygiene, tests, reproducibility, and delivery quality.

## Analyze the briefing

Identify objective, intended users, actors, primary journeys/interfaces, business rules, data, authentication/authorization, integrations, technology constraints, persistence, accessibility, deployment, acceptance criteria, exclusions, scale, security, privacy, availability, and operational needs.

Use stable identifiers:

- functional requirements: `RF-001`;
- non-functional requirements: `RNF-001`;
- business rules: `RN-001`;
- risks: `RSK-001`;
- architecture decisions: `ADR-001`;
- test cases: `TC-001`.

Never invent requirements, stakeholders, approvals, costs, dates, credentials, or environments. Mark unknown information `TBD`, `Assumption`, or `Not applicable`. Ask only when a missing answer materially changes architecture, security, ownership, or scope; otherwise record a reversible assumption.

## Classify and route

Classify exactly one primary project type and record the evidence:

### Frontend-only

Select, in order:

1. `build-frontend`;
2. `test-fullstack-project`;
3. `generate-project-documentation`.

Do not create a backend. Select `design-data-persistence` only if client-local persistence needs independent design; do not imply server persistence.

### Backend-only

Select, in order:

1. `design-data-persistence` when applicable;
2. `implement-authentication` when applicable;
3. `build-backend`;
4. `test-fullstack-project`;
5. `generate-project-documentation`.

Do not create a frontend for an API without a visual interface.

### Full-stack

Select, in order:

1. `design-data-persistence` when applicable;
2. `implement-authentication` when applicable;
3. `build-backend` and `build-frontend` against the agreed contract;
4. `test-fullstack-project`;
5. `generate-project-documentation`.

### Non-web

Select only technical skills matching the interface and architecture, followed by `test-fullstack-project` and `generate-project-documentation`. Do not select `build-frontend` for a CLI/batch/library without visual UI. Record the accessible UI foundation as `not applicable` with the reason.

### Modernization

Select, in order:

1. `modernize-fullstack-project`;
2. `build-frontend` and/or `build-backend` when the approved phase affects those layers, plus other affected specialist skills only when applicable;
3. `test-fullstack-project`;
4. `generate-project-documentation`.

Never perform an automatic big-bang rewrite. Preserve behavior, baseline, compatibility, checkpoints, rollback, and incremental phases.

### Infrastructure

Add `provision-infrastructure` only when explicitly requested or justified by deployment requirements. Do not create Docker, Terraform, queues, cloud resources, CI/CD, or distributed services by default. Never deploy or mutate external infrastructure without explicit authorization.

For infrastructure-only scope, select `provision-infrastructure`, then `test-fullstack-project` for applicable static/non-mutating validation, then `generate-project-documentation`. Mark application implementation skills `not applicable` unless the briefing also changes application code.

For every unselected competence, record `not applicable` and the reason. Do not create empty directories.

## Explicit skill execution

Before executing a selected competence, read that skill's `SKILL.md` explicitly and follow its reference-routing instructions. Do not depend only on implicit activation. Load only references applicable to the chosen stack and scope; never load all frontend or backend variants simultaneously.

Require every specialist handoff to contain:

- **Status:** `completed`, `partial`, or `blocked`;
- **Scope:** executed and not executed;
- **Files:** created, altered, removed;
- **Requirements:** satisfied, pending, `not applicable`;
- **Checks:** `passed`, `failed`, `not run`, `not applicable`;
- **Assumptions**, **Risks**, **Decisions** with evidence;
- **Handoff:** contracts, artifacts, gaps, rollback, and next-skill instructions.

Do not proceed across a checkpoint when a blocking contract, security, data-loss, or scope issue remains unresolved.

## Architecture and shared contracts

Prefer the smallest architecture that meets acceptance criteria. Keep presentation, application/domain behavior, persistence, integrations, configuration, validation, and errors separated proportionately. Keep components, controllers, and handlers thin.

Before frontend/backend implementation, define the shared API contract. Prefer OpenAPI when appropriate. Record routes, methods, request/response schemas, validation, errors, authentication, compatibility, and ownership. Frontend must not invent endpoints; backend must not change them silently.

Maintain traceability:

```text
Requirement -> business rule -> component -> test -> result
```

Use Context7 or authoritative vendor documentation for framework/runtime/dependency compatibility. Respect explicit stacks and healthy repository conventions. Never select versions merely because they are newest and never upgrade runtimes or dependencies without scope.

## Application structure

Every generated application requires its own root. Adapt names to repository conventions while keeping separate frontend and backend directories whenever both exist:

```text
<application-root>/
|-- frontend/        # only when applicable
|-- backend/         # only when applicable
|-- docs/
|   `-- project/
|-- .gitignore
`-- README.md
```

Do not create missing layers as empty placeholders.

## Mandatory quality foundations

- For visual UI, `build-frontend` must apply its complete [`accessibility-ui.md`](../build-frontend/references/accessibility-ui.md): A−, reset, A+, 87.5–125%, light/dark/system theme, preference persistence, keyboard, visible focus, WCAG 2.2 AA contrast, reduced motion, responsiveness, tests, and documentation.
- For projects without visual UI, record accessibility UI as `not applicable` with a reason.
- Implement authentication only when required and enforce authorization on the backend.
- Choose persistence proportionately; require migrations for durable databases and never share private user data globally.
- Keep credentials/secrets external, use placeholders, validate inputs, configure integration timeouts, validate upstream responses, and log safely.
- Keep business rules outside controllers, handlers, UI components, and persistence adapters.

## Checkpoints and validation

Coordinate these checkpoints:

1. briefing classification and acceptance mapping;
2. stack/architecture decision and `ADR-*` evidence;
3. API/data/authentication contracts;
4. specialist vertical-slice handoffs;
5. test baseline and affected checks;
6. full relevant tests, builds/packages, lint/format/static checks, smoke/system/accessibility checks;
7. documentation after implementation and tests;
8. final divergence, secret, generated-output, status, and diff review.

Never call static inspection a runtime test or claim a command passed unless it executed successfully. Explain every `failed`, `not run`, and `not applicable` result.

## Mandatory documentation

Always execute `generate-project-documentation` after implementation and tests. Use `Standard` by default, or justified `Light`/`Extended`. Generate all nine documents under `docs/project/`, adapted and aligned with PMBOK guidance without claiming PMI certification or official compliance.

Documentation must consolidate specialist handoffs for requirements, architecture, persistence, authentication, tests, risks, decisions, results, limitations, and rollback. Compare briefing, documents, implementation, and tests; record every material divergence.

## Completion and final report

Do not declare completion unless applicable journeys, rules, validation, contracts, persistence, authentication, accessibility, tests, builds, secret handling, ignored outputs, documentation, traceability, and divergence checks are satisfied or honestly classified.

Report operating mode, inventory, classification, selected/unselected skills, execution order, contracts, implemented scope, files, documentation profile/set, divergences, `passed`/`failed`/`not run`/`not applicable` checks, assumptions, risks, limitations, rollback, commands, and next step.

Do not commit, push, create a branch/PR, deploy, or mutate external infrastructure unless explicitly authorized.
