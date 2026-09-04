# GitHub Actions guidance

Use only when GitHub Actions exists or is explicitly selected.

- Preserve existing workflow intent and least-privilege `permissions`.
- Pin action references according to organizational policy; verify current supported action/runtime documentation.
- Use repository/environment secrets and OIDC where appropriate; never echo credentials or expose untrusted inputs to shell execution.
- Separate validation, build, test, package, and deployment gates. Add deployment only with explicit scope and approvals.
- Use dependency caches carefully; never cache secrets or replace lockfile-based installation.
- Constrain pull-request workflows from forks and avoid privileged execution of untrusted code.
- Validate YAML and applicable local workflow checks. Do not trigger, modify, or publish workflows outside the approved scope.
