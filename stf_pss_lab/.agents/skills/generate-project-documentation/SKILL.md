---
name: generate-project-documentation
description: Generate or update functional, technical, testing, governance, and project-management documentation from a project briefing, specialist handoffs, and actual repository evidence. Use for project charters, management plans, functional/technical specifications, test plans, risk registers, traceability matrices, change logs, and lessons learned with Light, Standard, or Extended tailoring adapted and aligned with PMBOK guidance. Do not use to invent project facts, replace implementation/testing, or claim PMI certification or official compliance.
---

# Generate Project Documentation

Generate an evidence-based documentation set adapted and aligned with PMBOK guidance. Do not claim PMI certification, endorsement, accreditation, or official compliance.

Preserve all preexisting user changes. Never claim a verification passed unless it executed successfully.

## Required references

Read [references/documentation-standard.md](references/documentation-standard.md) completely before generating or updating documents.

Read every template used for the selected profile:

- [project charter](references/templates/project-charter.md)
- [project management plan](references/templates/project-management-plan.md)
- [functional specification](references/templates/functional-specification.md)
- [technical specification](references/templates/technical-specification.md)
- [test plan](references/templates/test-plan.md)
- [risk register](references/templates/risk-register.md)
- [traceability matrix](references/templates/traceability-matrix.md)
- [change log](references/templates/change-log.md)
- [lessons learned](references/templates/lessons-learned.md)

## Workflow

1. Read repository instructions and inspect `git status --short`.
2. Read the briefing, existing documentation, manifests, configuration, source code, tests, and build results relevant to the project.
3. Collect applicable specialist handoffs for frontend, backend, persistence, authentication, testing, modernization, and infrastructure. Reconcile their requirements, decisions, risks, results, and gaps with repository evidence.
4. Select `Light`, `Standard`, or `Extended` using the tailoring criteria. Use `Standard` when the user does not choose a profile.
5. Record evidence and gaps before drafting. Treat executable configuration, source, and tests as stronger evidence than stale prose.
6. Assign identifiers consistently: `RF-001` for functional requirements, `RNF-001` for non-functional requirements, `RN-001` for business rules, `RSK-001` for risks, `ADR-001` for architecture decisions, and `TC-001` for test cases.
7. Generate the required documents in the project's documentation directory using the selected templates and profile.
8. Link every applicable requirement to implementing components and verifying tests in the traceability matrix.
9. Compare briefing, documentation, implementation, and tests. Report contradictions, missing implementation, undocumented behavior, and missing test coverage explicitly.
10. Mark unavailable facts as `TBD`, `Assumption`, or `Not applicable`; never invent stakeholders, dates, costs, approvals, environments, evidence, or status.
11. Run structural and repository checks. Classify verification as `passed`, `failed`, `not run`, or `not applicable`.

## Output set

Generate all nine documents for `Standard` and `Extended` profiles:

1. `project-charter.md`
2. `project-management-plan.md`
3. `functional-specification.md`
4. `technical-specification.md`
5. `test-plan.md`
6. `risk-register.md`
7. `traceability-matrix.md`
8. `change-log.md`
9. `lessons-learned.md`

For `Light`, generate the same files with compact sections so the set remains predictable and traceable. Mark sections excluded by tailoring as `Not applicable` with a reason; do not silently omit required files.

## Evidence rules

- Derive current-state claims from actual code and executable configuration.
- Distinguish `Requirement`, `Implemented`, `Verified`, `Assumption`, and `TBD`.
- Never convert a proposed feature into an implemented claim.
- Never mark a test as passed unless it ran successfully or trustworthy existing results are explicitly provided and attributed.
- Preserve unresolved differences in a divergence log or the relevant document; do not harmonize them by guessing.
- Avoid personal or confidential data that is unnecessary for project control.

## Quality gate

Before completion, verify:

- all required files exist;
- the selected profile and tailoring rationale are stated;
- required identifiers are unique and correctly formatted;
- each applicable requirement maps to a component and test, or has an explicit gap;
- risks have unique `RSK-*` identifiers and actionable treatment fields;
- missing facts use the required markers;
- briefing, documentation, code, and tests were cross-checked;
- the exact phrase `adapted and aligned with PMBOK guidance` appears in English documents, or `adaptado e alinhado às orientações do PMBOK` in Portuguese documents;
- no official PMI compliance or certification is claimed;
- repository status and diff checks were performed without altering unrelated work.

## Common handoff

Report **Status** (`completed`, `partial`, or `blocked`), **Scope** executed/not executed, **Files** created/altered/removed, **Requirements** satisfied/pending/not applicable, **Checks** grouped by `passed`/`failed`/`not run`/`not applicable`, **Assumptions**, **Risks**, **Decisions** with evidence, and **Handoff** containing generated documents, divergences, unresolved facts, rollback, and next actions.

Report generated files, profile, evidence inspected, divergences, verification states, limitations, and recommended next action. Do not commit, push, deploy, or mutate infrastructure unless explicitly requested.
