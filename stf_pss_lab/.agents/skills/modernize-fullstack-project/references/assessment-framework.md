# Modernization assessment framework

## Inventory

Capture evidence rather than assumptions:

| Area | Evidence examples |
|---|---|
| Runtime | `.java-version`, `pom.xml`, Gradle files, `.nvmrc`, `package.json`, CI images |
| Framework | parent POM, dependency management, Angular packages, framework configuration |
| Build | wrapper versions, scripts, lockfiles, build plugins, reproducibility |
| Architecture | entry points, modules, controllers, services, repositories, integrations |
| Data | database engine, entities, migrations, seed data, backups |
| Security | authentication, authorization, secret handling, dependency findings |
| Quality | unit, integration, system tests, coverage, lint, static analysis |
| Operations | environments, containers, infrastructure, logs, metrics, deployment |

## Finding format

For each finding record:

- **Evidence:** exact file, configuration, or command result.
- **Impact:** user, security, reliability, maintainability, or operations.
- **Risk if unchanged:** what can realistically fail or become unsupported.
- **Recommendation:** smallest useful improvement.
- **Effort:** small, medium, or large.
- **Migration risk:** low, medium, high, or critical.
- **Prerequisites:** tests, runtime, dependency, data, or organizational decisions.
- **Rollback:** how to return to the last known-good state.

## Priority guidance

1. Active security exposure and data-loss risk.
2. Unsupported runtime or framework blocking maintenance.
3. Missing tests around behavior about to change.
4. Reproducible build and repository hygiene.
5. Low-risk dependency and tooling upgrades.
6. Framework migrations.
7. Architecture improvements justified by measurable pain.
8. Optional developer-experience improvements.

Do not label age alone as a defect. Connect recommendations to support, risk, cost, or user value.
