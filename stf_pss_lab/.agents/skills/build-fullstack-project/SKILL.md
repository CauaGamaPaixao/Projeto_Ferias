---
name: build-fullstack-project
description: Generate or substantially scaffold a complete web application from natural-language requirements, including stack selection, frontend, backend, integrations, tests, and handoff. Use for new project creation, MVP generation, full-stack prototypes, or reproducing the delivery style learned from Projeto_Ferias. Do not use for isolated bug fixes, tiny edits, or tasks that already prescribe a narrower specialist workflow.
---

# Build a full-stack project

Deliver a working application from requirements while adapting the solution to the requested domain and constraints.

## 1. Inspect and frame

1. Read the nearest `AGENTS.md` files and inspect the workspace with `rg --files`, manifests, tests, and Git status.
2. Convert the briefing into actors, user journeys, business rules, data, integrations, non-functional constraints, and acceptance criteria.
3. Ask only questions whose answers materially change architecture, security, data ownership, or scope. For reversible details, document an assumption and proceed.
4. Create a short implementation plan with independently verifiable vertical slices.

## 2. Choose the architecture

1. Respect an explicitly requested stack.
2. When the stack is open, read [references/stack-selection.md](references/stack-selection.md) and select the smallest adequate profile.
3. When using lessons from BrasilDrop, read [references/projeto-ferias-patterns.md](references/projeto-ferias-patterns.md). Reuse its principles, not its domain names or accidental limitations.
4. Confirm runtime versions and commands from authoritative documentation or an available documentation MCP when the choice may have changed.
5. Record the decision and its tradeoffs in the project documentation.

## 3. Design before bulk generation

Define:

- bounded features and routes/screens;
- domain entities, invariants, and relationships;
- API or server-rendered contracts;
- persistence and external integration boundaries;
- authentication/authorization needs;
- error behavior and validation;
- test strategy and acceptance mapping.

Avoid speculative abstractions. Create interfaces at volatile boundaries such as persistence and third-party APIs, not for every class.

## 4. Implement in vertical slices

For each slice:

1. Implement the domain rule and application behavior.
2. Add the delivery layer: UI route, controller, endpoint, or command.
3. Add persistence/integration adapters only when needed.
4. Add tests for the happy path, important validation, and failure behavior.
5. Run the narrowest relevant verification before starting the next slice.

Keep the app runnable after every slice. Use accessible, responsive UI states for loading, empty data, success, validation, and failures when applicable.

## 5. Handle integrations safely

- Keep credentials in environment variables and provide placeholder configuration.
- Add timeouts and translate upstream failures into useful application errors.
- Do not silently replace a requested real integration with mock data. If credentials or access are unavailable, implement a clearly labeled adapter or fallback and state what remains unverified.
- Use available MCP tools for current documentation or external-system context when appropriate; do not treat MCP as required application architecture.

## 6. Validate and hand off

Read [references/delivery-checklist.md](references/delivery-checklist.md), then:

1. Run tests, build, lint, formatting checks, and smoke checks supported by the project.
2. Search for likely leaked secrets and generated artifacts before finalizing.
3. Map every acceptance criterion to evidence: test, command output, or manual check.
4. Document setup, environment variables, run/test commands, architecture, and known limitations.
5. Summarize created behavior, decisions, verification results, assumptions, and remaining risks.

Never report success from inspection alone. Distinguish `passed`, `failed`, and `not run`.
