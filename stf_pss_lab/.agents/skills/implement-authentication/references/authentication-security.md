# Authentication security foundation

## Core controls

- Verify identity without revealing whether an account exists unnecessarily.
- Hash passwords with an adaptive, salted algorithm and parameters appropriate to current authoritative guidance and the installed stack.
- Enforce authorization server-side at resource and ownership boundaries; deny by default.
- Apply least privilege and keep roles/permissions explicit and testable.
- Rotate session identifiers after authentication and privilege changes.
- Define idle/absolute expiration, logout/revocation, concurrent-session behavior, recovery, and audit needs.
- Rate-limit or otherwise mitigate credential attacks when applicable without creating denial-of-service risks.
- Protect secrets in external configuration and redact sensitive logs.

## Browser threats

Assess CSRF for cookie credentials, CORS for actual trusted origins, XSS impact on any browser-held credential, cookie `Secure`/`HttpOnly`/`SameSite`, clickjacking, and cache behavior. Frontend guards do not authorize resources.

## Verification

Test positive and negative authentication, logout, expiry, fixation, unauthorized/forbidden distinctions, role changes, and cross-user ownership. Record limitations and `not run` checks honestly.
