package com.example.collegescheduler.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.collegescheduler.db.AppDatabase;
import com.example.collegescheduler.db.dao.AssignmentDao;
import com.example.collegescheduler.db.entities.Assignment;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssignmentRepository {
    private final AssignmentDao assignmentDao;
    private final ExecutorService executorService;

    public AssignmentRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        assignmentDao = db.assignmentDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public CompletableFuture<Long> insertAssignment(Assignment assignment) {
        return CompletableFuture.supplyAsync(() -> assignmentDao.insertAssignment(assignment), executorService);
    }

    public void updateAssignment(Assignment assignment) {
        executorService.execute(() -> assignmentDao.updateAssignment(assignment));
    }

    public void deleteAssignment(Assignment assignment) {
        executorService.execute(() -> assignmentDao.deleteAssignment(assignment));
    }

    public LiveData<List<Assignment>> getAssignmentsByUsername(String username) {
        return assignmentDao.getAssignmentsByUsername(username);
    }

}
