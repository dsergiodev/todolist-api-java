package com.dsergio.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsergio.todolist.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    
}
