# BrasilDrop — Projeto de Férias

Marketplace de artigos esportivos temático para a Copa do Mundo 2026, com assistente de IA integrado ao Google Gemini.

---

## Estrutura do projeto

```
stf_pss_lab/
├── a_frontend/   → Aplicação Angular 17 (interface do usuário)
└── b_backend/    → API REST Spring Boot 3 (Java 21)
```

---

## Pré-requisitos

| Ferramenta | Versão mínima |
|---|---|
| Java | 21 |
| Maven | 3.9+ |
| Node.js | 18+ |
| npm | 9+ |
| Angular CLI | 17+ (`npm install -g @angular/cli@17`) |

---

## Como rodar

### 1. Backend (c_backend)

```powershell
# Windows PowerShell
cd c_backend
$env:GEMINI_API_KEY="sua_chave_aqui"
mvn spring-boot:run
```

```bash
# Linux / macOS
cd c_backend
export GEMINI_API_KEY="sua_chave_aqui"
mvn spring-boot:run
```

O backend sobe em **http://localhost:8080**

> A chave da API Gemini é necessária apenas para o Copa Assistant.
> Sem ela, o resto da aplicação funciona normalmente.

---

### 2. Frontend (b_frontend)

Em outro terminal:

```bash
cd b_frontend
npm install
ng serve
```

O frontend sobe em **http://localhost:4200**

> O Angular já está configurado com proxy para o backend (`proxy.conf.json`),
> então todas as chamadas `/api/*` são redirecionadas automaticamente para `localhost:8080`.

---

## Credenciais de demonstração

```
E-mail:  demo@brasilmarket.com
Senha:   123456
```

---

## Tecnologias utilizadas

### Backend
- Java 21
- Spring Boot 3 (Spring MVC, Spring Web)
- API REST (JSON)
- Google Gemini API (Copa Assistant com Google Search grounding)
- ViaCEP API (validação de endereço no checkout)

### Frontend
- Angular 17
- TypeScript
- CSS (paleta verde/amarelo — identidade BrasilDrop)
- Angular Router, HttpClient, FormsModule

---

## Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/products` | Lista produtos (params: `q`, `category`) |
| GET | `/api/products/categories` | Lista categorias disponíveis |
| GET | `/api/cart` | Itens do carrinho + total |
| POST | `/api/cart/add` | Adiciona produto ao carrinho |
| POST | `/api/cart/remove` | Remove produto do carrinho |
| GET | `/api/wishlist` | Lista favoritos |
| POST | `/api/wishlist/toggle` | Adiciona/remove dos favoritos |
| GET | `/api/auth/me` | Usuário da sessão atual |
| POST | `/api/auth/login` | Login |
| POST | `/api/auth/register` | Cadastro |
| POST | `/api/auth/logout` | Logout |
| POST | `/api/checkout` | Finaliza pedido |
| POST | `/api/copa-assistant/chat` | Pergunta ao Copa Assistant |
