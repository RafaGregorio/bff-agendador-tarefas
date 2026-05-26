# 📋 BFF Agendador de Tarefas — BFF

BFF (Backend for Frontend) do sistema de agendamento de tarefas. Atua como ponto central de entrada da aplicação, roteando e orquestrando as requisições para os microsserviços de **Usuário**, **Agendador de Tarefas** e **Notificação**.

---

## 🏗️ Arquitetura

```
Cliente
   │
   ▼
┌─────────────────────────────┐
│         BFF (porta 8085)    │  ◄── Autenticação JWT (Spring Security)
│     bgg-agendador-tarefas   │  ◄── Roteamento via OpenFeign
│                             │  ◄── Cron de notificações automáticas
└──────┬───────────┬──────────┘
       │           │           │
       ▼           ▼           ▼
┌──────────┐ ┌──────────┐ ┌──────────────┐
│ Usuário  │ │ Tarefas  │ │  Notificação │
│ :8082    │ │ :8083    │ │  :8084       │
│          │ │          │ │              │
│PostgreSQL│ │ MongoDB  │ │  JavaMail    │
└──────────┘ └──────────┘ └──────────────┘
```

### Microsserviços

| Serviço | Porta | Banco | Responsabilidade |
|---|---|---|---|
| [cadatro_usuario](https://github.com/RafaGregorio/cadatro_usuario) | 8082 | PostgreSQL | Cadastro, login e gestão de usuários |
| [agendador-tarefas](https://github.com/RafaGregorio/agendador-tarefas) | 8083 | MongoDB | CRUD e agendamento de tarefas |
| [notificacao](https://github.com/RafaGregorio/notificacao) | 8084 | — | Envio de e-mails via JavaMailSender |
| **BFF (este repo)** | **8085** | — | Orquestração, autenticação e roteamento |

---

## 🔐 Autenticação e Autorização

A autenticação é gerenciada pelo **serviço de Usuário** em conjunto com o BFF:

1. O cliente envia credenciais para `POST /usuario/login`
2. O BFF repassa a requisição ao microsserviço de usuários
3. O serviço de usuários valida as credenciais e retorna um **JWT**
4. O BFF devolve o token ao cliente
5. Todas as requisições subsequentes devem incluir o token no header:

```
Authorization: Bearer <token>
```

> Todos os endpoints exceto `/usuario/login` e `POST /usuario` (cadastro) exigem autenticação via JWT.

---

## ⏰ Cron — Notificações Automáticas

O BFF possui um **job agendado (Cron)** que verifica periodicamente tarefas próximas do prazo e aciona o serviço de notificações para envio automático de e-mails aos usuários.

Fluxo do Cron:

```
Cron (BFF)
   │
   ├── Busca tarefas por período → GET /tarefas/eventos
   │
   └── Para cada tarefa encontrada:
          └── Aciona serviço de Notificação → envia e-mail ao usuário
```

---

## 📡 Endpoints

### Tarefas

| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| `GET` | `/tarefas` | Busca lista de tarefas por e-mail do usuário | ✅ |
| `POST` | `/tarefas` | Salvar nova tarefa | ✅ |
| `PUT` | `/tarefas` | Alterar dados da tarefa | ✅ |
| `PATCH` | `/tarefas` | Alterar status da tarefa | ✅ |
| `DELETE` | `/tarefas` | Deletar tarefa por ID | ✅ |
| `GET` | `/tarefas/eventos` | Buscar tarefas por período | ✅ |

### Usuário

| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| `POST` | `/usuario` | Cadastrar novo usuário | ❌ |
| `POST` | `/usuario/login` | Login e geração de JWT | ❌ |
| `GET` | `/usuario` | Buscar dados do usuário por e-mail | ✅ |
| `PUT` | `/usuario` | Atualizar dados do usuário | ✅ |
| `DELETE` | `/usuario/{email}` | Deletar usuário | ✅ |
| `POST` | `/usuario/telefone` | Salvar telefone | ✅ |
| `PUT` | `/usuario/telefone` | Atualizar telefone | ✅ |
| `POST` | `/usuario/endereco` | Salvar endereço | ✅ |
| `PUT` | `/usuario/endereco` | Atualizar endereço | ✅ |

---

## 🛠️ Stack

- **Java** + **Spring Boot**
- **Spring Security** + **JWT** — autenticação e autorização
- **OpenFeign** — comunicação entre microsserviços via REST
- **Swagger / OpenAPI** — documentação das APIs
- **Docker** + **Docker Compose** — containerização de toda a stack

---

## 🚀 Rodando localmente

### Pré-requisitos

- Java 17+
- Docker e Docker Compose
- Todos os microsserviços clonados localmente

### 1. Clone todos os repositórios

```bash
git clone https://github.com/RafaGregorio/bgg-agendador-tarefas   # BFF
git clone https://github.com/RafaGregorio/cadatro_usuario
git clone https://github.com/RafaGregorio/agendador-tarefas
git clone https://github.com/RafaGregorio/notificacao
```

### 2. Configure as variáveis de ambiente

Crie um arquivo `.env` na raiz do BFF com as seguintes variáveis:

```env
# JWT
JWT_SECRET=seu_secret_aqui

# URLs dos microsserviços
USUARIO_SERVICE_URL=http://usuario:8082
TAREFAS_SERVICE_URL=http://agendador-tarefas:8083
NOTIFICACAO_SERVICE_URL=http://notificacao:8084

# E-mail (para o serviço de notificação)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=seu_email@gmail.com
MAIL_PASSWORD=sua_senha_de_app
```

### 3. Suba toda a stack com Docker Compose

```bash
cd bgg-agendador-tarefas
docker compose up --build
```

O Docker Compose do BFF orquestra todos os 4 serviços. Após subir, os serviços estarão disponíveis em:

| Serviço | URL |
|---|---|
| BFF | http://localhost:8085 |
| Usuário | http://localhost:8082 |
| Agendador de Tarefas | http://localhost:8083 |
| Notificação | http://localhost:8084 |

### 4. Acesse a documentação Swagger

```
http://localhost:8085/swagger-ui.html
```

---

## 📁 Repositórios dos Microsserviços

| Serviço | Repositório |
|---|---|
| Usuário | [github.com/RafaGregorio/cadatro_usuario](https://github.com/RafaGregorio/cadatro_usuario) |
| Agendador de Tarefas | [github.com/RafaGregorio/agendador-tarefas](https://github.com/RafaGregorio/agendador-tarefas) |
| Notificação | [github.com/RafaGregorio/notificacao](https://github.com/RafaGregorio/notificacao) |
