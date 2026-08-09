# Projeto_Ferias reference patterns

## Source profile

The reference application lives in `lab_vacation260714/brasil_drop/` and uses a separated frontend and backend architecture.

The frontend lives in `b_frontend/` and uses Angular 17, TypeScript, Angular Router, HttpClient, FormsModule, Jasmine, and Karma.

The backend lives in `c_backend/` and uses Java 21, Spring Boot 3, Maven, REST APIs, JUnit 5, and Mockito.

Its primary user journeys are authentication, product catalog search and filtering, wishlist management, shopping cart, checkout, ViaCEP address lookup, and an optional Gemini-backed assistant.

Use the following directories as the source of truth:

- `b_frontend/src/` for frontend source code.
- `c_backend/src/main/` for backend source code.
- `b_frontend/src/**/*.spec.ts` for Angular tests.
- `c_backend/src/test/` for backend tests.

Ignore generated or installed content such as:

- `b_frontend/node_modules/`
- `b_frontend/.angular/`
- `b_frontend/dist/`
- `c_backend/target/`

## Patterns worth carrying forward

### General architecture

- Separate frontend presentation concerns from backend business rules.
- Use an API contract to define communication between frontend and backend.
- Prefer the smallest architecture that satisfies the project's requirements.
- Organize functionality around complete user journeys rather than isolated technical layers.
- Keep credentials and environment-specific configuration out of source code.
- Provide exact commands for setup, execution, testing, and build.

### Frontend patterns

- Organize Angular code into pages, reusable components, services, guards, and domain models.
- Keep HTTP communication inside Angular services rather than components.
- Keep page components focused on presentation state and user interactions.
- Use Angular Router for navigation and route guards for protected flows.
- Represent API responses with explicit TypeScript interfaces or models.
- Reuse shared components for repeated interface elements such as navigation.
- Add tests for components, services, guards, validation, and important user interactions.
- Include loading, empty, success, validation, and error states when applicable.
- Maintain responsive and accessible interfaces.

### Backend patterns

- Organize backend code by clear responsibilities: controller, service, repository, model, configuration, DTO, and integration adapter.
- Use constructor injection and keep controllers thin.
- Keep filtering, authentication, cart totals, checkout, and other business rules inside services.
- Use DTOs at API boundaries instead of exposing internal implementation details unnecessarily.
- Hide storage behind repository contracts when persistence may change.
- Keep third-party HTTP calls behind service or integration boundaries.
- Source API keys and credentials from environment variables.
- Translate integration failures into useful and safe API responses.
- Test business behavior with JUnit and Mockito, including important integration failure cases.

### Full-stack integration patterns

- Keep frontend API paths and backend routes consistent.
- Use development proxy configuration when the frontend and backend run on different local ports.
- Validate data at both the user interface and backend trust boundaries.
- Document required environment variables and external service dependencies.
- Keep the application usable when an optional integration, such as the Gemini assistant, is unavailable whenever the requirements permit it.

## Verification commands

Run frontend checks from:

```text
lab_vacation260714/brasil_drop/b_frontend/
```

Use:

```bash
npm install
npm test
npm run build
```

Run backend checks from:

```text
lab_vacation260714/brasil_drop/c_backend/
```

Use:

```bash
mvn test
mvn package
```

Do not report these checks as successful unless the commands were actually executed and completed successfully.

## Limitations not to copy by default

- In-memory users, products, carts, wishlists, and orders are suitable only for demonstrations or explicitly temporary prototypes.
- Plain-text or demonstration authentication is not production-grade authentication.
- Session-based shopping state may not satisfy distributed deployment, concurrency, persistence, or scaling requirements.
- A separated Angular frontend and Spring Boot backend are not automatically the correct architecture for every project.
- Angular 17 and Java 21 are characteristics of this reference project, not mandatory versions for every generated application.
- Gemini is a product feature in the sample, not part of the project-generation agent itself.
- ViaCEP is a domain-specific integration and should only be reused when the new project requires Brazilian address lookup.
- Committed `node_modules/`, `.angular/`, `dist/`, or `target/` content is a repository hygiene problem and must not be reproduced.
- Limited end-to-end, controller, security, and integration testing leaves important behavior insufficiently verified.
- Demo credentials and hard-coded sample data must not be treated as production configuration.
- Splitting frontend and backend introduces API-contract, CORS, deployment, and version-coordination costs that must be justified by the new project.

## Generalization rule

Extract architectural intent, responsibility boundaries, quality gates, testing habits, integration practices, and delivery workflows.

Never automatically copy:

- Marketplace-specific entities or business rules.
- Brazil-themed styling or product data.
- Package names and directory names.
- Angular, Spring Boot, or specific runtime versions.
- Gemini, ViaCEP, or other external integrations.
- Demo credentials or in-memory persistence.
- API routes from the reference project.

Select the architecture, stack, persistence, integrations, and deployment model from the requirements of each new project. Explicit user and company constraints take precedence over the reference implementation.
