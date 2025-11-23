package com.dsergio.todolist.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dsergio.todolist.entity.Todo;
import com.dsergio.todolist.exception.BadRequestException;
import com.dsergio.todolist.exception.TodoNotFoundException;
import com.dsergio.todolist.repository.TodoRepository;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> list() {
        return todoRepository.findAll(Sort.by("priority").descending());
    }

    public Todo getById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found! " + id));
    }

    public List<Todo> create(Todo todo) {
        todoRepository.save(todo);
        return list();
    }

    public List<Todo> update(Long id, Todo todo) {

        Todo entity = todoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Todo %d não existe!".formatted(id)));

        // copia campos do objeto recebido
        entity.setName(todo.getName());
        entity.setDescription(todo.getDescription());
        entity.setDone(todo.isDone());
        entity.setPriority(todo.getPriority());

        todoRepository.save(entity);

        return list();
    }

    public List<Todo> delete(Long id) {
        todoRepository.findById(id).ifPresentOrElse((existingTodo) -> todoRepository.delete(existingTodo), () -> {
          throw new BadRequestException("Todo %d não existe! ".formatted(id));
        });
        return list();
      }
}
