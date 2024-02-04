package com.example.collegescheduler.ui.assignments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.collegescheduler.R;

public class AssignmentsFragment extends Fragment {

    private EditText editTextTitle;
    private EditText editTextDueDate;
    private EditText editTextTaskDetail;
    private LinearLayout inputContainer;
    private ListView listViewTasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignments, container, false);

        // Initialize views
        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDueDate = view.findViewById(R.id.editTextDueDate);
        editTextTaskDetail = view.findViewById(R.id.editTextTaskDetail);
        inputContainer = view.findViewById(R.id.inputContainer);
        listViewTasks = view.findViewById(R.id.listViewTasks);

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClick();
            }
        });

        return view;
    }

    private void onAddButtonClick() {
        // Get the text from the EditTexts
        String dueDate = editTextDueDate.getText().toString();
        String title = editTextTitle.getText().toString();
        String taskDetail = editTextTaskDetail.getText().toString();

        // Create a new ConstraintLayout for the task
        View taskView = createTaskView(dueDate,title,taskDetail);

        // Add the taskView to the inputContainer
        inputContainer.addView(taskView);

        // Clear input fields
        editTextTitle.getText().clear();
        editTextDueDate.getText().clear();
        editTextTaskDetail.getText().clear();
    }

    private View createTaskView(String title, String dueDate, String associatedClass) {
        // Inflate the task layout
        View taskView = getLayoutInflater().inflate(R.layout.assignment_add_on, null);

        // Set the values for the TextViews
        TextView textViewTitle = taskView.findViewById(R.id.textViewTitle);
        TextView textViewDueDate = taskView.findViewById(R.id.textViewDueDate);
        TextView textViewAssociatedClass = taskView.findViewById(R.id.textViewAssociatedClass);

        textViewTitle.setText(title);
        textViewDueDate.setText(dueDate);
        textViewAssociatedClass.setText(associatedClass);

        return taskView;
    }
}