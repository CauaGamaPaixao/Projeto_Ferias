# Unit testing

- Test isolated domain rules, use cases, validation, normalization, state reducers/stores, and error decisions.
- Prefer observable input/output behavior; avoid coupling to private methods or framework internals.
- Use deterministic data and control time/randomness where relevant.
- Cover happy paths, boundaries, invalid input, and representative failures.
- Mock only true collaborators at the unit boundary; do not mock the unit under test.
- Keep unit tests fast and independent, but do not claim they validate wiring, serialization, persistence engines, browsers, or network contracts.
- Run the configured non-watch command and record exact output/state.
