# Navalha & Companhia — agendamento de barbearia

Aplicação demonstrativa full-stack para consulta de disponibilidade, reservas e administração de profissionais, serviços e bloqueios de agenda.

## Requisitos

- Java 17 e Maven 3.9+
- Node.js 20 LTS e npm 10+ (Node 25 também compila, mas não é LTS)
- Google Chrome/Chromium para os testes Angular headless

## Executar

Em um terminal:

```powershell
cd backend
mvn spring-boot:run
```

Em outro terminal:

```powershell
cd frontend
npm install
npm start
```

Acesse `http://localhost:4200`. O frontend encaminha `/api` para `http://localhost:8080`. O H2 usa `backend/data/barbershop.mv.db`; o console de demonstração fica em `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:file:./data/barbershop`, usuário `sa`, senha vazia).

## Testes e builds

```powershell
cd backend
mvn test
mvn package

cd ../frontend
npm test
npm run build
```

`npm test` já usa Chrome Headless e modo não interativo. Artefatos em `target/`, `dist/`, `.angular/`, `node_modules/` e o arquivo de dados H2 são ignorados pelo Git.

## Arquitetura

- `frontend/`: Angular 17 standalone, formulários reativos e serviço HTTP tipado. A tela alterna entre jornada do cliente e administração.
- `backend/`: Spring Boot 3.3.8, API REST, Bean Validation, serviço de aplicação e repositórios Spring Data JPA.
- `H2`: persistência em arquivo para preservar a demonstração entre reinícios. `DemoDataConfig` cria dois profissionais, três serviços e um bloqueio de almoço apenas quando as tabelas correspondentes estão vazias.

O `SchedulingService` concentra as regras de agenda. Reservas e bloqueios são intervalos semiabertos: há conflito quando `novo início < fim existente` e `novo fim > início existente`. Assim, horários consecutivos são permitidos e sobreposições são rejeitadas. A agenda padrão vai de 09:00 a 18:00; a disponibilidade avança em intervalos de 30 minutos e considera a duração do serviço.

### API

| Método | Rota | Uso |
|---|---|---|
| GET | `/api/professionals` | Listar profissionais |
| POST | `/api/professionals` | Cadastrar profissional |
| GET | `/api/services` | Listar serviços |
| POST | `/api/services` | Cadastrar serviço |
| GET | `/api/availability?professionalId=&serviceId=&date=YYYY-MM-DD` | Consultar horários |
| POST | `/api/bookings` | Reservar serviço |
| POST | `/api/blocks` | Bloquear período |

Respostas de validação usam HTTP 400, recursos ausentes 404 e conflitos de agenda 409.

## Dados de demonstração

- Profissionais: Ana Martins e Carlos Lima.
- Serviços: Corte clássico (30 min), Barba (30 min) e Corte e barba (60 min).
- Ana possui bloqueio de almoço das 12:00 às 13:00 no dia seguinte à primeira inicialização.

## Critérios de aceite e evidências

| Critério | Evidência |
|---|---|
| Consultar horários | Tela “Agendar”, `GET /api/availability` e cálculo em `SchedulingService` |
| Reservar serviço/profissional | Formulário de confirmação e `POST /api/bookings` |
| Cadastrar profissionais e serviços | Tela “Administração” e endpoints POST correspondentes |
| Bloquear horários | Formulário administrativo e `POST /api/blocks` |
| Impedir reserva sobre reserva | `SchedulingServiceTest.rejectsOverlappingBooking` |
| Impedir reserva sobre bloqueio | `SchedulingServiceTest.rejectsBlockedTime` |
| Validar frontend/backend | Reactive Forms, Bean Validation e `BookingValidationTest` |
| Dados iniciais | `DemoDataConfig` |

## Decisões e limitações

- Autenticação e autorização não foram incluídas porque não fazem parte do escopo; a área administrativa é uma separação de interface, não uma barreira de segurança.
- A configuração é adequada a uma demonstração local. Para produção, use autenticação, migrações versionadas (Flyway/Liquibase), banco servidor e controle de concorrência/constraint de exclusão próprio do banco.
- Não há Gemini, ViaCEP, produtos, carrinho, wishlist ou pagamentos.
