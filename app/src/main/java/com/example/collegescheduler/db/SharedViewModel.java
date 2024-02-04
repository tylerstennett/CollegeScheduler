package com.example.collegescheduler.db;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.collegescheduler.db.entities.Assignment;
import com.example.collegescheduler.db.entities.Course;
import com.example.collegescheduler.db.entities.Exam;
import com.example.collegescheduler.db.entities.User;
import com.example.collegescheduler.db.repositories.AssignmentRepository;
import com.example.collegescheduler.db.repositories.CourseRepository;
import com.example.collegescheduler.db.repositories.ExamRepository;
import com.example.collegescheduler.db.repositories.TodoItemRepository;
import com.example.collegescheduler.db.repositories.UserRepository;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {
    private final AssignmentRepository assignmentRepository;
    private final CourseRepository courseRepository;
    private final ExamRepository examRepository;
    private final TodoItemRepository todoItemRepository;
    private final UserRepository userRepository;
    private final MutableLiveData<String> username = new MutableLiveData<>(); // keep location immutable
    private MutableLiveData<Long> insertAssignmentId = new MutableLiveData<>();
    private MutableLiveData<Long> insertCourseId = new MutableLiveData<>();
    private MutableLiveData<Long> insertExamId = new MutableLiveData<>();
    private MutableLiveData<Long> insertTodoItemId = new MutableLiveData<>();

    public SharedViewModel(Application application) {
        super(application);
        this.assignmentRepository = new AssignmentRepository(application);
        this.courseRepository = new CourseRepository(application);
        this.examRepository = new ExamRepository(application);
        this.todoItemRepository = new TodoItemRepository(application);
        this.userRepository = new UserRepository(application);
    }

    // getters and setters for the shared username value; not to be confused with actual queries


    public LiveData<String> getUsernameData() {
        return this.username;
    }
    public void setUsernameValue(String usernameValue) {
        this.username.setValue(usernameValue);
    }

    // functions for assignments
    public LiveData<List<Assignment>> getAssignmentsByUsername(String username) {
        return assignmentRepository.getAssignmentsByUsername(username);
    }
    public LiveData<Long> getInsertAssignmentId() {
        return insertAssignmentId; // IMPLEMENT OBSERVER IN FRAGMENT
    }
    public void insertAssignment(Assignment assignment) {
        assignmentRepository.insertAssignment(assignment).thenAccept(insertAssignmentId::postValue); // assign long to insertAssignmentId
    }
    public void updateAssignment(Assignment assignment) {
        assignmentRepository.updateAssignment(assignment);
    }
    public void deleteAssignment(Assignment assignment) {
        assignmentRepository.deleteAssignment(assignment);
    }

    // functions for courses
    public LiveData<List<Course>> getCoursesByUsername(String username) {
        return courseRepository.getCoursesByUsername(username);
    }
    public LiveData<Long> getInsertCourseId() {
        return insertCourseId; // IMPLEMENT OBSERVER IN FRAGMENT
    }
    public void insertCourse(Course course) {
        courseRepository.insertCourse(course).thenAccept(insertCourseId::postValue); // assign long to insertAssignmentId
    }
    public void updateCourse(Course course) {
        courseRepository.updateCourse(course);
    }
    public void deleteCourse(Course course) {
        courseRepository.deleteCourse(course);
    }

    // functions for exams
    public LiveData<List<Exam>> getExamsByUsername(String username) {
        return examRepository.getExamsByUsername(username);
    }
    public void insertExam(Exam exam) {
        examRepository.insertExam(exam).thenAccept(insertExamId::postValue);
    }

    // functions for user
    public LiveData<User> getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
    public void insertUser(User user) {
        userRepository.insertUser(user);
    }
}
