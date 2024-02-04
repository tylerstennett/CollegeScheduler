package com.example.collegescheduler.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.collegescheduler.db.entities.Course;
import com.example.collegescheduler.db.entities.TodoItem;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    long insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM course WHERE username = :username")
    LiveData<List<Course>> getCoursesByUsername(String username);
}
