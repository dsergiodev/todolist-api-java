# TODO List

API para gerenciar tarefas (CRUD) que faz parte [desse
desafio](https://github.com/simplify-liferay/desafio-junior-backend-simplify).

## Tecnologias

-   Spring Boot
-   Spring MVC
-   Spring Data JPA
-   SpringDoc OpenAPI 3
-   MySQL

## Práticas adotadas

-   SOLID, DRY, YAGNI, KISS
-   API REST
-   Consultas com Spring Data JPA
-   Injeção de Dependências
-   Tratamento de respostas de erro
-   Geração automática do Swagger

## Como Executar

    ./mvnw clean package
    java -jar target/todolist-0.0.1-SNAPSHOT.jar

## Endpoints

### Criar Tarefa

`POST /todos`

### Listar Tarefas

`GET /todos`

### Atualizar Tarefa

`PUT /todos/{id}`

### Remover Tarefa

`DELETE /todos/{id}`


## 📘 Documentação da API (Swagger)

A API possui documentação interativa gerada automaticamente pelo Swagger/OpenAPI.

Após subir o projeto, acesse:

👉 http://localhost:8080/swagger-ui/index.html

Nessa página você pode visualizar todas as rotas, parâmetros, respostas e até realizar testes diretamente pela interface.
