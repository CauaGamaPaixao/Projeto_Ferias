# Stack selection guide

Choose from requirements and team constraints, not personal preference.

| Signal | Good starting profile | Main tradeoff |
|---|---|---|
| CRUD/MVP, SEO, forms, small team, Java required | Spring Boot + Thymeleaf + relational DB | Fast single-repo delivery; less client-side interactivity |
| Rich browser interactions and separate API | React/Angular/Vue + typed API backend | Clear frontend boundary; more tooling and contracts |
| TypeScript end to end | React/Next.js + Node/Nest/Express + PostgreSQL | Shared language; requires disciplined layering |
| Enterprise Java/API-first | Spring Boot REST + PostgreSQL + chosen frontend | Strong ecosystem; more setup than a small monolith |
| Data/automation-heavy service | FastAPI/Django + PostgreSQL + suitable UI | Excellent Python ecosystem; choose Django for built-in admin/ORM |
| Static content or simple landing experience | Static HTML/CSS/JS or static-site framework | Minimal operations; unsuitable for complex server state |

## Decision order

1. Honor explicit language, framework, runtime, database, deployment, and company standards.
2. Prefer the existing stack when extending a healthy repository.
3. Match architecture to user journeys, data consistency, security, SEO, interactivity, scale, and team skills.
4. Prefer maintained stable versions verified from official sources at implementation time.
5. Minimize independent services, build systems, and deployment units.

## Required decision note

Record the selected stack, decisive requirements, one rejected alternative, and the reason. Avoid claiming a stack is universally best.
