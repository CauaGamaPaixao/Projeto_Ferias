# System and E2E testing

Select the existing system-test tool. If none exists and E2E is required, compare Playwright, Cypress, or an equivalent using stack compatibility, browser needs, team conventions, CI support, and maintenance cost; consult current authoritative documentation before adding anything.

- Exercise the deployed/runnable application through public interfaces.
- Cover the principal success journey and a representative failure for full-stack applications.
- Verify frontend/backend contract behavior, navigation, validation, persistence, authentication, errors, and recovery as applicable.
- Use stable user-facing locators and deterministic test data.
- Keep credentials fake and environments explicitly identified; never run destructive cases against shared/production systems without authorization.
- Separate automated E2E results from manual walkthroughs and static inspection.
- Capture command, environment, result, and diagnostic artifacts. A build alone is not system validation.
