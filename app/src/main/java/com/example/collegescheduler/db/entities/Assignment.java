package com.example.collegescheduler.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "assignment",
        foreignKeys=@ForeignKey(
                entity = User.class,
                parentColumns = "username",
                childColumns = "username",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("username")})
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    public int assignmentId;

    public String assignmentName;
    public String dueDate;
    public String courseName;
    public String username;

    public Assignment(String assignmentName, String dueDate, String courseName) {
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
        this.courseName = courseName;
        this.username = username;
    }

}
