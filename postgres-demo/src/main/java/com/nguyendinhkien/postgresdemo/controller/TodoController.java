package com.nguyendinhkien.postgresdemo.controller;

import com.nguyendinhkien.postgresdemo.exception.ResourceNotFoundException;
import com.nguyendinhkien.postgresdemo.model.Todo;
import com.nguyendinhkien.postgresdemo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todolist")
    public Page<Todo> getTodoList(Pageable pageable){
        return todoRepository.findAll(pageable);
    }

    @PostMapping("/todolist")
    public Todo createTodo(@Valid @RequestBody Todo todo){
        return todoRepository.save(todo);
    }
    @PutMapping("/todolist/{todoId}")
    public Todo updateTodoList(@PathVariable Long todoId,
                               @Valid @RequestBody Todo todoRequest){
        return todoRepository.findById(todoId)
                .map(todo -> {
                    todo.setTitle(todoRequest.getTitle());
                    todo.setDescription(todoRequest.getDescription());
                    return todoRepository.save(todo);
                }).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id " + todoId));
    }
    @DeleteMapping("/todolist/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long todoId){
        return todoRepository.findById(todoId)
                .map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().build();
                }).orElseThrow(()->new ResourceNotFoundException("Todo not found with id " + todoId));
    }
}
