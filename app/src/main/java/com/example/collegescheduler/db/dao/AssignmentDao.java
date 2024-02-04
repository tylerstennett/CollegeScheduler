package com.example.collegescheduler.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.collegescheduler.db.entities.Assignment;

import java.util.List;

@Dao
public interface AssignmentDao {
    @Insert
    long insertAssignment(Assignment assignment);

    @Update
    void updateAssignment(Assignment assignment);

    @Delete
    void deleteAssignment(Assignment assignment);

    @Query("SELECT * FROM assignment WHERE username = :username")
    LiveData<List<Assignment>> getAssignmentsByUsername(String username);

    @Query("SELECT * FROM assignment where assignmentId = :assignmentId")
    LiveData<Assignment> getAssignmentById(int assignmentId);

}
