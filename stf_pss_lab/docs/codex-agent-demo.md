# Codex CLI project-builder demonstration

## What was built

The agent is the Codex CLI itself, configured through four complementary layers:

1. `AGENTS.md` supplies persistent repository behavior and quality rules.
2. `.agents/skills/build-fullstack-project/` supplies the reusable requirements-to-delivery workflow.
3. Skill references retain lessons from BrasilDrop and guide stack selection without hard-coding one domain.
4. MCP configuration gives Codex current external documentation when local knowledge is insufficient.

This replaces the earlier architecture in which a Spring application called Gemini and attempted to host the agents. The `CopaAssistant` may remain a marketplace feature, but it is not the project-generation agent.

## Prepare the CLI

From the repository root:

```bash
codex --version
codex mcp add context7 -- npx -y @upstash/context7-mcp
codex mcp list
codex
```

Inside the Codex TUI, use `/skills` to confirm `build-fullstack-project` and `/mcp` to confirm Context7. The checked-in `.codex/config.toml.example` is an alternative configuration reference.

## Demonstration prompt

```text
$build-fullstack-project

Crie um sistema web de agendamento para uma barbearia. Clientes devem consultar horários e reservar um serviço; administradores devem cadastrar profissionais, serviços e bloquear horários. Use Java 17, Spring Boot, Thymeleaf e H2 para a demonstração. Inclua validações, dados iniciais, testes automatizados e instruções de execução. Antes de implementar, apresente critérios de aceite e um plano curto. Não adicione integrações de pagamento.
```

This prompt demonstrates that the workflow is domain-agnostic while constraining the stack so the live demo remains predictable and quick.

## Expected agent flow

1. Inspect instructions, repository state, and available tools.
2. Extract actors, journeys, rules, data, and acceptance criteria.
3. Ask only material questions and state reversible assumptions.
4. Plan vertical slices.
5. Generate a runnable application with tests.
6. Execute checks and report `passed`, `failed`, or `not run` honestly.

## Five-minute presentation

1. **Problem:** the first version placed Gemini agents inside the application, reversing host and agent roles.
2. **Correction:** Codex is now the host agent; repository instructions shape behavior, the skill defines the reusable workflow, and MCP supplies external capabilities.
3. **Learning:** BrasilDrop contributes proven patterns—thin controllers, services, repositories, externalized secrets, tests, and handoff commands—without forcing every project to be a Spring marketplace.
4. **Demo:** invoke the skill with the barbershop prompt and show its requirements, plan, generated files, and test evidence.
5. **Evolution:** add company-specific skills or package the workflow as a plugin when it must be distributed with connectors to the wider team.

## Evaluation evidence

- Agnostic input: the skill description is about complete web applications, not marketplaces.
- Learned context: `references/projeto-ferias-patterns.md` records both reusable strengths and limitations.
- Correct stack behavior: explicit constraints win; otherwise `references/stack-selection.md` guides a reasoned choice.
- MCP usage: Context7 is configured for current framework documentation.
- Quality gates: `AGENTS.md` and the delivery checklist require tests, build evidence, security hygiene, and acceptance mapping.
