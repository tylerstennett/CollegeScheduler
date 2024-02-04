package com.example.collegescheduler.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.collegescheduler.db.AppDatabase;
import com.example.collegescheduler.db.dao.AssignmentDao;
import com.example.collegescheduler.db.dao.CourseDao;
import com.example.collegescheduler.db.entities.Assignment;
import com.example.collegescheduler.db.entities.Course;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseRepository {
    private final CourseDao courseDao;
    private final ExecutorService executorService;

    public CourseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        courseDao = db.courseDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public CompletableFuture<Long> insertCourse(Course course) {
        return CompletableFuture.supplyAsync(() -> courseDao.insertCourse(course), executorService);
    }

    public void updateCourse(Course course) {
        executorService.execute(() -> courseDao.updateCourse(course));
    }

    public void deleteCourse(Course course) {
        executorService.execute(() -> courseDao.deleteCourse(course));
    }

    public LiveData<List<Course>> getCoursesByUsername(String username) {
        return courseDao.getCoursesByUsername(username);
    }
}
