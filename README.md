# Serviço de email para novos cadastros

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) 
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) 
[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE) 
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white) 
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![Microservices](https://img.shields.io/badge/Microservices-2470C0?style=for-the-badge&logo=microservices&logoColor=white)

Este projeto consiste em microsserviços para APIs de envio de emails e cadastro de usuários. A solução foi desenvolvida com **Java, Spring, Postgres em contêiner Docker, RabbitMQ para mensageria e AWS Simple Email Service**. Foi criado para resolver e melhorar o ***desafio técnico backend da Uber***, disponível [neste repositório](https://github.com/uber-archive/coding-challenge-tools/blob/master/coding_challenge.md).


## Sumário

- [Arquitetura e Estrutura de Diretórios](#arquitetura-e-estrutura-de-diretórios)
- [Pré-requisitos](#pré-requisitos)
- [Fluxo de Execução](#fluxo-de-execução)
- [Instalação do Projeto](#instalação-do-projeto)
- [Endpoints da API](#endpoints-da-api)
- [Licença](#licença)
- [Contribuindo](#contribuindo)



## Arquitetura e Estrutura de Diretórios

A arquitetura é composta por dois principais microsserviços, um gateway de API e um ***message broker***:

- **`user-service`**: Uma aplicação Spring Boot responsável por gerir dados dos usuários. Ele expõe uma API REST para operações de CRUD e, ao criar um novo usuário, publica um evento de notificação para um fila em nuvem, gerenciada pelo RabbitMQ.
- **`email-service`**: Uma aplicação Spring Boot que escuta os eventos de criação de usuário. Ao receber uma notificação, ele utiliza o serviço Amazon SES (Simple Email Service) para enviar um e-mail de boas-vindas ao novo usuário.

A comunicação entre os serviços é feita de forma assíncrona utilizando **RabbitMQ** como *message broker*, o que garante que os serviços sejam independentes e resilientes.

### Tecnologias Utilizadas

- **Linguagem**: Java 17
- **Framework**: Spring Boot 3
- **Persistência**: Spring Data JPA com PostgreSQL
- **Migração de Banco de Dados**: Flyway
- **Mensageria**: RabbitMQ
- **Envio de E-mail**: AWS SES (Simple Email Service)
- **Build Tool**: Maven


### Estrutura de Diretórios

O projeto está organizado da seguinte forma:

```
com.felipe.email
├── EmailApplication.java  # Ponto de entrada do serviço de email 
├── dto/                   # Objetos de transferência de dados
├── consumer/              # Consumidor de mensagens do RabbitMQ
├── domain/                # Entidades do domínio
├── configuration/         # Configurações do Spring e RabbitMQ
├── enums/                 # Enumerações utilizadas
├── repository/            # Repositórios JPA
└── service/               # Lógica de negócio para envio de e-mails
```

```
com.felipe.user
├── UserApplication.java   # Ponto de entrada do serviço de usuário
├── controller/            # Controladores REST
├── infra/                 # Configurações de infraestrutura (Docker, RabbitMQ, PostgreSQL)
│     ├── exception/       # Exceções customizadas
│     └── handler/         # Manipuladores de exceções globais
├── dto/                   # Objetos de transferência de dados
├── domain/                # Entidades do domínio
├── mapper/                # Mapeadores entre DTOs e entidades
├── configuration/         # Configurações do Spring e RabbitMQ
├── producer/              # Produtor de mensagens para o RabbitMQ
├── repository/            # Repositórios JPA
└── service/               # Lógica de negócio para gerenciamento de usuários

```



## Pré-requisitos

Antes de começar, certifique-se de que você tem os seguintes softwares instalados:

- **Java 17** ou superior
- **Maven 3.8** ou superior
- **Docker** e **Docker Compose**



## Fluxode Execução

O fluxo principal da aplicação ocorre da seguinte maneira:

1.  Um cliente faz uma requisição `POST /users` para o `user-service` com os dados de um novo usuário.
2.  O `user-service` valida os dados e salva o novo usuário em seu banco de dados PostgreSQL.
3.  Após salvar, o `user-service` publica uma mensagem contendo os detalhes do usuário na fila `designed-to-email-ms` no RabbitMQ.
4.  O `email-service`, que está escutando esta fila, consome a mensagem.
5.  O `email-service` formata e envia um e-mail de boas-vindas para o endereço de e-mail do usuário utilizando o AWS SES.
6.  O `email-service` salva um registro do e-mail enviado em seu próprio banco de dados para fins de rastreamento.



## Instalação do Projeto

Siga os passos abaixo para configurar e executar o ambiente de desenvolvimento.

### 1. Clonar o Repositório

```bash
git clone https://github.com/filipeluisgg/desafio-backend-uber.git
cd desafio-backend-uber
```

### 2. Configurar Variáveis de Ambiente

Ambos os microsserviços dependem de variáveis de ambiente para se conectar a serviços externos. Você pode criar um arquivo `.env` na raiz de cada projeto (`user/` e `email/`) ou configurar estas variáveis diretamente no seu sistema.

**`user-service` (`user/.env`)**

```
RABBITMQ_ADDRESSES=localhost:5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
RABBITMQ_VIRTUAL_HOST=/
```

**`email-service` (`email/.env`)**

```
RABBITMQ_ADDRESSES=localhost:5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
RABBITMQ_VIRTUAL_HOST=/

AWS_REGION=sua-regiao-aws
AWS_ACCESS_KEY_ID=sua-access-key
AWS_SECRET_ACCESS_KEY=sua-secret-key
EMAIL_SENDER=seu-email-verificado-no-ses
```

### 3. Iniciar a Infraestrutura com Docker

Os arquivos `compose.yaml` nos diretórios `/infra` de cada microsserviço podem ser usados para subir a infraestrutura necessária (PostgreSQL, RabbitMQ).
Para iniciar os contêineres, execute:

```bash
docker-compose up -d
```

### 4. Executar os Microsserviços

Abra um terminal para cada microsserviço e execute os seguintes comandos:

**Para o `user-service`:**

```bash
cd user
mvn spring-boot:run
```

**Para o `email-service`:**

```bash
cd email
mvn spring-boot:run
```



## Endpoints da API

A API do `user-service` está disponível em `http://localhost:8081`.

### Criar um Novo Usuário

- **`POST /users`**

  Cria um novo usuário no sistema, publicando na fila em nuvem e enviado um email de boas vindas.

  **Request Body:**

  ```json
  {
    "name": "Felipe",
    "email": "felipe@example.com"
  }
  ```

  **Response (201 Created):**

  ```json
  {
    "name": "Felipe",
    "email": "felipe@example.com"
  }
  ```

### Listar Todos os Usuários

- **`GET /users`**

  Retorna uma lista com todos os usuários cadastrados.

  **Response (200 OK):**

  ```json
  [
    {
      "name": "Felipe",
      "email": "felipe@example.com"
    }
  ]
  ```

### Buscar Usuário por Nome

- **`GET /users/{name}`**

  Retorna os dados de um usuário específico pelo seu nome.

  **Response (200 OK):**

  ```json
  {
    "name": "Felipe",
    "email": "felipe@example.com"
  }
  ```



## Contribuindo

Contribuições são bem vindas. Se você achar problemas ou tiver sugestões de melhoria, por favor, abra uma `issue` ou um
`pull request` no repositório.

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b branch-name`)
3. Commit suas mudanças (`git commit -m 'feat: add some feature '`)
4. Push para a branch (`git push origin branch-name`)
5. Abra um Pull Request

Obs: Se optar por fazer uma contribuição, por favor siga o estilo de código já existente no projeto e [convenções de commit](https://www.conventionalcommits.org/en/v1.0.0/).



## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo `LICENSE` para mais detalhes.



## Autores

* **Luis Felipe** - *Trabalho inicial*
* └─ [LinkedIn](https://www.linkedin.com/in/filipeluisgg/)
