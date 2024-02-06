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

    public String task;
    public String taskDetails;
    public boolean completed;


    public TodoItem(String task, String taskDetails) {
        this.task = task;
        this.taskDetails = taskDetails;
    }
}
