package com.nguyendinhkien.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nguyendinhkien.todolist.model.Todo;
import com.nguyendinhkien.todolist.retrofit.RestClient;
import com.nguyendinhkien.todolist.retrofit.TodoListApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTodoActivity extends AppCompatActivity {
    EditText editTextTitle, editTextDetail;
    Button buttonAdd;
    TodoListApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        editTextDetail = findViewById(R.id.edt_detail);
        editTextTitle = findViewById(R.id.edt_title);
        buttonAdd = findViewById(R.id.btn_add);
        api = RestClient.createService(TodoListApi.class);

        Intent intent = getIntent();
        Todo todo = (Todo) intent.getSerializableExtra("TODO");
        if (todo==null){
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addTodo();
                }
            });
        }
        else {
            editTextTitle.setText(todo.getTitle());
            editTextDetail.setText(todo.getDetail());
            buttonAdd.setText("Edit");
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editTodo(todo.getId());
                }
            });
        }

    }

    private void editTodo(int id) {
        String title = editTextTitle.getText().toString();
        String detail = editTextDetail.getText().toString();
        if (title.equals("")||detail.equals("")){
            Toast.makeText(AddTodoActivity.this, "Input is invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        api.editTodo(id, new Todo(title,detail)).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AddTodoActivity.this, "Edit successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddTodoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.e("code", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Log.e("put", t.getMessage());
            }
        });
    }

    private void addTodo() {
        String title = editTextTitle.getText().toString();
        String detail = editTextDetail.getText().toString();
        if (title.equals("")||detail.equals("")){
            Toast.makeText(AddTodoActivity.this, "Input is invalid", Toast.LENGTH_SHORT).show();
            return;
        }
        api.addTodo(new Todo(title, detail)).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()){
                    Toast.makeText(AddTodoActivity.this, "Add successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddTodoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Log.e("post",t.getMessage());
            }
        });
    }
}