package com.example.collegescheduler.interfaces;

import com.example.collegescheduler.db.entities.TodoItem;

public interface TodoListDatabase {
    void insertTodoItemToDatabase(TodoItem todoItem);
    void updateTodoItemInDatabase(TodoItem todoItem);
    void updateTodoItemWithText(TodoItem todoItem);
    void deleteTodoItemInDatabase(TodoItem todoItem);
    void updateTodoItemCompleted(boolean completed, TodoItem todoItem);
}
