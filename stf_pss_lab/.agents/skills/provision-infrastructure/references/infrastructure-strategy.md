# Infrastructure strategy

- Start from deployment requirements and existing operational conventions, not preferred tools.
- Define system boundary, deployment units, environments, configuration, secrets, networking, persistence dependencies, availability, scaling, observability, backup/recovery, rollback, and ownership when known.
- Prefer the fewest deployable units and services that satisfy the briefing.
- Separate build artifacts from runtime configuration and keep secrets in an approved external mechanism.
- Make health, logging, metrics, alerts, and operational runbooks proportionate to risk.
- Record costs, accounts, regions, approvals, and environments as `TBD` when not supplied.
- Distinguish local file generation/static validation from external provisioning/deployment.
- Require explicit authorization for every external mutation and preserve a recoverable rollback path.
