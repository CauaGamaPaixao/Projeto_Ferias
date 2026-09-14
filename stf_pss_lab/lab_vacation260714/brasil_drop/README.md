# Brasil Drop

Marketplace esportivo com Angular 17.3 e Spring Boot 3.3.8, checkout simulado, favoritos, acessibilidade de fonte e Copa Assistant.

## Estrutura

- `b_frontend/`: interface Angular.
- `c_backend/`: API Java, usuários e pedidos persistidos.
- `a_doc/`: requisitos e documentação da integração de comprovantes.

## Executar localmente

Pré-requisitos: Java 21 (o POM mantém compatibilidade com Java 17), Maven 3.9+, Node compatível com Angular 17 e npm.
A validação desta entrega utilizou Java 21.0.2 e Node 24.19.0 do ambiente; consulte a matriz oficial de compatibilidade do Angular antes de atualizar runtimes.

Backend, em um terminal:

```powershell
cd c_backend
mvn spring-boot:run
```

Frontend, em outro terminal:

```powershell
cd b_frontend
npm ci
npm start
```

Abra http://localhost:4200. O proxy encaminha `/api` para http://localhost:8080.
Conta de demonstração local: `demo@brasilmarket.com` / `123456`.

O banco H2 é criado automaticamente em `c_backend/data/brasildrop.mv.db` quando a API é iniciada a partir de `c_backend/`.
As migrations Flyway são aplicadas na inicialização. Não é necessário instalar um servidor de banco.

## Configuração

`c_backend/.env.example` documenta as variáveis disponíveis. O Spring lê variáveis de ambiente; não carrega esse arquivo automaticamente.

| Variável | Uso |
|---|---|
| `BRASILDROP_DB_URL` | URL JDBC H2; padrão `jdbc:h2:file:./data/brasildrop;DB_CLOSE_ON_EXIT=FALSE`. Use caminho absoluto em instalações permanentes. |
| `BRASILDROP_DB_USER` | Usuário do banco; padrão local `sa`. |
| `BRASILDROP_DB_PASSWORD` | Senha do banco; padrão local vazio. |
| `GEMINI_API_KEY` | Opcional, necessária apenas para o Copa Assistant. |

Não versione o banco nem credenciais. A conta demo permanece disponível para a demonstração deste projeto.
Catálogo, carrinho, favoritos e sessões continuam em memória. Usuários e pedidos são persistidos; reiniciar a API exige novo login, mas mantém o histórico.

## Comprovantes de compra

1. Entre, adicione produtos ao carrinho e finalize a compra informando um CEP válido.
2. A confirmação abre `/pedidos/:code/confirmacao` e consulta o pedido salvo.
3. Use **Imprimir comprovante** para imprimir em A4 ou salvar como PDF no navegador.
4. Use **Meus pedidos**, no menu, para consultar e reimprimir compras anteriores.
5. Atualizar a confirmação ou abrir seu endereço diretamente preserva o pedido. Se a sessão expirou, o login retorna ao comprovante.

O comprovante contém código, data no horário de Brasília, comprador, produtos, quantidades, preços da compra, subtotais, total, pagamento, parcelas e endereço. Mostra **Documento não fiscal** e esclarece que a compra é simulada. Não há cobrança real, emissão de NF-e/NFC-e, desconto ou frete.
A impressão oculta menu, assistente e botões. Os preços históricos não dependem do catálogo atual.

## API

| Método | Rota | Uso |
|---|---|---|
| GET | `/api/products` | Catálogo, filtros `q` e `category` |
| GET | `/api/products/categories` | Categorias |
| GET | `/api/cart` | Itens e total |
| POST | `/api/cart/add`, `/api/cart/remove` | Alterar carrinho |
| GET | `/api/wishlist` | Favoritos |
| POST | `/api/wishlist/toggle` | Alternar favorito |
| GET | `/api/auth/me` | Sessão atual |
| POST | `/api/auth/login`, `/register`, `/logout` | Autenticação (prefixo `/api/auth`) |
| POST | `/api/checkout` | Salvar compra e limpar carrinho após commit |
| GET | `/api/orders` | Histórico do comprador autenticado |
| GET | `/api/orders/{code}` | Comprovante; 401 sem sessão, 403 outro comprador, 404 inexistente |
| POST | `/api/copa-assistant/chat` | Assistente |

As respostas de pedidos usam `Cache-Control: no-store` e não incluem senha ou e-mail interno de propriedade.

## Verificações

```powershell
cd b_frontend
npm test -- --watch=false --browsers=ChromeHeadless
npm run build
```

```powershell
cd c_backend
mvn test
mvn package
```

É necessário Chrome instalado para os testes Angular. Não há scripts de lint ou formatação configurados no projeto.
Neste host, o atalho PowerShell global do npm rejeitou flags; foi usado `& 'C:/Program Files/nodejs/npm.cmd' test -- --watch=false --browsers=ChromeHeadless`.

Consulte [integração e evidências](a_doc/merge-comprovantes.md) para decisões, cobertura, limites e backup.
