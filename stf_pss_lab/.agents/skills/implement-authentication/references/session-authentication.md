# Session authentication

Use when browser/server architecture, revocation, and centralized session control favor opaque server-managed sessions.

- Store only an opaque session identifier in the client; keep authoritative state server-side or in an approved session store.
- Rotate identifiers on login and privilege changes; invalidate on logout and expiration.
- Configure cookies with appropriate `Secure`, `HttpOnly`, `SameSite`, path, domain, and lifetime.
- Implement CSRF protection for state-changing requests when cookie credentials are automatically attached.
- Define multi-instance session storage, cleanup, concurrent sessions, and failover when applicable.
- Do not place sensitive application data in client-visible session payloads.
- Test fixation defenses, cookie flags, CSRF, expiration, logout invalidation, unauthorized and forbidden access, and cross-user isolation.
