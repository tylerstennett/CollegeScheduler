package com.example.collegescheduler.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.collegescheduler.db.entities.Exam;
import com.example.collegescheduler.db.entities.TodoItem;

import java.util.List;

@Dao
public interface ExamDao {
    @Insert
    long insertExam(Exam exam);

    @Update
    void updateExam(Exam exam);

    @Delete
    void deleteExam(Exam exam);

    @Query("SELECT * FROM exam WHERE username = :username")
    LiveData<List<Exam>> getExamsByUsername(String username);

    @Query("SELECT * FROM exam WHERE examId = :examId")
    LiveData<Exam> getExamById(int examId);
}
