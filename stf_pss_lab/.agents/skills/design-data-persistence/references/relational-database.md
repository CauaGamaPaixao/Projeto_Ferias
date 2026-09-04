# Relational database guidance

- Select an engine from explicit requirements, existing configuration, operational support, consistency needs, and deployment constraints; do not choose by habit.
- Model stable identities, nullability, relationships, uniqueness, referential integrity, and domain constraints explicitly.
- Define transaction boundaries in application services and avoid leaking persistence concerns into controllers.
- Add indexes based on access patterns and validate their write/storage tradeoffs.
- Document isolation, locking, optimistic/pessimistic concurrency, pagination, retention, backup, restore, and ownership boundaries.
- Keep repository tests representative of the selected engine when engine behavior matters.
- Source connection settings and credentials externally. Use placeholders only.
- Consult Context7 or official driver/ORM/database documentation for installed-version compatibility; do not upgrade dependencies automatically.
