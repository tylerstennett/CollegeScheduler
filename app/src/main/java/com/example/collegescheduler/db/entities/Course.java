package com.example.collegescheduler.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "course",
        foreignKeys=@ForeignKey(
                entity = User.class,
                parentColumns = "username",
                childColumns = "username",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("username")})
public class Course {

    @PrimaryKey(autoGenerate = true)
    public int courseId;

    public String username;
    public String courseName;
    public String courseTime;
    public String daysOfWeek;
    public String professor;
    public String section;
    public String location;

    public Course(String username, String courseName, String courseTime, String daysOfWeek, String professor, String section, String location) {
        this.username = username;
        this.courseName = courseName;
        this.courseTime = courseTime;
        this.daysOfWeek = daysOfWeek;
        this.professor = professor;
        this.section = section;
        this.location = location;
    }

}
