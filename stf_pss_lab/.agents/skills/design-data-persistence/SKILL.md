---
name: design-data-persistence
description: Assess, design, implement, and verify proportionate application persistence, data models, repositories, migrations, transactions, fixtures, isolation, backup, and rollback. Use when a project needs client-local, in-memory, relational, or explicitly requested storage. Do not use when no persistence is required, to invent a database or credentials, or to perform destructive data operations without authorization.
---

# Design Data Persistence

Implement only the persistence scope selected by the orchestrator. Inspect requirements, executable configuration, data sensitivity, concurrency, tests, and backend/frontend handoffs before deciding.

Preserve all preexisting user changes. Never claim a verification passed unless it executed successfully.

## References

Read only the applicable references:

- [relational database](references/relational-database.md) for durable relational storage;
- [in-memory storage](references/in-memory-storage.md) for disposable demonstrations;
- [migrations](references/migrations.md) whenever durable schema evolution applies.

For client-local or another explicitly requested store, consult authoritative documentation and record limitations. Do not load irrelevant references.

## Workflow

1. Classify persistence as none, client-local, in-memory, relational, or explicitly requested other storage.
2. Confirm `RF-*`, `RNF-*`, `RN-*`, ownership, retention, consistency, concurrency, privacy, recovery, and deployment constraints.
3. Model entities, relationships, invariants, repository boundaries, transactions, indexes, isolation, and failure behavior proportionately.
4. Require versioned migrations for durable databases; design safe forward and rollback/recovery procedures.
5. Add deterministic fixtures/demo data only when useful and clearly non-production.
6. Test repository behavior, transactions, migrations, constraints, concurrency, and user-data isolation as applicable.
7. Run configured persistence tests and migration validation without destructive external operations.
8. Return the common handoff.

## Rules

- Use in-memory storage only for explicitly disposable demonstrations where restart data loss and limited concurrency are acceptable.
- Never keep private user data in shared global state.
- Document consistency, concurrency, transaction, isolation, retention, backup, recovery, and rollback decisions.
- Prefer roll-forward migrations in real environments; never rewrite applied history silently.
- Never invent or commit database credentials, execute destructive operations, deploy, commit, or push without explicit authorization.
- Mark missing facts `TBD`, `Assumption`, or `Not applicable`.

## Common handoff

Report **Status** (`completed`, `partial`, `blocked`), **Scope**, **Files**, **Requirements**, **Checks** as `passed`/`failed`/`not run`/`not applicable`, **Assumptions**, **Risks**, **Decisions** with evidence, and **Handoff** including model, repository contract, migrations, fixtures, commands, gaps, and rollback. Never claim an unexecuted check passed.
