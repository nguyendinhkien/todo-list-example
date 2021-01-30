package com.nguyendinhkien.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nguyendinhkien.todolist.adapter.TodoListAdapter;
import com.nguyendinhkien.todolist.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerViewTodo;
    FloatingActionButton buttonAdd;
    List<Todo> todoList;
    TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find by id
        recyclerViewTodo = findViewById(R.id.rv_todo);
        buttonAdd = findViewById(R.id.btn_add);
        //set recyclerview
        todoList = new ArrayList<>();
        adapter = new TodoListAdapter(todoList, this);
        recyclerViewTodo.setAdapter(adapter);
        recyclerViewTodo.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTodo.setHasFixedSize(true);
        //action
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodo();
            }
        });
    }

    private void addTodo() {
        Todo todo = new Todo("test", "detail");
        todoList.add(todo);
        adapter.notifyItemInserted(todoList.size()-1);
    }
}