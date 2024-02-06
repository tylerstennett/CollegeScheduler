package com.example.collegescheduler.interfaces;

import com.example.collegescheduler.db.entities.Course;

public interface CourseDatabase {
    void addCourseToDatabase(Course course);
    void deleteCourseFromDatabase(Course course);
    void updateCourseInDatabase(Course course);
    void updateCourseWithText(Course course);
}
