package com.example.collegescheduler.interfaces;

import com.example.collegescheduler.db.entities.Assignment;

public interface AssignmentDatabase {
    void insertAssignmentToDatabase(Assignment assignment);
    void updateAssignmentInDatabase(Assignment assignment);
    void updateAssignmentWithText(Assignment assignment);
    void deleteAssignmentInDatabase(Assignment assignment);
    void updateAssignmentCompleted(boolean completed, Assignment assignment);

}
