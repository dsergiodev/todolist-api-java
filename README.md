# TODO List

API para gerenciar tarefas (CRUD) que faz parte [desse
desafio](https://github.com/simplify-liferay/desafio-junior-backend-simplify).

## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Mysql](https://dev.mysql.com/downloads/)

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

{
    "desciption": "Desc Todo 1",
    "name": "Todo 1",
    "priority": 1,
    "done": false
}

### Listar Tarefas

`GET /todos`

### Atualizar Tarefa

`PUT /todos/{id}`
{
    "desciption": "Desc Todo 11",
    "name": "Todo 11",
    "priority": 1,
    "done": true
}

### Remover Tarefa

`DELETE /todos/{id}`


## 📘 Documentação da API (Swagger)

A API possui documentação interativa gerada automaticamente pelo Swagger/OpenAPI.

Após subir o projeto, acesse:

👉 http://localhost:8080/swagger-ui/index.html

Nessa página você pode visualizar todas as rotas, parâmetros, respostas e até realizar testes diretamente pela interface.
