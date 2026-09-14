# API Brasil Drop

Spring Boot 3.3.8; compilação Java 17, execução validada com Java 21.

```powershell
mvn spring-boot:run
mvn test
mvn package
```

A API escuta na porta 8080. Execute os comandos nesta pasta para manter o caminho padrão do H2 em `data/brasildrop.mv.db`.
As migrations em `src/main/resources/db/migration/` são aplicadas automaticamente pelo Flyway.

Usuários e pedidos ficam no banco; as senhas usam PBKDF2 com salt individual. A API não devolve hashes.
A compra salva cabeçalho e snapshots dos itens na mesma transação JDBC antes de limpar o carrinho da sessão.
Não existe estoque transacional ou pagamento real neste protótipo.

Configure o ambiente conforme [README principal](../README.md) e [.env.example](.env.example).
O arquivo `.env.example` é apenas referência, não é carregado automaticamente pelo Spring.

Para backup, pare a API e copie o arquivo `.mv.db` para um local protegido. Restaure com a API parada e a mesma configuração de caminho/senha. Nunca exclua o banco para reverter código.

Detalhes do contrato, decisões de persistência e testes: [merge de comprovantes](../a_doc/merge-comprovantes.md).
