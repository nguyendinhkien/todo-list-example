package com.nguyendinhkien.todolist.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyendinhkien.todolist.AddTodoActivity;
import com.nguyendinhkien.todolist.R;
import com.nguyendinhkien.todolist.model.Todo;
import com.nguyendinhkien.todolist.retrofit.RestClient;
import com.nguyendinhkien.todolist.retrofit.TodoListApi;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoViewHolder> {
    List<Todo> todoList;
    Context context;
    TodoListApi api;

    public TodoListAdapter(List<Todo> todoList, Context context) {
        this.todoList = todoList;
        this.context = context;
        api = RestClient.createService(TodoListApi.class);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.textViewTitle.setText(todo.getTitle());
        holder.textViewDetail.setText(todo.getDetail());
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(todo,position);
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditActivity(todo);
            }
        });
    }

    private void openEditActivity(Todo todo) {
        Intent intent = new Intent(context, AddTodoActivity.class);
        intent.putExtra("TODO", todo);
        context.startActivity(intent);
    }

    private void openDialog(Todo todo, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Xoá")
                .setTitle("Bạn muốn xoá ghi nhớ này?")
                .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        api.deleteTodo(todo.getId()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(context, "Delete successfully", Toast.LENGTH_SHORT).show();
                                    todoList.remove(position);
                                    notifyItemRemoved(position);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("delete", t.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
    }


    @Override
    public int getItemCount() {
        return todoList.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle,textViewDetail;
        LinearLayout layout;
        ImageView imageViewDelete;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.txt_title);
            textViewDetail = itemView.findViewById(R.id.txt_detail);
            layout = itemView.findViewById(R.id.layout_item_todo);
            imageViewDelete = itemView.findViewById(R.id.img_delete);

        }
    }
}
