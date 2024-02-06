package com.example.collegescheduler.interfaces;

import com.example.collegescheduler.db.entities.Exam;

public interface ExamDatabase {
    void deleteExamFromDatabase(Exam exam);
    void addExamToDatabase(Exam exam);

}
