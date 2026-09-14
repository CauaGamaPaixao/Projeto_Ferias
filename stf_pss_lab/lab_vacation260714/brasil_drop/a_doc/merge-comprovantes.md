# Integração de comprovantes — Brasil Drop

Implementação do trecho SPO → Brasil Drop de `plano-merge-brasil-drop-spo.md`, fornecido pelo usuário. O documento original descrevia uma análise em modo plano; a solicitação atual autorizou implementar suas funcionalidades no Brasil Drop.

## Escopo entregue

- DTO completo e explícito de comprovante em checkout e consulta.
- H2 em arquivo, Spring JDBC e migration Flyway V1.
- Snapshots de produto, nome, preço unitário, quantidade e subtotal, sem dependência do catálogo atual.
- Pedido associado à conta do comprador, com nome histórico, endereço, pagamento, parcelas, total e data UTC ISO 8601.
- Contas persistentes para permitir nova autenticação após reiniciar a API sem possibilitar recadastrar o e-mail de um comprador anterior.
- Histórico em `/pedidos`, acesso por `/pedidos/:code/confirmacao` e redirecionamento da rota antiga para o histórico.
- Componente Angular de comprovante com impressão A4, responsividade e estados de carregamento, erro, acesso negado e sessão expirada.
- Senhas armazenadas com PBKDF2; respostas de pedidos não expõem credenciais ou e-mail interno.
- Testes de contrato, autorização, regras, rollback, arquivo persistente e UI.

As fases de tema e acessibilidade no SPO não fazem parte desta implementação. O SPO não é este repositório.
Ajustes de fonte já existentes no Brasil Drop foram preservados.

## Decisões e limites

**Persistência:** adotada a opção B do plano. JDBC foi escolhido no lugar da sugestão de JPA para manter uma camada pequena e SQL explícito, com transação Spring e migrations. H2 evita exigir outro serviço no ambiente local. A integração de Flyway segue o mecanismo de inicialização documentado pelo [Spring Boot](https://docs.spring.io/spring-boot/docs/3.2.3/reference/html/howto.html#howto.data-initialization.migration-tool.flyway); as versões das dependências são gerenciadas pelo POM Spring Boot existente.

**Atomicidade:** `OrderRepository.save` insere pedido e itens em uma transação. A limpeza do carrinho ocorre somente após o commit. Checkout e mutações do carrinho compartilham o monitor de sessão. Dois envios simultâneos produzem um pedido; o segundo encontra o carrinho vazio. O carrinho continua em memória: uma interrupção do processo após commit pode perder a resposta, mas o comprovante permanece no histórico. Não há protocolo de idempotência entre múltiplos servidores; a aplicação é local, de uma instância.

**Identidade:** usuários deixam de ser voláteis para manter a propriedade histórica. A conta demo é criada somente quando inexistente. Contas anteriores, que existiam apenas na memória do processo antigo, precisam ser cadastradas uma vez no novo banco. Não havia pedidos persistidos para migrar.

**Valores:** BRL, `BigDecimal` e `DECIMAL(19,2)`. O servidor calcula tudo a partir do catálogo; o cliente não envia preços. A UI mantém números conforme o contrato existente. Não se acrescentaram campos fictícios de desconto, frete ou CNPJ. O comprovante informa a quantidade de parcelas sem arredondar um valor de parcela que pudesse divergir do total. Mantidas as regras existentes: PIX à vista, cartão até 10 parcelas, boleto até 6.

**Datas:** armazenadas em UTC com precisão de microssegundos e offset explícito; exibidas em `America/Sao_Paulo`, também após recarregar.

**Impressão:** A4, margem de 15 mm, preto/branco, sem navegação, assistente ou ações; cabeçalho de tabela repetível em páginas seguintes. Documento não fiscal de uma compra simulada. Nenhuma emissão fiscal ou captura de pagamento foi adicionada.

**Operação:** catálogo e sessões continuam em memória, conforme o projeto original. O banco e a conta demonstrativa são para uso local; este merge não transforma a aplicação em um serviço de comércio pronto para produção. O histórico atualmente não é paginado.

## Contrato

`POST /api/checkout` mantém a rota. Entrada validada:

```json
{
  "cep": "01001-000",
  "rua": "Praça da Sé",
  "numero": "123",
  "complemento": "",
  "paymentMethod": "PIX",
  "installments": 1
}
```

Saída (também usada em `GET /api/orders/{code}`):

```json
{
  "code": "BD-<identificador>",
  "buyerName": "Nome do comprador",
  "items": [
    {"productId": 1, "name": "Camisa Brasil", "unitPrice": 349.90, "quantity": 2, "subtotal": 699.80}
  ],
  "total": 699.80,
  "address": {
    "cep": "01001-000", "logradouro": "Praça da Sé", "bairro": "Sé",
    "localidade": "São Paulo", "uf": "SP", "numero": "123", "complemento": ""
  },
  "paymentMethod": "PIX",
  "installments": 1,
  "createdAt": "2026-09-14T12:00:00Z"
}
```

`GET /api/orders` devolve somente os pedidos da sessão, mais recentes primeiro. Sem sessão: 401. Pedido de outro comprador: 403. Código inexistente: 404. Entrada inválida: 400. Dados pessoais não são mantidos em localStorage ou history.state.

## Aceite e evidências

| Critério | Evidência automatizada |
|---|---|
| Itens, subtotal, total, endereço, parcelas e campos públicos | `OrderFlowTest.checkoutCanBeReadInNewSessionWithCompletePrivateContract` |
| 401/403/404 e histórico filtrado | `OrderFlowTest.rejectsAnonymousForeignAndMissingOrders` |
| Histórico independente do catálogo | `OrderFlowTest.receiptKeepsSnapshotAfterCatalogChanges` |
| Rejeitar pagamento/CEP incorretos sem perder carrinho | testes parametrizados e de endereço de `OrderFlowTest` |
| Falha após inserir cabeçalho e um item desfaz a transação | `OrderFlowTest.failedItemInsertRollsBackHeaderAndKeepsCart` |
| Checkout simultâneo não duplica pedido | `OrderFlowTest.concurrentCheckoutCreatesOnlyOneOrder` |
| Contas e comprovantes sobrevivem à reabertura do arquivo | `FilePersistenceTest` |
| Hash de senha e e-mail não reutilizável | `UserServiceTest` |
| URL direta, erros HTTP, retry e mudança de rota | `order-success.component.spec.ts` |
| Renderização de um/vários itens, PIX/parcelas e complemento opcional | `order-receipt.component.spec.ts` |
| Histórico vazio, link de comprovante e sessão expirada | `orders.component.spec.ts` |
| Impedir segundo clique e navegar pelo código salvo | `checkout.component.spec.ts` |

Baseline: os 8 testes Java existentes passaram. O frontend veio sem dependências instaladas; após `npm ci`, a suíte antiga falhou ao compilar uma referência obsoleta a `AppComponent.title`. Os testes iniciais também precisavam de módulos HTTP/router/forms. Essas configurações foram corrigidas sem alterar as funcionalidades correspondentes.

Validação final em 14/09/2026:
- `npm ci --no-audit --no-fund`: passou.
- `npm test -- --watch=false --browsers=ChromeHeadless`: 27 testes passaram.
- `npm run build`: passou.
- `mvn -B -ntp test package`: 21 testes passaram e JAR gerado.
- Lint/format: não há comandos configurados nos manifests.
- `git diff --check`: passou.
- Navegador Chrome: login → catálogo → carrinho → checkout (ViaCEP real) → comprovante → refresh → histórico → reimpressão passou. Nova sessão pediu login e retornou ao mesmo comprovante.
- Layout revisado em 1440 px e 375 px com fonte de 20 px. No celular, os itens se reorganizam em blocos para manter valores monetários inteiros.
- Impressão real do navegador para PDF: A4, sem navegação/assistente/ações. Um fixture de 60 itens gerou quatro páginas, com todos os itens e o total preservados, cabeçalho da tabela repetido e revisão visual das quatro páginas. Esse fixture é apenas de QA; o fluxo normal foi validado com produtos reais do catálogo.
- Evidências locais (não versionadas): `qa-output/receipt-desktop.png`, `receipt-mobile.png`, `orders-mobile.png`, `receipt-a4.pdf`, `receipt-long-a4.pdf`, `smoke.log` e `long-print.log`.
- O build Angular passa com aviso de tamanho do CSS do comprovante (2,36 kB, acima da meta de 2 kB e abaixo do limite de erro de 4 kB).
- O Flyway emite aviso de que H2 2.2.224 é mais recente que sua versão testada 2.2.220; migrations, rollback e reabertura do arquivo passaram usando as versões gerenciadas pelo Spring Boot.
- O npm local emitiu avisos de dependências antigas e de scripts esbuild bloqueados; a instalação, compilação e testes concluíram com sucesso. Atualização ampla de dependências fica fora deste merge.

## Backup e retorno à versão anterior

1. Pare a API antes de copiar `c_backend/data/brasildrop.mv.db`. Se usou URL customizada, copie o arquivo dessa URL.
2. Guarde a cópia fora do repositório com acesso restrito, pois contém endereços e hashes de contas.
3. Para restaurar, pare a API e restaure o arquivo no mesmo caminho usando a mesma senha.
4. Para reverter somente o código, volte à revisão anterior preservando o banco. A aplicação antiga não lê os novos pedidos, mas eles continuam no arquivo para quando esta versão for reaplicada.
5. Não execute DROP de tabelas e não altere V1 após aplicá-la. Mudanças futuras devem usar novas migrations.

O teste de arquivo verifica fechamento e reabertura, não substitui um ensaio de restauração do ambiente de produção. Não houve deploy, merge na branch principal ou alteração no SPO.
