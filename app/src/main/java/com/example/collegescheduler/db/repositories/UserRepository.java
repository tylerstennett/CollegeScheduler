package com.example.collegescheduler.db.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.collegescheduler.db.AppDatabase;
import com.example.collegescheduler.db.dao.UserDao;
import com.example.collegescheduler.db.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private final UserDao userDao;
    private final ExecutorService executorService;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertUser(User user) {
        executorService.execute(() -> userDao.insertUser(user));
    }

    public LiveData<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

}
