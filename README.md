# Finance API Spring

Este projeto é uma API RESTful desenvolvida com Spring Boot para gerenciar transações financeiras. A API permite criar, atualizar, buscar e deletar transações e categorias, e ainda inclui suporte a autenticação de usuários.

## Stack Tecnológica

- Java 17
- Spring Boot
- Spring Data JPA
- Spring MVC
- Spring Security
- Hibernate
- Lombok
- Jakarta EE
- PostgreSQL (ou outro banco de dados relacional)

## Estrutura do Projeto

- `com.financeapispring.controllers`: Contém os controladores que expõem as APIs RESTful.
- `com.financeapispring.model`: Contém as entidades JPA que representam as tabelas do banco de dados.
- `com.financeapispring.dto`: Contém as classes DTO para transferência de dados.
- `com.financeapispring.service`: Contém os serviços que implementam a lógica de negócio.
- `com.financeapispring.repository`: Contém os repositórios JPA que fazem a comunicação com o banco de dados.

## Configuração do Projeto

### Pré-Requisitos

- Java 17
- Maven
- PostgreSQL (ou outro banco de dados relacional)

### Passos para Configuração

1. Clone o repositório:
    ```sh
    git clone git@github.com:JulianoL13/FinanceApiJava.git
    cd finance-api-spring
    ```

2. Configure o banco de dados no arquivo `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/finance
    spring.datasource.username=seu-usuario
    spring.datasource.password=sua-senha
    spring.jpa.hibernate.ddl-auto=update
    ```

3. Compile e execute o projeto:
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

## APIs Endpoints

### Categoria

- **Listar Todas as Categorias**
    ```http
    GET /api/categories
    ```

- **Criar Nova Categoria**
    ```http
    POST /api/categories
    Content-Type: application/json

    {
        "name": "Categoria Exemplo",
        "description": "Descrição da Categoria",
        "user": {
            "id": 1
        }
    }
    ```

- **Atualizar Categoria**
    ```http
    PUT /api/categories/{id}
    Content-Type: application/json

    {
        "name": "Categoria Atualizada",
        "description": "Descrição Atualizada",
        "user": {
            "id": 1
        }
    }
    ```

- **Remover Categoria**
    ```http
    DELETE /api/categories/{id}
    ```

- **Buscar Categoria pelo Nome**
    ```http
    GET /api/categories/byName/{name}
    ```

### Transação

- **Listar Todas as Transações**
    ```http
    GET /api/transactions
    ```

- **Criar Nova Transação**
    ```http
    POST /api/transactions
    Content-Type: application/json

    {
        "amount": 100.0,
        "description": "Compra no supermercado",
        "date": "2023-10-01",
        "type": "EXPENSE",
        "recurring": false,
        "categoryId": 1,
        "userId": 1
    }
    ```

- **Atualizar Transação**
    ```http
    PUT /api/transactions/{id}
    Content-Type: application/json

    {
        "amount": 120.0,
        "description": "Descrição Atualizada",
        "date": "2023-10-01",
        "type": "EXPENSE",
        "recurring": true,
        "category": {
            "id": 1
        },
        "user": {
            "id": 1
        }
    }
    ```

- **Remover Transação**
    ```http
    DELETE /api/transactions/{id}
    ```

- **Buscar Transações por Usuário**
    ```http
    GET /api/transactions/user/{userId}
    ```

- **Buscar Transações por Categoria**
    ```http
    GET /api/transactions/category/{categoryId}
    ```

- **Buscar Transações por Intervalo de Datas**
    ```http
    GET /api/transactions/date?startDate=2023-10-01&endDate=2023-10-31
    ```

- **Buscar Transações com Valor Maior que um Valor Especificado e ID do Usuário (Paginado)**
    ```http
    GET /api/transactions/amount/greater?amount=50.0&userId=1&page=0&size=10
    ```

- **Buscar Transações com Valor Menor que um Valor Especificado e ID do Usuário (Paginado)**
    ```http
    GET /api/transactions/amount/less?amount=200.0&userId=1&page=0&size=10
    ```

## Autenticação e Autorização

Este projeto utiliza Spring Security para autenticação e autorização dos usuários. A entidade `User` implementa `UserDetails` para integração com o Spring Security.

### Entidade User

- **ID**: Identificador único do usuário.
- **Email**: Email do usuário (deve ser único).
- **Nome**: Nome do usuário.
- **Senha**: Senha do usuário (armazenada de forma segura).
- **Data de Criação**: Data e hora da criação do usuário.
- **Lista de Categorias**: Lista de categorias associadas ao usuário.
- **Lista de Transações**: Lista de transações associadas ao usuário.
- **Role**: Papel do usuário (ADMIN, USER).

## Contribuindo

Para contribuir com o projeto:

1. Faça um fork do repositório.
2. Crie uma nova branch para sua feature (`git checkout -b feature/nova-feature`).
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`).
4. Dê um push na sua branch (`git push origin feature/nova-feature`).
5. Abra um pull request.

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.
