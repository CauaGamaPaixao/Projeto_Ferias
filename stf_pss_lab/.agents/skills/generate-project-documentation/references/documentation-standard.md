# Project documentation standard

## Purpose and positioning

Produce a coherent functional, technical, testing, and management record from the briefing and actual project evidence. Describe the approach as **adapted and aligned with PMBOK guidance** or, in Portuguese, **adaptado e alinhado às orientações do PMBOK**. Never state or imply PMI certification, endorsement, accreditation, audit approval, or official compliance.

## Source precedence

Use sources in this order for current-state claims:

1. executable configuration and manifests;
2. source code and database migrations;
3. automated tests and actual validation results;
4. approved decisions and change records;
5. briefing and acceptance criteria;
6. existing narrative documentation.

Higher precedence does not cancel a lower source. Record disagreements as divergences and identify the evidence on each side.

## Tailoring profiles

### Light

Use for a small prototype or low-risk, short-lived application with few components, no regulated data, limited dependencies, and informal governance. Keep every required document, but use compact sections, qualitative risks, and concise controls. Mark excluded sections `Not applicable` with a reason.

### Standard

Use by default for ordinary applications with multiple journeys, components, contributors, integrations, or meaningful operational risk. Complete every template at normal depth and maintain end-to-end traceability.

### Extended

Use for high complexity, material business impact, regulated or sensitive data, multiple teams or vendors, critical integrations, formal governance, or demanding availability and security needs. Expand governance, assurance, dependency, procurement, security, privacy, release, operational-readiness, and quantitative-risk sections only when applicable and supported by evidence.

If evidence is insufficient to choose confidently, use `Standard` and record the selection as an assumption.

## Required markers

- `TBD`: a required fact is unknown and must be supplied or discovered.
- `Assumption`: a reversible working premise used to proceed; include validation or owner when known.
- `Not applicable`: a section does not apply; always include the reason.

Never invent stakeholders, owners, dates, costs, approvals, environments, status, test results, or decisions.

## Identifier rules

- Functional requirement: `RF-001`.
- Non-functional requirement: `RNF-001`.
- Business rule: `RN-001`.
- Risk: `RSK-001`.
- Optional test, change, and decision identifiers: `TC-001`, `CHG-001`, and `DEC-001`.

Increment identifiers by one and keep them stable across revisions. Retire identifiers instead of reusing them for different meanings.

## Requirement quality

Write atomic, testable requirements. Include identifier, title, statement, rationale, source, priority, acceptance criteria, implementation status, linked business rules, component mapping, test mapping, and gaps. Separate desired behavior from observed implementation. Record undocumented implemented behavior as a divergence, not as an approved requirement.

## Traceability and consistency

Maintain links in both directions:

```text
Briefing / source -> Requirement -> Business rule -> Component -> Test -> Result
```

For every applicable requirement, identify an implementing component and verification method. Use `TBD` for unknown mappings and `Not applicable` only with justification.

Compare:

| Surface | Check |
|---|---|
| Briefing | Scope, exclusions, constraints, acceptance criteria |
| Documentation | Stable identifiers, consistent statements, changes |
| Implementation | Actual routes, components, services, schemas, configuration |
| Tests | Covered behavior, expected failures, execution status |

Classify findings as contradiction, missing implementation, undocumented implementation, missing test, stale document, or unresolved assumption.

## Document control

Each document must state title, project, version, status, profile, last-updated date if known, owner if known, evidence sources, and confidentiality when supplied. Unknown dates and owners remain `TBD`. Do not manufacture historical versions.

## Verification states

- `passed`: executed or directly verified;
- `failed`: executed and unsuccessful;
- `not run`: applicable but not executed;
- `not applicable`: irrelevant, with reason.

Do not treat static inspection as executed runtime validation.
