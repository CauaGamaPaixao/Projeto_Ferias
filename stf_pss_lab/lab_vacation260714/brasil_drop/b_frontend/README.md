# BrasilDrop frontend

Aplicação Angular 17.3 que consome a API REST do diretório `../c_backend`.

## Pré-requisitos

- Node.js 20.19.6 LTS, fixado em `.nvmrc`.
- npm 10 ou 11.

Angular 17.3 declara oficialmente Node `^18.13.0 || ^20.9.0`; Node 20.19.6 pertence a essa matriz. Como a linha Node 20 chegou ao fim de vida em abril de 2026, esta pinagem é uma ponte de compatibilidade até a atualização incremental do Angular. Os builds e testes devem ser executados ao trocar o runtime.

## Instalação e execução

```bash
nvm use
npm ci
npm start
```

A aplicação abre em `http://localhost:4200`. O servidor de desenvolvimento usa `proxy.conf.json` para encaminhar `/api/*` a `http://localhost:8080`.

Não é necessário instalar o Angular CLI globalmente; os scripts usam a versão local do lockfile.

## Testes e build

```bash
npm test -- --watch=false
npm run build
```

O build de produção é gravado em `dist/`. Cache Angular, cobertura, dependências instaladas e outputs compilados são ignorados pelo Git.

## Versões efetivas

As faixas declaradas estão em `package.json`; as versões resolvidas e reproduzíveis estão em `package-lock.json`. Atualizações de framework devem alterar ambos em uma fase própria.
