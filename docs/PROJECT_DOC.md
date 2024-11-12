
# Finance API Spring
# Membros do grupo:
##   Juliano Laranjeira Viana, Vitória de Oliveira Fontes, Caio Moura de Lima, Luis Henrique Almeida Borges, Abner Cruz Miranda, Deusivan Ferreira da Silva.

### Documentação Técnica v1.1

## Sumário
1. [Visão Geral](#1-visão-geral)
2. [Tecnologias Utilizadas](#2-tecnologias-utilizadas)
3. [Estrutura do Projeto](#3-estrutura-do-projeto)
4. [Configuração do Ambiente](#4-configuração-do-ambiente)
5. [Modelos de Dados](#5-modelos-de-dados)
6. [Segurança e Autenticação](#6-segurança-e-autenticação)
7. [API Endpoints](#7-api-endpoints)
8. [Contribuição](#8-contribuição)
9. [Suporte e Contato](#9-suporte-e-contato)

---

## 1. Visão Geral

### 1.1 Descrição do Projeto
Este projeto consiste em uma API RESTful desenvolvida com Spring Boot, projetada para gerenciar transações financeiras de forma segura e eficiente. A API oferece funcionalidades completas de CRUD para transações e categorias financeiras, com suporte a autenticação de usuários.

### 1.2 Objetivos
- Fornecer uma interface programática para gerenciamento de transações financeiras
- Garantir a segurança e privacidade dos dados dos usuários
- Facilitar o controle e categorização de despesas e receitas
- Oferecer relatórios e análises financeiras

---

## 2. Tecnologias Utilizadas

### 2.1 Backend
- **Java 17**
  - Linguagem de programação principal
  - Suporte a recursos modernos da linguagem

### 2.2 Frameworks e Bibliotecas
- **Spring Boot**: Framework principal
	- Versão: 3.3.5
  - Spring Data JPA
  - Spring MVC
  - Spring Security
	  - JWT
	  - oAuth2
  - Spring Validation
  - Spring Web
	

### 2.3 Armazenamento de dados
- **Hibernate**: Framework ORM
- **H2**: Banco de dados de desenvolvimento
- **PostgreSQL**: Banco de dados relacional
	- 2.3.1 **Diagram Inicial do Banco de Dados**
	- ![[WhatsApp Image 2024-11-05 at 00.30.41.jpeg]]

### 2.4 Ferramentas de Desenvolvimento
- **Lombok**: Redução de código boilerplate
- **Maven**: Gerenciamento de dependências
- **Jakarta EE**: Especificações Java EE

### 2.5 Containerização e DevOps

-   **Docker**: Plataforma de containerização
    -   Docker Compose: Orquestração de containers
    -   Dockerfile: Definição de imagens
-   **Container Registry**: Armazenamento de imagens Docker

---

## 3. Estrutura do Projeto

### 3.1 Organização de Pacotes

| Pacote | Descrição | Responsabilidades |
|--------|-----------|-------------------|
| `com.financeapispring.controllers` | Controladores REST | Endpoints da API e tratamento de requisições |
| `com.financeapispring.model` | Entidades | Modelos de dados e entidades JPA |
| `com.financeapispring.dto` | DTOs | Objetos de transferência de dados |
| `com.financeapispring.service` | Serviços | Lógica de negócios |
| `com.financeapispring.repository` | Repositórios | Acesso ao banco de dados |
| `com.financeapispring.exception` | Exceções| Lança algumas Exceções | 
| `com.financeapispring.security` | Segurança| Responsável por lidar com as configurações de segurança | 

### 3.2 Arquitetura

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── financeapispring/
│   │           ├── controllers/
│   │           ├── model/
│   │           ├── dto/
│   │           ├── exceptions/
│   │           ├── security/
│   │           ├── service/
│   │           └── repository/
│   └── resources/
│       └── application.properties
└── test/
    └── java/
```

---

## 4. Configuração do Ambiente

### 4.1 Pré-requisitos
- Java Development Kit (JDK) 17 ou superior
- Maven 3.8+ (4.0.0 utilizado no projeto)
- PostgreSQL 13+ ou banco de dados compatível
- IDE de sua preferência (recomendado: IntelliJ IDEA ou Eclipse)

### 4.2 Configuração do Banco de Dados Principal
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```
### 4.2 Configuração do Banco de Dados de desenvolvimento
```properties
spring.datasource.url=jdbc:h2:mem:financedb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
```
---

## 5. Modelos de Dados

### 5.1 Usuário (User)
```java
@Entity  
@Table(name = "users")  
@NoArgsConstructor  
@AllArgsConstructor  
public class User implements UserDetails {  
    @Id  
 @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
  @Column(nullable = false, unique = true)  
    private String email;  
  
  @Column(nullable = false)  
    private String name;  
  
  @Column(nullable = false)  
    private String password;  
  
  @Column(nullable = false)  
    private LocalDateTime createdAt;  
  
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)  
    @JsonBackReference("user-category")  
    private List<Category> categories;  
  
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)  
    @JsonBackReference("user-transaction")  
    private List<Transaction> transactions;  
  
  @Enumerated(EnumType.STRING)  
    @Column(nullable = false)  
    private UserRole role;  
  
  public User(Long id, String email, String name, String password, UserRole role) {  
        this.id = id;  
  this.email = email;  
  this.name = name;  
  this.password = password;  
  this.role = role;  
  }  
  
    public User(String name, String email, String password, UserRole role) {  
        this.name = name;  
  this.email = email;  
  this.password = password;  
  this.role = role;  
  }  
  
    @PrePersist  
  protected void onCreate() {  
        this.createdAt = LocalDateTime.now();  
  }  
  
    public Collection<? extends GrantedAuthority> getAuthorities() {  
        return createAuthorities(this.role);  
  }  
  
    private Collection<? extends GrantedAuthority> createAuthorities(UserRole role) {  
        if (role == UserRole.ADMIN) {  
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));  
  }  
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));  
  }  
  
    public Long getId() {  
        return id;  
  }  
  
    public void setId(Long id) {  
        this.id = id;  
  }  
  
    public String getEmail() {  
        return email;  
  }  
  
    public void setEmail(String email) {  
        this.email = email;  
  }  
  
    public String getName() {  
        return name;  
  }  
  
    public void setName(String name) {  
        this.name = name;  
  }  
  
    public String getPassword() {  
        return password;  
  }  
  
    @Override  
  public String getUsername() {  
        return email;  
  }  
  
    public void setPassword(String password) {  
        this.password = password;  
  }  
  
    public LocalDateTime getCreatedAt() {  
        return createdAt;  
  }  
  
    public void setCreatedAt(LocalDateTime createdAt) {  
        this.createdAt = createdAt;  
  }  
  
    public List<Category> getCategories() {  
        return categories;  
  }  
  
    public void setCategories(List<Category> categories) {  
        this.categories = categories;  
  }  
  
    public List<Transaction> getTransactions() {  
        return transactions;  
  }  
  
    public void setTransactions(List<Transaction> transactions) {  
        this.transactions = transactions;  
  }  
  
    public UserRole getRole() {  
        return role;  
  }  

    public void setRole(UserRole role) {  
        this.role = role;  
  }  
}
}
```

#### 5.1.1 Atributos Principais
- **ID**: Identificador único
- **Email**: Email único do usuário
- **Nome**: Nome completo
- **Senha**: Hash da senha
- **Data de Criação**: Timestamp automático
- **Role**: ADMIN ou USER

### 5.2 Usuário (Transaction)
```
@Entity  
@Table(name = "transactions")  
@NoArgsConstructor  
@AllArgsConstructor  
public class Transaction {  
    @Id  
 @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
  @Column(nullable = false)  
    private Double amount;  
  
  @Column(nullable = false)  
    private String description;  
  
  private Date date;  
  
  @Column(nullable = false)  
    private String type;  
  
  @Column  
  private Boolean recurring;  
  
  @ManyToOne  
 @JsonBackReference("category-transaction")  
    @JoinColumn(name = "category_id")  
    private Category category;  
  
  @ManyToOne  
 @JsonBackReference("user-transaction")  
    @JoinColumn(name = "user_id", nullable = false)  
    private User user;  
  
  public Long getId() {  
        return id;  
  }  
  
    public void setId(Long id) {  
        this.id = id;  
  }  
  
    public Double getAmount() {  
        return amount;  
  }  
  
    public void setAmount(Double amount) {  
        this.amount = amount;  
  }  
  
    public String getDescription() {  
        return description;  
  }  
  
    public void setDescription(String description) {  
        this.description = description;  
  }  
  
    public Date getDate() {  
        return date;  
  }  
  
    public void setDate(Date date) {  
        this.date = date;  
  }  
  
    public String getType() {  
        return type;  
  }  
  
    public void setType(String type) {  
        this.type = type;  
  }  
  
    public Boolean getRecurring() {  
        return recurring;  
  }  
  
    public void setRecurring(Boolean recurring) {  
        this.recurring = recurring;  
  }  
  
    public Category getCategory() {  
        return category;  
  }  
  
    public void setCategory(Category category) {  
        this.category = category;  
  }  
  
    public User getUser() {  
        return user;  
  }  
  
    public void setUser(User user) {  
        this.user = user;  
  }  
}
```


### 5.2.1 Atributos Principais

-   **ID**: Identificador único da transação
-   **Amount**: Valor da transação (não pode ser nulo)
-   **Description**: Descrição da transação (não pode ser nulo)
-   **Date**: Data da transação
-   **Type**: Tipo da transação (receita/despesa) (não pode ser nulo)
-   **Recurring**: Indica se é uma transação recorrente
---

## 5.3 Category
```
@Entity  
@Table(name = "categories")  
@NoArgsConstructor  
@AllArgsConstructor  
public class Category {  
    @Id  
 @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
  @Column(nullable = false)  
    private String name;  
  
  @Column(length = 500)  
    private String description;  
  
  @ManyToOne  
 @JsonBackReference("user-category")  
    @JoinColumn(name = "user_id", nullable = false)  
    private User user;  
  
  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)  
    @JsonBackReference("transaction-category")  
    private List<Transaction> transactions;  
  
  public Long getId() {  
        return id;  
  }  
  
    public void setId(Long id) {  
        this.id = id;  
  }  
  
    public String getName() {  
        return name;  
  }  
  
    public void setName(String name) {  
        this.name = name;  
  }  
  
    public String getDescription() {  
        return description;  
  }  
  
    public void setDescription(String description) {  
        this.description = description;  
  }  
  
    public User getUser() {  
        return user;  
  }  
  
    public void setUser(User user) {  
        this.user = user;  
  }  
  
    public List<Transaction> getTransactions() {  
        return transactions;  
  }  
  
    public void setTransactions(List<Transaction> transactions) {  
        this.transactions = transactions;  
  }  
}
```

### 5.3.1 Atributos Principais

-   **ID**: Identificador único da categoria
-   **Name**: Nome da categoria (não pode ser nulo)
-   **Description**: Descrição detalhada da categoria (máximo 500 caracteres)

## 6. Segurança e Autenticação

### 6.1 Spring Security
O projeto utiliza Spring Security para:
- Autenticação baseada em JWT
- Autorização baseada em roles
- Proteção contra ataques comuns

### 6.2 Fluxo de Autenticação
1. Cliente envia credenciais
2. Sistema valida e gera token JWT
3. Cliente usa token para requisições subsequentes

---

## 7. API Endpoints

### 7.1 Autenticação
```
POST /api/auth/login
POST /api/auth/register
```

### 7.2 Transações
```
# Operações Básicas
GET    /api/transactions           # Lista todas as transações
POST   /api/transactions          # Cria uma nova transação
PUT    /api/transactions/{id}     # Atualiza uma transação existente
DELETE /api/transactions/{id}     # Remove uma transação
GET    /api/transactions/{id}     # Busca transação por ID

# Filtros Específicos
GET    /api/transactions/user/{userId}          # Busca transações por usuário
GET    /api/transactions/category/{categoryId}  # Busca transações por categoria
GET    /api/transactions/date                   # Busca transações por período entre data x e y
GET    /api/transactions/amount/greater         # Busca transações com valor maior que
GET    /api/transactions/amount/less            # Busca transações com valor menor que
```

### 7.3 Categorias
```
# Listagem e Busca
GET /api/categories              # Retorna todas as categorias
GET /api/categories/{id}         # Busca categoria por ID
GET /api/categories/byName/{name} # Busca categoria por nome

# Criação e Atualização
POST /api/categories            # Cria uma nova categoria
PUT  /api/categories/{id}       # Atualiza uma categoria existente

# Remoção
DELETE /api/categories/{id}     # Remove uma categoria
```

---

## 8. Contribuição

### 8.1 Processo de Desenvolvimento
1. Fork do repositório
2. Criação de branch (`feature/nova-feature`)
3. Commit das alterações
4. Push para a branch
5. Criação de Pull Request

### 8.2 Padrões de Código
- Seguir convenções de código Java
- Documentar alterações significativas
- Incluir testes unitários se possível
- Manter compatibilidade com Java 17

---

## 9. Suporte e Contato
	
### 9.1 Informações de Contato
- **Desenvolvedores**:  Juliano Laranjeira Viana, Vitória de Oliveira Fontes, Caio Moura de Lima, Luis Henrique Almeida Borges, Abner Cruz Miranda, Deusivan Ferreira da Silva.
- **GitHub**: https://github.com/JulianoL13/FinanceApiJava/tree/main

### 9.2 Suporte
Para suporte técnico ou dúvidas:
1. Abra uma issue no GitHub
2. Envie um email para a equipe de suporte
3. Consulte a documentação online

---

*Última atualização: 11/11/2024*
