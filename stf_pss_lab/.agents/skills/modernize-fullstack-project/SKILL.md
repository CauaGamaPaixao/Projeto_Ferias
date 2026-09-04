---
name: modernize-fullstack-project
description: Assess, plan, and execute safe incremental modernization of an existing full-stack application while preserving behavior. Use for framework or runtime upgrades, dependency modernization, legacy refactoring, architecture evolution, persistence or authentication upgrades, repository cleanup, and migration planning. Do not use for greenfield application generation, isolated bug fixes, or automatic big-bang rewrites.
---

# Modernize a full-stack project

Modernize an existing application through evidence, compatibility checks, incremental changes, verification, and rollback planning.

Preserve all preexisting user changes. Never claim a verification passed unless it executed successfully.

## 1. Guided target selection

Before analysis, resolve exactly one project target:

1. If no project path was provided, ask for an absolute path or a path relative to the current workspace. Do not inspect the agent repository, current workspace, or a nearby project as a substitute.
2. Resolve the candidate to a canonical absolute path and show it to the user. Confirm that the directory exists and that it is readable before inspecting project contents.
3. If the path contains multiple plausible applications or project roots, list the candidates and ask the user to select exactly one. Do not begin analysis until that ambiguity is resolved.
4. Find the applicable Git root from the selected project. Run all Git commands from that Git root; never run `git init` and never copy the project automatically.
5. Read every applicable `AGENTS.md` from the filesystem hierarchy and selected project before further inspection.
6. Detect frontend, backend, manifests, lockfiles, build files, tests, runtime configuration, migrations, CI, containers, and deployment files within the selected target. Identify the stacks from executable configuration.
7. Check and report read access and write access separately. Do not request unrestricted access. A selected path grants neither write permission nor authorization to edit.
8. Keep the validated absolute target path for the conversation. Change it only when the user explicitly selects another project, then repeat validation and intake for the new target.

If the directory does not exist, cannot be resolved, is not readable, or is not a single project target, stop before analysis, report the specific error, request another path or one candidate selection, and confirm that no files were changed.

## 2. Guided modernization intake

Before establishing the baseline, collect and confirm:

- project path, resolved through Guided target selection;
- modernization objective;
- initial mode: **Assessment**, **Plan**, or **Execute**; recommend **Assessment**;
- behaviors, journeys, APIs, UI, data, and operational contracts that must be preserved;
- constraints, exclusions, protected files or components, and prohibited changes.

Ask only for missing information. If the skill is invoked without a path, request the project path, objective, and initial mode together, plus preserved behaviors and constraints or exclusions. A safe reversible default is **Assessment**, but do not invent the objective or target.

After validating the path, show a target summary containing:

- absolute path;
- Git root, or `not applicable` when the selected project is not in a Git worktree;
- components found;
- detected stacks and evidence manifests;
- applicable `AGENTS.md` instructions;
- Git state, obtained from the correct Git root;
- read status;
- write status, checked separately;
- operating mode;
- modernization objective;
- protected scope: preserved behaviors, constraints, and exclusions.

Read-only access may support **Assessment** or **Plan** when inspection is safe and permitted. Block **Execute** without confirmed write access and confirm that no files were changed. Even with write access, do not enter **Execute** until the user explicitly authorizes a named phase and its scope. Supplying or selecting a path is never authorization to edit.

## 3. Select the operating mode

Infer one of these modes from the request and state it before proceeding:

- **Assessment:** inspect and report only; do not change files.
- **Plan:** produce a prioritized migration plan; do not change files.
- **Execute:** implement explicitly approved modernization steps.

Default to **Assessment** when authorization to edit is unclear. Never turn a request to analyze, assess, review, or plan into implementation.

For **Execute**, require both confirmed write access and explicit authorization for a specific phase. If either is absent, remain in **Assessment** or **Plan**, report the blocker, and confirm that no files were changed.

## 4. Establish the baseline

1. Read the nearest `AGENTS.md` files and inspect Git status.
2. Locate frontend, backend, manifests, lockfiles, build files, runtime configuration, migrations, tests, CI, containers, and deployment files.
3. Read [references/assessment-framework.md](references/assessment-framework.md).
4. Record current runtime and framework versions from executable configuration, not README claims.
5. Identify user journeys, public contracts, persistence behavior, authentication, integrations, and operational constraints that must remain compatible.
6. Run existing tests and builds when the environment permits. Record each result as `passed`, `failed`, or `not run`.
7. Treat existing failures as baseline evidence; do not attribute them to modernization work.

If documentation conflicts with manifests or source, report the drift and treat executable configuration as the effective technical truth.

## 5. Research supported migration paths

Use an available documentation MCP such as Context7 for current framework and library guidance. If it is unavailable, consult authoritative vendor documentation.

For each important recommendation, record:

- installed version and evidence location;
- proposed target version;
- source and source version;
- compatibility requirements;
- breaking changes and deprecations;
- migration sequence;
- uncertainty or version mismatch.

Never select a target solely because it is the newest release. Prefer a supported version compatible with the organization's runtime, dependencies, deployment environment, and migration budget.

For Angular work, read [references/angular-modernization.md](references/angular-modernization.md). For Spring Boot work, read [references/spring-modernization.md](references/spring-modernization.md).

## 6. Build the modernization backlog

Classify findings:

- **Security:** exposed secrets, unsupported dependencies, weak authentication, authorization gaps.
- **Runtime and framework:** Java, Node.js, Angular, Spring Boot, build tools.
- **Dependencies:** obsolete, vulnerable, duplicated, incompatible, or unused packages.
- **Architecture:** coupling, business rules in delivery layers, unstable boundaries, missing contracts.
- **Data:** in-memory state, schema evolution, migrations, compatibility, rollback.
- **Quality:** missing tests, failing tests, linting, formatting, static analysis, CI.
- **Operations:** configuration, observability, containers, infrastructure, deployment, rollback.
- **Repository hygiene:** generated output, installed dependencies, secrets, duplicate source trees.

Score each item by user value, risk reduction, urgency, effort, dependency, and reversibility. Separate quick wins from prerequisite work and high-risk migrations.

## 7. Design an incremental plan

Read [references/migration-checklist.md](references/migration-checklist.md), then produce phases that keep the application recoverable:

1. Baseline and characterization tests.
2. Repository hygiene and reproducible builds.
3. Low-risk dependency updates.
4. Runtime or framework prerequisites.
5. One framework major-version step at a time when required by supported upgrade paths.
6. Architecture, persistence, authentication, or infrastructure changes as separate slices.
7. Regression, integration, system, and smoke validation.
8. Documentation, deployment, monitoring, and rollback confirmation.

For every phase define scope, files or components affected, prerequisites, acceptance criteria, commands, risks, rollback, and an independent checkpoint.

Do not combine unrelated frontend, backend, database, authentication, and infrastructure migrations into a single unreviewable change.

## 8. Execute only approved phases

When the user explicitly authorizes implementation:

1. Confirm the approved phase and excluded scope.
2. Start from a clean or clearly understood working tree.
3. Add characterization tests before changing behavior that lacks coverage.
4. Make the smallest coherent change.
5. Update configuration, code, tests, and documentation together.
6. Run narrow checks after each step and full relevant checks at the phase boundary.
7. Stop on an unexpected breaking change, data-loss risk, security boundary change, or required scope expansion.
8. Never rotate credentials, rewrite Git history, delete production data, or deploy without explicit authorization.

Preserve API, UI, and data compatibility unless the approved plan explicitly changes a contract and includes consumer migration.

## 9. Report evidence

End with:

- operating mode;
- current-state inventory;
- documentation sources and version gaps;
- prioritized findings;
- completed or proposed phases;
- acceptance-criteria evidence;
- commands with `passed`, `failed`, or `not run`;
- behavior or contracts intentionally changed;
- remaining risks and technical debt;
- rollback or recovery instructions;
- recommended next phase.

Never describe an assessment as a completed modernization and never claim a check passed without running it.

## 10. Coordinate specialist skills

When an approved phase affects frontend, backend, persistence, authentication, testing, documentation, or infrastructure, read and use only the applicable specialist `SKILL.md` files. Preserve modernization ownership of baseline, compatibility, incremental sequencing, checkpoints, and rollback. Do not let specialist execution turn modernization into an automatic rewrite or broaden the approved phase.

Always coordinate affected validation with `test-fullstack-project` and update evidence-based project records with `generate-project-documentation`. Use `provision-infrastructure` only for explicitly authorized infrastructure scope.

## 11. Common handoff

Report **Status** (`completed`, `partial`, or `blocked`), **Scope** executed/not executed, **Files** created/altered/removed, **Requirements** satisfied/pending/not applicable, **Checks** grouped by `passed`/`failed`/`not run`/`not applicable`, **Assumptions**, **Risks**, **Decisions** with evidence, and **Handoff** containing baseline, compatibility findings, phase checkpoint, rollback, affected contracts, and next-skill instructions.

Do not invent stakeholders, approvals, costs, dates, credentials, environments, versions, or results. Mark unknown information `TBD`, `Assumption`, or `Not applicable`. Do not commit, push, deploy, or mutate infrastructure without explicit authorization.
