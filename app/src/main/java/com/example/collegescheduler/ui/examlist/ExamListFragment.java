package com.example.collegescheduler.ui.examlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.collegescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class ExamListFragment extends Fragment {

    private EditText editTextExamName;
    private EditText editTextDate;
    private EditText editTextTime;
    private EditText editTextLocation;
    private EditText editTextClassName;
    private ConstraintLayout inputContainer;
    private RecyclerView recyclerViewExams;
    private ExamListAdapter examListAdapter;
    List<Exam> list;

    public static ExamListFragment newInstance() {
        return new ExamListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_list, container, false);

        // init views
        editTextExamName = view.findViewById(R.id.editTextExamName);
        editTextDate = view.findViewById(R.id.editTextExamDate);
        editTextTime = view.findViewById(R.id.editTextExamTime);
        editTextLocation = view.findViewById(R.id.editTextExamLocation);
        editTextClassName = view.findViewById(R.id.editTextExamClass);
        inputContainer = view.findViewById(R.id.inputContainer);
        recyclerViewExams = view.findViewById(R.id.recyclerViewExams);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewExams.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        examListAdapter = new ExamListAdapter(list);
        recyclerViewExams.setAdapter(examListAdapter);

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClick();
            }
        });

        return view;
    }

    public void onAddButtonClick() {
        // Get text from input fields
        String examName = editTextExamName.getText().toString();
        String examDate = editTextDate.getText().toString();
        String examTime = editTextTime.getText().toString();
        String examLocation = editTextLocation.getText().toString();
        String examClassName = editTextClassName.getText().toString();

        // create new exam card
        // View examView = createExamView(examName, examDate, examTime, examLocation, examClassName);

        Exam exam = new Exam(examName, examDate, examTime, examLocation, examClassName);

        // recyclerViewExams.addView(examView);
        examListAdapter.addItem(exam);

        // clear input fields
        editTextExamName.getText().clear();
        editTextDate.getText().clear();
        editTextTime.getText().clear();
        editTextLocation.getText().clear();
        editTextClassName.getText().clear();
    }

    private View createExamView(String examName, String examDate, String examTime,
                                String examLocation, String examClassName) {

        View examView = getLayoutInflater().inflate(R.layout.exam_entry, null);

        TextView textViewExamName = examView.findViewById(R.id.examName);
        TextView textViewExamDate = examView.findViewById(R.id.examDate);
        TextView textViewExamTime = examView.findViewById(R.id.examTime);
        TextView textViewExamLocation = examView.findViewById(R.id.examLocation);
        TextView textViewExamClassName = examView.findViewById(R.id.examCourse);

        textViewExamName.setText(examName);
        textViewExamDate.setText(examDate);
        textViewExamTime.setText(examTime);
        textViewExamLocation.setText(examLocation);
        textViewExamClassName.setText(examClassName);

        return examView;
    }
}
