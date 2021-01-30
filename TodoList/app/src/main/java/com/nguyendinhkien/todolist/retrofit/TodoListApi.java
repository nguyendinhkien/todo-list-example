package com.nguyendinhkien.todolist.retrofit;

import com.nguyendinhkien.todolist.model.Todo;
import com.nguyendinhkien.todolist.model.TodoList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TodoListApi {
    @GET("todolist")
    Call<TodoList> getTodoList();
    @POST("todolist")
    Call<Todo> addTodo(@Body Todo todo);
    @PUT("todolist/edit/{id}")
    Call<Todo>editTodo(@Path("id")int id, @Body Todo todo);
    @DELETE("todolist/delete/{id}")
    Call<ResponseBody> deleteTodo(@Path("id")int id);
}
