package com.example.collegescheduler.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "classes",
        foreignKeys=@ForeignKey(
                entity = User.class,
                parentColumns = "username",
                childColumns = "username",
                onDelete = ForeignKey.CASCADE
        ))
public class Classes {

    @PrimaryKey(autoGenerate = true)
    public int classId;

    public String username;
    public String className;
    public Date classTime;
    public String daysOfWeek;
    public String professor;
    public String section;
    public String location;

}
