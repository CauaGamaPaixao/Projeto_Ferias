# Token authentication

Use when explicit API/mobile/service requirements and trust boundaries justify bearer or proof-based tokens. Do not equate token authentication with JWT automatically.

- Decide opaque versus structured tokens from validation, revocation, disclosure, size, and interoperability needs.
- Validate issuer, audience, signature/MAC, time claims, algorithm constraints, and token type when applicable.
- Keep access tokens short-lived; define refresh rotation, replay detection, revocation, logout, and key rotation when refresh tokens exist.
- Avoid browser storage accessible to scripts unless the threat model explicitly accepts the XSS exposure.
- Restrict scopes/permissions and enforce resource ownership server-side.
- Never log tokens or embed secrets/private keys in source.
- Test invalid signature/format, wrong issuer/audience, expiration, revocation, refresh reuse, insufficient scope, unauthenticated/forbidden access, and cross-user isolation.
