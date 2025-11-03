# Biblioteka API

Uma API RESTful para gerenciamento de uma biblioteca, construída com Java e Spring Boot.

## Funcionalidades

*   **Autenticação & Autorização:** Sistema seguro de login e registro de usuários com JWT (JSON Web Tokens).
*   **Gerenciamento de Acervo:** CRUD completo para Livros, Autores e Gêneros.
*   **Gerenciamento de Usuários:** CRUD para usuários, com diferentes perfis de acesso (roles).
*   **Empréstimos:** Sistema para registrar e controlar o empréstimo de livros.
*   **Reservas:** Funcionalidade para reservar livros que estão atualmente emprestados.
*   **Multas:** Cálculo e aplicação automática de multas para devoluções em atraso.

## Tecnologias Utilizadas

*   **Backend:**
    *   Java 17
    *   Spring Boot 3
    *   Spring Security (com JWT)
    *   Spring Data JPA
*   **Banco de Dados:**
    *   PostgreSQL
*   **Build & Dependências:**
    *   Apache Maven

## Como Começar

Siga estas instruções para ter uma cópia do projeto rodando localmente para desenvolvimento e testes.

### Pré-requisitos

*   JDK 17 ou superior instalado.
*   Apache Maven instalado.
*   Um cliente PostgreSQL (opcional, para visualizar o banco de dados).

### Configuração

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/Biblioteka-API-main.git
    cd Biblioteka-API-main
    ```

2.  **Configure o ambiente:**
    O arquivo `src/main/resources/application.properties` contém as configurações necessárias para a conexão com o banco de dados e para a segurança da API.

    **Atenção:** Os dados abaixo são para um banco de dados de exemplo. **É altamente recomendável que você altere o `api.security.token.secret` e as credenciais do banco de dados para um ambiente de produção.**

    ```properties
    # Nome da Aplicação
    spring.application.name=Biblioteka API

    # Configuração do Banco de Dados (PostgreSQL Neon)
    spring.datasource.url=
    spring.datasource.username=
    spring.datasource.password=
    spring.datasource.driver-class-name=org.postgresql.Driver

    # Configuração de CORS (Cross-Origin Resource Sharing)
    cors.allowed-origins=http://localhost:3000,http://127.0.0.1:3000

    # Chave secreta para geração de Token JWT
    api.security.token.secret=avynIzJvRG6jhZJKRlGL3M6OdmNEHp464p6HQs1VnObQ
    ```

3.  **Execute a aplicação:**
    Use o Maven para compilar e iniciar o servidor Spring Boot.

    ```bash
    ./mvnw spring-boot:run
    ```
    A API estará disponível em `http://localhost:8080`.

## Licença

Este projeto está licenciado sob a Licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
