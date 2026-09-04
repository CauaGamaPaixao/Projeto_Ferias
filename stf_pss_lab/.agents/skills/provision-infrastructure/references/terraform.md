# Terraform guidance

Use only when Terraform is requested or already part of the approved infrastructure.

- Pin Terraform and provider constraints based on executable configuration and authoritative compatibility evidence.
- Organize modules/environments proportionately; avoid premature abstraction.
- Keep credentials and secret values outside configuration, plans, outputs, and logs.
- Define remote state, encryption, access, backup, and locking; mark each `TBD` when not provided.
- Use variables for environment-specific values and safe examples without real identifiers/credentials.
- Run `terraform fmt` and `terraform validate` when Terraform exists. Run plan only when non-mutating access and scope are authorized; review destructive replacements and sensitive output.
- Prefer safe lifecycle/migration sequencing and document rollback or recovery. Never run `terraform apply` or `destroy`, import/move state, or create external resources without explicit authorization.
