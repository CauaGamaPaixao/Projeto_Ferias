---
name: implement-authentication
description: Design, implement, and verify secure authentication, backend authorization, password storage, sessions or tokens, login, logout, expiration, permissions, and access controls. Use only when authentication or authorization is required by the briefing or selected architecture. Do not use when authentication is unnecessary, to trust frontend guards as authorization, or to choose JWT automatically.
---

# Implement Authentication

Implement only approved authentication and authorization requirements. If none exist, return `not applicable` with the reason. Read repository instructions, threat-relevant requirements, API/persistence contracts, executable configuration, and tests before editing.

Preserve all preexisting user changes. Never claim a verification passed unless it executed successfully.

## References

Always read [authentication security](references/authentication-security.md). Then read exactly one primary mechanism reference:

- [session authentication](references/session-authentication.md);
- [token authentication](references/token-authentication.md).

Choose sessions or tokens from client type, trust boundaries, deployment, revocation, CSRF exposure, and existing architecture. For another mechanism, consult Context7 or authoritative security/framework documentation. Do not load both mechanism references unless comparing a material decision.

## Workflow

1. Confirm identities, login methods, protected resources, ownership, roles/permissions, session/token lifetime, logout, recovery, and acceptance criteria.
2. Document threats and limitations, including credential attacks, session fixation, CSRF, XSS/token theft, CORS, enumeration, authorization bypass, and cross-user access.
3. Coordinate identity persistence and ownership with `design-data-persistence` and API responses with `build-backend`/`build-frontend`.
4. Implement appropriate password hashing, secure credential handling, backend authorization, session/token lifecycle, logout/revocation, expiry, and safe errors/logging.
5. Configure CSRF and CORS according to the selected mechanism and actual origins; do not use permissive defaults silently.
6. Test valid login, invalid login, logout, unauthenticated access, forbidden access, expiration, session fixation defenses, and access to another user's resource.
7. Run configured security, unit, integration, and system checks with `test-fullstack-project`.
8. Return the common handoff.

## Rules

- Never store plaintext or reversibly encrypted passwords; use an appropriate adaptive password-hashing algorithm supported by the installed stack.
- Enforce authorization on the backend for every protected operation and ownership boundary.
- Never log passwords, secrets, session identifiers, reset codes, or bearer tokens.
- Keep secrets external and use placeholders only.
- Do not select JWT by default or place long-lived tokens in insecure browser storage without an explicit threat-based decision.
- Do not invent users, roles, credentials, environments, approvals, or security claims. Use `TBD`, `Assumption`, or `Not applicable`.
- Do not commit, push, deploy, rotate credentials, or mutate external identity infrastructure without explicit authorization.

## Common handoff

Report **Status** (`completed`, `partial`, `blocked`), **Scope**, **Files**, **Requirements**, **Checks** grouped by `passed`/`failed`/`not run`/`not applicable`, **Assumptions**, **Risks**, **Decisions** and evidence, and **Handoff** with threat model, mechanism, authorization rules, configuration, tests, gaps, and rollback. Never claim an unexecuted check passed.
