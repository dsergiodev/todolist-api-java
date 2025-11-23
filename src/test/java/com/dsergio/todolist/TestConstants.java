package com.dsergio.todolist;

import java.util.ArrayList;
import java.util.List;

import com.dsergio.todolist.entity.Todo;

public class TestConstants {
  public static final List<Todo> TODOS = new ArrayList<>() {
    {
      add(new Todo("todo 1", "desc todo 1", false, 1));
      add(new Todo("todo 2", "desc todo 2", false, 1));
      add(new Todo("todo 3", "desc todo 3", false, 1));
      add(new Todo("todo 4", "desc todo 4", false, 1));
      add(new Todo("todo 5", "desc todo 5", false, 1));
    }
  };

  public static final Todo TODO = TODOS.get(0);
}