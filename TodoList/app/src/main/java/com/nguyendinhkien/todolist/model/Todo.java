package com.nguyendinhkien.todolist.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Todo implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String detail;

    public Todo(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
