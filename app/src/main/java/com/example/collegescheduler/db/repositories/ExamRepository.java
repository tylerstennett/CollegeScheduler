package com.example.collegescheduler.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.collegescheduler.db.AppDatabase;
import com.example.collegescheduler.db.dao.CourseDao;
import com.example.collegescheduler.db.dao.ExamDao;
import com.example.collegescheduler.db.entities.Assignment;
import com.example.collegescheduler.db.entities.Course;
import com.example.collegescheduler.db.entities.Exam;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExamRepository {
    private final ExamDao examDao;
    private final ExecutorService executorService;

    public ExamRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        examDao = db.examDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public CompletableFuture<Long> insertExam(Exam exam) {
        return CompletableFuture.supplyAsync(() -> examDao.insertExam(exam), executorService);
    }

    public void updateExam(Exam exam) {
        executorService.execute(() -> examDao.updateExam(exam));
    }

    public void deleteExam(Exam exam) {
        executorService.execute(() -> examDao.deleteExam(exam));
    }

    public LiveData<List<Exam>> getExamsByUsername(String username) {
        return examDao.getExamsByUsername(username);
    }
}
