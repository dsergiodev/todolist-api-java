package com.dsergio.todolist;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Map;

import com.dsergio.todolist.entity.Todo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/remove.sql") // limpa antes da classe de teste
class TodolistApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // helper: busca o primeiro id presente após importar dados
    private Long getFirstTodoId() throws Exception {
        MvcResult result = mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<Map<String, Object>> list = objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
        });

        if (list.isEmpty()) {
            return null;
        }

        Number idNumber = (Number) list.get(0).get("id");
        return idNumber == null ? null : idNumber.longValue();
    }

    @Test
    void testCreateTodoSuccess() throws Exception {
        var todo = new Todo("todo 1", "desc todo 1", false, 1);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(todo.getName()))
                .andExpect(jsonPath("$[0].description").value(todo.getDescription()))
                .andExpect(jsonPath("$[0].done").value(todo.isDone()))
                .andExpect(jsonPath("$[0].priority").value(todo.getPriority()));
    }

    @Test
    void testCreateTodoFailure() throws Exception {
        var invalid = new Todo("", "", false, 0);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Sql("/import.sql")
    @Test
    void testUpdateTodoSuccess() throws Exception {
        // pega um id existente (o import.sql deve popular)
        Long id = getFirstTodoId();
        if (id == null) {
            // se não tiver dados, falha o teste com mensagem clara
            throw new IllegalStateException("Nenhum todo encontrado após import.sql");
        }

        // monta payload de atualização
        var updated = new Todo(id, "UPDATED name", "UPDATED description", true, 9);

        mockMvc.perform(put("/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                // espera que lista exista e contenha pelo menos 1 elemento
                .andExpect(jsonPath("$.length()").isNotEmpty())
                .andExpect(jsonPath("$[0].name").value(updated.getName()))
                .andExpect(jsonPath("$[0].description").value(updated.getDescription()))
                .andExpect(jsonPath("$[0].done").value(updated.isDone()))
                .andExpect(jsonPath("$[0].priority").value(updated.getPriority()));
    }

    @Test
    void testUpdateTodoFailure() throws Exception {
        var invalid = new Todo(99999L, "", "", false, 0);

        mockMvc.perform(put("/todos/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Sql("/import.sql")
    @Test
    void testDeleteTodoSuccess() throws Exception {
        Long id = getFirstTodoId();
        if (id == null) {
            throw new IllegalStateException("Nenhum todo encontrado após import.sql");
        }

        // Deleta o primeiro
        mockMvc.perform(delete("/todos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4))
                // valida o novo primeiro elemento (já que o primeiro foi removido)
                .andExpect(jsonPath("$[0].name").value(TestConstants.TODOS.get(1).getName()))
                .andExpect(jsonPath("$[0].description").value(TestConstants.TODOS.get(1).getDescription()))
                .andExpect(jsonPath("$[0].done").value(TestConstants.TODOS.get(1).isDone()))
                .andExpect(jsonPath("$[0].priority").value(TestConstants.TODOS.get(1).getPriority()));
    }

    @Test
    void testDeleteTodoFailure() throws Exception {
        mockMvc.perform(delete("/todos/99999"))
                .andExpect(status().isBadRequest());
    }

    @Sql("/import.sql")
    @Test
    void testListTodos() throws Exception {
        MvcResult result = mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<Map<String, Object>> list = objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
        });

        // valida tamanho e alguns campos (sem comparar ids, que são gerados pelo banco)
        org.assertj.core.api.Assertions.assertThat(list).hasSize(5);
        org.assertj.core.api.Assertions.assertThat(list.get(0).get("name")).isEqualTo(TestConstants.TODOS.get(0).getName());
        org.assertj.core.api.Assertions.assertThat(list.get(1).get("name")).isEqualTo(TestConstants.TODOS.get(1).getName());
        org.assertj.core.api.Assertions.assertThat(list.get(2).get("name")).isEqualTo(TestConstants.TODOS.get(2).getName());
        org.assertj.core.api.Assertions.assertThat(list.get(3).get("name")).isEqualTo(TestConstants.TODOS.get(3).getName());
        org.assertj.core.api.Assertions.assertThat(list.get(4).get("name")).isEqualTo(TestConstants.TODOS.get(4).getName());
    }
}
