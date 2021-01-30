package com.nguyendinhkien.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nguyendinhkien.todolist.adapter.TodoListAdapter;
import com.nguyendinhkien.todolist.model.Todo;
import com.nguyendinhkien.todolist.model.TodoList;
import com.nguyendinhkien.todolist.retrofit.RestClient;
import com.nguyendinhkien.todolist.retrofit.TodoListApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        recyclerViewTodo.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTodo.setHasFixedSize(true);
        TodoListApi api = RestClient.createService(TodoListApi.class);
        api.getTodoList().enqueue(new Callback<TodoList>() {
            @Override
            public void onResponse(Call<TodoList> call, Response<TodoList> response) {
                todoList = response.body().getTodoList();
                System.out.println(todoList.size());
                adapter = new TodoListAdapter(todoList, MainActivity.this);
                recyclerViewTodo.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TodoList> call, Throwable t) {
                Log.e("get", t.getMessage());
            }
        });
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