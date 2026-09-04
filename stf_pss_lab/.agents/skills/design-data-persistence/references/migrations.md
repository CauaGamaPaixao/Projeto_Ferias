# Migration guidance

- Use ordered, version-controlled migrations for durable schema changes.
- Make the current schema reproducible from an empty database and validate upgrades from the supported prior state.
- Separate schema, data backfill, constraint enforcement, and destructive cleanup when safer.
- Design backward/forward compatibility for rolling deployments when required.
- Prefer expand/migrate/contract and roll-forward recovery in real environments.
- Never edit an already applied migration silently or run destructive migrations without explicit authorization and verified backup/recovery.
- Document preconditions, locks/downtime, data volume, rollback or compensating migration, and verification queries.
- Test migrations against the selected engine when possible. Classify unexecuted environment validation as `not run`.
