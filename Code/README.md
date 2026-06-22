# Brasil Drop

Marketplace web inspirado na Droper, focado em chuteiras e artigos esportivos da selecao brasileira para o clima de copa.

O projeto usa Java 17, Spring Boot e Thymeleaf no backend. O frontend foi mantido em HTML e CSS, com a interacao feita por formularios renderizados pelo servidor.

## Funcionalidades

- Cadastro e login de usuario.
- Catalogo de produtos esportivos da selecao brasileira.
- Filtro por categoria e busca por texto.
- Lista de desejos com produtos favoritos.
- Carrinho de compras em sessao.
- Checkout com simulacao de pagamento.
- Consulta de endereco pela API ViaCEP a partir do CEP informado.

## Como rodar

### Pre-requisitos

- Java 17 ou superior.
- Maven 3.9 ou superior.
- Conexao com internet para baixar dependencias Maven e consultar a API ViaCEP.

### Inicializacao

No terminal, dentro da pasta do projeto:

```bash
mvn spring-boot:run
```

Depois acesse:

```text
http://localhost:8080
```

Conta demo:

```text
E-mail: demo@brasilmarket.com
Senha: 123456
```

### Build

```bash
mvn test
```

```bash
mvn package
```

Para executar o JAR gerado:

```bash
java -jar target/brasil-market-0.0.1-SNAPSHOT.jar
```

## Estrutura

```text
src/main/java/br/com/projetoferias
  controller/   Rotas web de autenticacao, catalogo, carrinho e checkout
  model/        Records e enums do dominio
  service/      Regras de negocio, catalogo em memoria, sessao e ViaCEP

src/main/resources/templates
  Paginas HTML renderizadas pelo Thymeleaf

src/main/resources/static/css
  Estilos CSS da aplicacao
```

## Como interpretar a task do gestor

### Hyper: Viber code

Provavelmente o gestor quis indicar um ambiente ou ferramenta de codificacao assistida. "Hyper" pode estar ligado a produtividade, automacao ou execucao rapida de tarefas. "Viber code" parece uma referencia ambigua; pode ser um nome interno, extensao, ferramenta ou ate um erro de digitacao para alguma ferramenta de geracao/validacao de codigo.

Ideia para este projeto: documentar no Obsidian ou no README quais prompts, comandos e decisoes foram usados para criar a aplicacao. Se houver uma ferramenta especifica chamada Viber Code na empresa, ela pode ser usada para gerar partes repetitivas, como entidades, DTOs, controllers e testes.

### IDE: VSCode

O projeto deve ser facil de abrir, rodar e depurar no Visual Studio Code.

Ideia para este projeto: instalar as extensoes Extension Pack for Java e Spring Boot Extension Pack. Depois abrir a pasta no VSCode, rodar `BrasilMarketApplication` pela aba Run and Debug ou executar `mvn spring-boot:run` no terminal integrado.

### Skill

"Skill" indica uma capacidade ou procedimento reutilizavel. No contexto da task, pode ser uma habilidade documentada para guiar o desenvolvimento, como "criar marketplace Spring com checkout simulado" ou "integrar ViaCEP".

Ideia para este projeto: transformar este README em base de uma skill interna, contendo passos, padroes, comandos, estrutura esperada e criterios de aceite.

### Brain: Obsidian, Outras Alternativas, e ate uma hands on

"Brain" sugere uma base de conhecimento pessoal ou compartilhada. Obsidian e uma boa opcao para registrar arquitetura, decisoes, backlog e aprendizados. "Outras alternativas" podem incluir Notion, Confluence, Logseq ou Markdown no proprio repositorio. "Hands on" indica demonstrar a aplicacao funcionando na pratica.

Ideia para este projeto:

- Criar uma nota no Obsidian chamada `Brasil Drop - Marketplace`.
- Registrar requisitos, endpoints, fluxo de usuario e pendencias.
- Fazer uma hands on mostrando cadastro, login, favoritos, carrinho, CEP e pagamento simulado.

### MCP: Teste, Conversao de Natural2Cobol

MCP significa Model Context Protocol. Ele permite conectar agentes de IA a ferramentas externas, repositorios, arquivos, bancos, APIs ou sistemas internos.

"Teste" indica experimentar MCPs no fluxo de desenvolvimento. "Conversao de Natural2Cobol" provavelmente e um caso de uso separado: usar um agente ou ferramenta para converter codigo Natural para COBOL.

Ideia para este projeto:

- Criar um MCP de teste que exponha dados do catalogo ou pedidos simulados para um agente consultar.
- Criar um MCP que chame a API ViaCEP como ferramenta externa.
- Usar o projeto como laboratorio simples antes de aplicar MCP em um caso mais complexo, como Natural2Cobol.

### Agent: Teste, Conversao de Natural2Cobol

"Agent" indica um agente de IA com objetivo, ferramentas e memoria de contexto. Ele poderia executar tarefas como analisar codigo, gerar testes, migrar trechos de sistema legado ou validar regras.

Ideia para este projeto:

- Criar um agente que leia requisitos e abra issues/tarefas para evolucao do marketplace.
- Criar um agente de QA que teste fluxos como login, favoritos, carrinho e checkout.
- Como paralelo ao Natural2Cobol, criar um agente menor para converter descricoes em portugues para classes Java, templates HTML ou casos de teste.

## Possiveis evolucoes

- Persistir usuarios, produtos, favoritos e pedidos com PostgreSQL ou MySQL.
- Adicionar Spring Security com hash de senha.
- Criar testes automatizados para services e controllers.
- Substituir imagens remotas por assets locais.
- Adicionar painel administrativo para cadastrar produtos.
- Criar endpoints REST para uma futura interface em React, Vue ou mobile.
- Integrar pagamento real em sandbox, como Mercado Pago ou Stripe.
