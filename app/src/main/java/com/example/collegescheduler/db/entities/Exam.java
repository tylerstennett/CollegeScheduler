package com.example.collegescheduler.db.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "exam",
        foreignKeys=@ForeignKey(
                entity = User.class,
                parentColumns = "username",
                childColumns = "username",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("username")})
public class Exam {
    @PrimaryKey(autoGenerate = true)
    public int examId;
    public String username;
    public String examName;
    public String date;
    public String courseName;
    public String time;
    public String location;
    public Exam(String username, String examName, String date, String courseName, String time, String location) {
        this.username = username;
        this.examName = examName;
        this.date = date;
        this.courseName = courseName;
        this.time = time;
        this.location = location;
    }

}