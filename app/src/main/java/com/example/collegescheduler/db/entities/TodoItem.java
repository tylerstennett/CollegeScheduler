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

    public String itemName;
    public String dueDate;
    public String courseName;
    public boolean completed;
    public String username;

    public TodoItem(String itemName, String dueDate, String courseName, boolean completed, String username) {
        this.itemName = itemName;
        this.dueDate = dueDate;
        this.courseName = courseName;
        this.completed = completed;
        this.username = username;
    }
}
