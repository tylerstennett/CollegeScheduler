package com.example.collegescheduler.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.collegescheduler.db.entities.User;

@Dao
public interface UserDao {
    @Insert
    long insertUser(User user);

    @Query("SELECT * FROM user WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

}
