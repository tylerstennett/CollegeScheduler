package com.example.collegescheduler.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.collegescheduler.db.AppDatabase;
import com.example.collegescheduler.db.dao.ExamDao;
import com.example.collegescheduler.db.dao.TodoItemDao;
import com.example.collegescheduler.db.entities.Assignment;
import com.example.collegescheduler.db.entities.Exam;
import com.example.collegescheduler.db.entities.TodoItem;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TodoItemRepository {
    private final TodoItemDao todoItemDao;
    private final ExecutorService executorService;

    public TodoItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        todoItemDao = db.todoItemDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public CompletableFuture<Long> insertTodoItem(TodoItem todoItem) {
        return CompletableFuture.supplyAsync(() -> todoItemDao.insertTodoItem(todoItem), executorService);
    }

    public void updateTodoItem(TodoItem todoItem) {
        executorService.execute(() -> todoItemDao.updateTodoItem(todoItem));
    }

    public void deleteTodoItem(TodoItem todoItem) {
        executorService.execute(() -> todoItemDao.deleteTodoItem(todoItem));
    }

    public LiveData<List<TodoItem>> getTodoItemsByUsername(String username) {
        return todoItemDao.getTodoItemsByUsername(username);
    }
}
