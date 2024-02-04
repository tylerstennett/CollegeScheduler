package com.example.collegescheduler.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.collegescheduler.db.dao.AssignmentDao;
import com.example.collegescheduler.db.dao.CourseDao;
import com.example.collegescheduler.db.dao.ExamDao;
import com.example.collegescheduler.db.dao.TodoItemDao;
import com.example.collegescheduler.db.dao.UserDao;
import com.example.collegescheduler.db.entities.*;

@Database(entities = {User.class, Assignment.class, TodoItem.class, Exam.class, Course.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ExamDao examDao();
    public abstract AssignmentDao assignmentDao();
    public abstract TodoItemDao todoItemDao();
    public abstract CourseDao courseDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) { // check if there's an instance before creating
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "AppDatabase"
            ).fallbackToDestructiveMigration().build(); // note: fallbackToDestructiveMigration() is for version changing (we scrap data in that case)
        }
        return INSTANCE;
    }


}
