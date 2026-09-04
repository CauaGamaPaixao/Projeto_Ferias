# Vanilla frontend guidance

Use for HTML, CSS, and JavaScript/TypeScript without a component framework.

- Separate semantic markup, design tokens/styles, application state, persistence, API clients, and DOM event wiring.
- Use modules and focused functions instead of a single global script.
- Avoid global mutable business state and inline handlers.
- Render untrusted content through safe text APIs; do not inject unsanitized HTML.
- Use native controls and platform behavior before recreating widgets.
- Test pure behavior and representative DOM interactions with the existing toolchain.
- Do not introduce a framework solely for organization. Consult authoritative browser/tool documentation for compatibility-sensitive behavior.
