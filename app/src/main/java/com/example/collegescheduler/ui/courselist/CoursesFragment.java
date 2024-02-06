package com.example.collegescheduler.ui.courselist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.SharedViewModel;
import com.example.collegescheduler.db.entities.Course;
import com.example.collegescheduler.db.entities.Exam;
import com.example.collegescheduler.interfaces.CourseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment implements CoursesAdapter.EditListener, CourseDatabase {
    private SharedViewModel sharedViewModel;
    private String username;

    private EditText editTextCourseName;
    private EditText editTextTime;
    private EditText editTextLocation;
    private EditText editTextInstructor;
    private EditText editTextCourseSection;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private boolean mondayChecked;
    private boolean tuesdayChecked;
    private boolean wednesdayChecked;
    private boolean thursdayChecked;
    private boolean fridayChecked;
    private ConstraintLayout inputContainer;
    private RecyclerView recyclerViewCourses;
    private CoursesAdapter coursesAdapter;
    List<Course> list;

    public static CoursesFragment newInstance() {
        return new CoursesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_courses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.username = sharedViewModel.getUsernameData().getValue();

        // init views
        editTextCourseName = view.findViewById(R.id.editTextCourseName);
        editTextTime = view.findViewById(R.id.editTextCourseTime);
        editTextLocation = view.findViewById(R.id.editTextCourseLocation);
        editTextInstructor = view.findViewById(R.id.editTextInstructor);
        editTextCourseSection = view.findViewById(R.id.editTextCourseSection);
        inputContainer = view.findViewById(R.id.inputContainer);
        recyclerViewCourses = view.findViewById(R.id.recyclerViewCourses);

        // sets listeners for each day of the week and sets booleans accordingly
        monday = view.findViewById(R.id.checkBoxMonday);
        monday.setOnCheckedChangeListener((buttonView, isChecked) -> mondayChecked = isChecked);

        tuesday = view.findViewById(R.id.checkBoxTuesday);
        tuesday.setOnCheckedChangeListener((buttonView, isChecked) -> tuesdayChecked = isChecked);

        wednesday = view.findViewById(R.id.checkBoxWednesday);
        wednesday.setOnCheckedChangeListener((buttonView, isChecked) -> wednesdayChecked = isChecked);

        thursday = view.findViewById(R.id.checkBoxThursday);
        thursday.setOnCheckedChangeListener((buttonView, isChecked) -> thursdayChecked = isChecked);

        friday = view.findViewById(R.id.checkBoxFriday);
        friday.setOnCheckedChangeListener((buttonView, isChecked) -> fridayChecked = isChecked);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCourses.setLayoutManager(layoutManager);

        list = new ArrayList<Course>();

        coursesAdapter = new CoursesAdapter(list, this, this);
        recyclerViewCourses.setAdapter(coursesAdapter);

        Button addButton = view.findViewById(R.id.addButtonCourse);

        sharedViewModel.getCoursesByUsername(this.username).observe(getViewLifecycleOwner(), courses -> {
            if (courses != null) {
                list.clear();
                coursesAdapter.addCourseList(courses);
            }
        });

        addButton.setOnClickListener(v -> onAddButtonClick());
    }

    @Override
    public void addCourseToDatabase(Course course) {
        sharedViewModel.insertCourse(course);
    }

    @Override
    public void deleteCourseFromDatabase(Course course) {
        sharedViewModel.deleteCourse(course);
    }

    @Override
    public void updateCourseWithText(Course course) {
        String courseName = editTextCourseName.getText().toString();
        String courseTime = editTextTime.getText().toString();
        String courseLocation = editTextLocation.getText().toString();
        String courseInstructor = editTextInstructor.getText().toString();
        String courseSection = editTextCourseSection.getText().toString();
        String courseDaysOfWeek = getDaysOfWeek();

        course.username = this.username;
        course.courseName = courseName;
        course.courseTime = courseTime;
        course.daysOfWeek = courseDaysOfWeek;
        course.professor = courseInstructor;
        course.section = courseSection;
        course.location = courseLocation;

        resetInputs();

        this.updateCourseInDatabase(course);
    }

    @Override
    public void updateCourseInDatabase(Course course) {
        sharedViewModel.updateCourse(course);
    }

    public void onAddButtonClick() {
        // Get text from input fields
        String courseName = editTextCourseName.getText().toString();
        String courseTime = editTextTime.getText().toString();
        String courseLocation = editTextLocation.getText().toString();
        String courseInstructor = editTextInstructor.getText().toString();
        String courseSection = editTextCourseSection.getText().toString();

        // create string for days of the week based on checkboxes
        String courseDaysOfWeek = getDaysOfWeek();

        Course course = new Course(this.username, courseName, courseTime, courseDaysOfWeek, courseInstructor, courseSection, courseLocation);
        this.addCourseToDatabase(course);

        //coursesAdapter.addItem(course);

        resetInputs();
    }

    // discontinued
    public void onEditButtonClick(int position) {
        // Get text from input fields
        String courseName = editTextCourseName.getText().toString();
        String courseTime = editTextTime.getText().toString();
        String courseLocation = editTextLocation.getText().toString();
        String courseInstructor = editTextInstructor.getText().toString();
        String courseSection = editTextCourseSection.getText().toString();

        // create string for days of the week based on checkboxes
        String courseDaysOfWeek = getDaysOfWeek();

        Course course = new Course("", courseName, courseTime, courseDaysOfWeek, courseInstructor, courseSection, courseLocation);

        coursesAdapter.editItem(course, position);

        resetInputs();
    }

    // helper method to create String for recurring days of the week
    private String getDaysOfWeek() {

        String courseDaysOfWeek = "";
        if (mondayChecked) courseDaysOfWeek += "M ";
        if (tuesdayChecked) courseDaysOfWeek += "Tu ";
        if (wednesdayChecked) courseDaysOfWeek += "W ";
        if (thursdayChecked) courseDaysOfWeek += "Th ";
        if (fridayChecked) courseDaysOfWeek += "F";
        courseDaysOfWeek = courseDaysOfWeek.trim();

        return courseDaysOfWeek;
    }

    private void resetInputs() {
        // clear input fields
        editTextCourseName.getText().clear();
        editTextTime.getText().clear();
        editTextLocation.getText().clear();
        editTextInstructor.getText().clear();
        editTextCourseSection.getText().clear();

        // clear checkboxes
        monday.setChecked(false);
        tuesday.setChecked(false);
        wednesday.setChecked(false);
        thursday.setChecked(false);
        friday.setChecked(false);
    }
}
