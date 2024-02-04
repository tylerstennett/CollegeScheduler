package com.example.collegescheduler.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.collegescheduler.db.entities.TodoItem;

import java.util.List;

@Dao
public interface TodoItemDao {
    @Insert
    long insertTodoItem(TodoItem todoItem);

    @Update
    void updateTodoItem(TodoItem todoItem);

    @Delete
    void deleteTodoItem(TodoItem todoItem);

    @Query("SELECT * FROM todo_item WHERE username = :username")
    LiveData<List<TodoItem>> getTodoItemsByUsername(String username);

    @Query("SELECT * FROM todo_item WHERE todoItemId = :todoItemId")
    LiveData<TodoItem> getTodoItemById(int todoItemId);
}
