package com.example.collegescheduler.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "exams",
        foreignKeys=@ForeignKey(
                entity = User.class,
                parentColumns = "username",
                childColumns = "username",
                onDelete = ForeignKey.CASCADE
        ))
public class Exams {
    @PrimaryKey(autoGenerate = true)
    public int examId;

    public String username;
    public String examName;
    public Date date;
    public String course;
    public String time;
    public String location;
}
