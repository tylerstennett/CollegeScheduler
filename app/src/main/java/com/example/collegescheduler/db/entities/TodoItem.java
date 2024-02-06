package com.example.collegescheduler.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_item",
        foreignKeys=@ForeignKey(
                entity = User.class,
                parentColumns = "username",
                childColumns = "username",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("username")})
public class TodoItem {
    @PrimaryKey(autoGenerate = true)
    public int todoItemId;

    public String username;
    public String task;
    public String taskDetails;
    public boolean completed;


    public TodoItem(String task, String taskDetails, String username, boolean completed) {
        this.task = task;
        this.taskDetails = taskDetails;
        this.username = username;
        this.completed = completed;
    }
}
