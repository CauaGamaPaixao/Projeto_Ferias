# In-memory storage guidance

Use only for disposable prototypes or tests when data loss on restart is accepted, durability is not required, and concurrency/multi-instance behavior is not expected.

- State the limitation prominently in documentation and handoff.
- Hide storage behind a repository boundary if durable replacement is plausible.
- Do not store private user data in a process-global collection shared across users.
- Define identity generation, validation, update/delete behavior, ordering, thread/concurrency safety, and reset behavior.
- Use deterministic fixtures where useful and prevent test state leakage.
- Test repository semantics and isolation.
- Classify persistence as inappropriate/blocked when requirements need durability, multi-instance consistency, backup, auditability, or real multi-user isolation.
