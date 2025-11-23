package com.dsergio.todolist.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsergio.todolist.entity.Todo;
import com.dsergio.todolist.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/todos")
public class TodoController {
  @Autowired
  private TodoService todoService;

  @Operation(summary = "Cria uma nova tarefa")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
      @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
  })
  @PostMapping
  ResponseEntity<List<Todo>> create(@Valid @RequestBody Todo todo) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(todoService.create(todo));
  }

  @Operation(summary = "Lista todas as tarefas")
  @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
  @GetMapping
  List<Todo> list() {
    return todoService.list();
  }

  @Operation(summary = "Atualiza uma tarefa existente")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
    @ApiResponse(responseCode = "400", description = "Tarefa não encontrada ou dados inválidos")
  })
  @PutMapping("{id}")
  List<Todo> update(@PathVariable Long id, @RequestBody Todo todo) {
    return todoService.update(id, todo);
  }

  @Operation(summary = "Remove uma tarefa pelo ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Tarefa removida com sucesso"),
    @ApiResponse(responseCode = "400", description = "Tarefa não encontrada")
  })
  @DeleteMapping("{id}")
  List<Todo> delete(@PathVariable Long id) {
    return todoService.delete(id);
  }
}