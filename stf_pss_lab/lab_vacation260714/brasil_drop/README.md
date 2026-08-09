# BrasilDrop

Marketplace de artigos esportivos temático para a Copa do Mundo 2026, com frontend Angular, API REST Spring Boot e assistente integrado ao Google Gemini.

## Estrutura

```text
brasil_drop/
├── b_frontend/  Aplicação Angular 17
├── c_backend/   API REST Spring Boot 3.3.8 (Java 17)
└── a_doc/       Documentação de produto
```

## Runtimes

| Ferramenta | Versão do projeto |
|---|---|
| Java | 17 |
| Maven | 3.9.9, fornecido pelo Maven Wrapper |
| Node.js | 20.19.6 LTS, fixado em `b_frontend/.nvmrc` |
| npm | 10 ou 11 |
| Angular | 17.3.x |
| Spring Boot | 3.3.8 |

> Angular 17.3 declara suporte oficial a Node `^18.13.0 || ^20.9.0`; Node 20.19.6 pertence a essa matriz. A linha Node 20 chegou ao fim de vida em abril de 2026, portanto esta pinagem é uma ponte de compatibilidade até a futura atualização incremental do Angular.

## Backend

No Windows:

```powershell
cd c_backend
$env:GEMINI_API_KEY="sua_chave_aqui"
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
cd c_backend
export GEMINI_API_KEY="sua_chave_aqui"
./mvnw spring-boot:run
```

O backend inicia em `http://localhost:8080`. A chave Gemini é opcional para o restante da aplicação; sem ela, o endpoint do Copa Assistant retorna erro de configuração.

## Frontend

Com um gerenciador compatível com `.nvmrc`:

```bash
cd b_frontend
nvm use
npm ci
npm start
```

O frontend inicia em `http://localhost:4200` e encaminha `/api/*` para `localhost:8080` pelo `proxy.conf.json`. Não é necessário instalar o Angular CLI globalmente.

## Verificações

```bash
cd b_frontend
npm test -- --watch=false
npm run build
```

```powershell
cd c_backend
.\mvnw.cmd test
.\mvnw.cmd package
```

## Configuração Gemini

- `GEMINI_API_KEY`: chave da API, sem valor padrão.
- O modelo efetivo é `gemini-2.5-flash`, configurado em `c_backend/src/main/resources/application.properties`.

Nunca grave credenciais no repositório.

## Conta de demonstração

```text
E-mail: demo@brasilmarket.com
Senha: 123456
```

Essa credencial existe apenas para demonstração local e não é adequada para produção.

## Endpoints principais

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/products` | Lista produtos; aceita `q` e `category` |
| GET | `/api/products/categories` | Lista categorias |
| GET | `/api/cart` | Retorna carrinho e total |
| POST | `/api/cart/add` | Adiciona produto |
| POST | `/api/cart/remove` | Remove produto |
| GET | `/api/wishlist` | Lista favoritos |
| POST | `/api/wishlist/toggle` | Alterna favorito |
| GET | `/api/auth/me` | Retorna o usuário atual |
| POST | `/api/auth/login` | Autentica usuário |
| POST | `/api/auth/register` | Cadastra usuário |
| POST | `/api/auth/logout` | Encerra sessão |
| POST | `/api/checkout` | Finaliza pedido simulado |
| POST | `/api/copa-assistant/chat` | Consulta o Copa Assistant |
