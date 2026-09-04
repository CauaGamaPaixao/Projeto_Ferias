---
name: provision-infrastructure
description: Design, implement, and statically validate explicitly scoped containers, CI/CD, environment configuration, secrets, observability, and Terraform infrastructure with rollback planning. Use only when infrastructure is requested or justified by deployment requirements. Do not use to add Docker or Terraform automatically, deploy, run terraform apply, create external resources, publish images, or mutate CI without applicable authorization.
---

# Provision Infrastructure

Work only within explicitly authorized infrastructure scope. Default to design and static validation when external mutation is not clearly authorized.

Preserve all preexisting user changes. Never claim a verification passed unless it executed successfully.

## References

Always read [infrastructure strategy](references/infrastructure-strategy.md). Load only applicable references:

- [Docker](references/docker.md) when containers are required;
- [GitHub Actions](references/github-actions.md) when that CI/CD platform is in scope;
- [Terraform](references/terraform.md) when Terraform is requested or already exists.

For other platforms, consult Context7 or authoritative provider/tool documentation. Do not load irrelevant references or pin versions without compatibility evidence.

## Workflow

1. Confirm deployment target, environments, availability, security, data, cost, recovery, CI/CD, observability, and authorization boundaries. Mark unknown values `TBD` or `Assumption`.
2. Inspect existing infrastructure, manifests, workflows, configuration, secret references, and application handoffs.
3. Select the smallest strategy satisfying requirements; record containers, IaC, and pipeline features not needed as `not applicable`.
4. Separate environments when required and keep configuration/secrets external.
5. Implement only approved local files. Never create external state implicitly.
6. Run applicable format, syntax, static, policy, build, `terraform fmt`, `terraform validate`, or non-mutating plan checks.
7. Document plan, state/locking, deployment, observability, rollback, recovery, and unresolved risks.
8. Return the common handoff.

## Safety boundaries

- Never run `terraform apply`, deploy, create/destroy resources, publish images, rotate secrets, or alter external systems without explicit authorization.
- Never create Docker, Terraform, or GitHub Actions merely because the tool exists.
- Pin Terraform providers and action references appropriately; keep credentials out of code and logs.
- Document remote state and locking as `TBD` when unspecified; do not invent environments, accounts, approvals, costs, or endpoints.
- Do not commit, push, or open a pull request without explicit authorization.

## Common handoff

Report **Status** (`completed`, `partial`, `blocked`), **Scope**, **Files**, **Requirements**, **Checks** as `passed`/`failed`/`not run`/`not applicable`, **Assumptions**, **Risks**, **Decisions** with evidence, and **Handoff** including environment/config contracts, commands, plan, rollback, external actions not performed, and next steps. Never claim an unexecuted validation passed.
