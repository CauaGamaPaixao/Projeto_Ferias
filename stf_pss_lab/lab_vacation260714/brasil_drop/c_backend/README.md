# BrasilDrop backend

API REST em Java 17 e Spring Boot 3.3.8 para catálogo, autenticação demonstrativa, carrinho, favoritos, checkout simulado e Copa Assistant. O frontend é a aplicação Angular em `../b_frontend`; este módulo não usa Thymeleaf nem renderização HTML no servidor.

## Pré-requisitos

- Java 17.
- Acesso à internet na primeira execução do Maven Wrapper e para as integrações ViaCEP/Gemini.

O Maven Wrapper fixa Maven 3.9.9, portanto não é necessária uma instalação global do Maven.

## Configuração

`GEMINI_API_KEY` é opcional para catálogo, autenticação, carrinho e checkout, mas obrigatória para o Copa Assistant. A chave não deve ser gravada no código.

O modelo efetivo é `gemini-2.5-flash`, definido em `src/main/resources/application.properties`. Não existe atualmente suporte executável à variável `GEMINI_MODEL`.

No PowerShell:

```powershell
$env:GEMINI_API_KEY="SUA_CHAVE"
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
export GEMINI_API_KEY="SUA_CHAVE"
./mvnw spring-boot:run
```

A API inicia em `http://localhost:8080`.

## Testes e empacotamento

No Windows:

```powershell
.\mvnw.cmd test
.\mvnw.cmd package
```

No Linux ou macOS:

```bash
./mvnw test
./mvnw package
```

O JAR é gerado em `target/brasil-market-0.0.1-SNAPSHOT.jar`.

## Estrutura

```text
src/main/java/br/com/projetoferias/
  assistant/   Orquestração do Copa Assistant
  config/      Configuração web e CORS
  controller/  Endpoints REST e DTOs
  gemini/      Cliente da API Gemini
  model/       Records e enums do domínio
  repository/  Contrato e catálogo em memória
  service/     Casos de uso e integrações
```

## Limitações atuais

- Usuários, produtos, carrinho, favoritos e pedidos não usam persistência durável.
- A autenticação é demonstrativa e não é apropriada para produção.
- ViaCEP e Gemini são integrações externas e dependem de conectividade.

Essas limitações pertencem a fases futuras e não são alteradas pela fase de higiene e runtimes.
