package com.example.collegescheduler.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="users")
public class User {
    @PrimaryKey
    public String username;
    public String password;
}
