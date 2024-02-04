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
import java.util.ArrayList;

/**
 * Fragment for managing assignments, including adding, editing, and deleting tasks.
 */
public class AssignmentsFragment extends Fragment {
    private EditText editTextAssignment;
    private EditText editTextDueDate;
    private EditText editTextClassName;
    private LinearLayout inputContainer;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignments, container, false);
        View addview = inflater.inflate(R.layout.assignment_add_on, container, false);

        // Initialize the adapter for the ListView
        CustomAssignment adapter = new CustomAssignment(requireContext(), R.layout.assignment_add_on, new ArrayList<>());
        ListView listView = view.findViewById(R.id.assignmentListSection);
        listView.setAdapter(adapter);

        // Find and initialize UI elements
        editTextAssignment = view.findViewById(R.id.editTextAssignment);
        editTextDueDate = view.findViewById(R.id.editTextDueDate);
        editTextClassName = view.findViewById(R.id.editTextClassName);
        inputContainer = view.findViewById(R.id.inputContainer);

        // Set up onClickListener for the add button
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClick();
            }
        });

        // Set up onClickListeners for the edit and delete buttons in the addview layout
        Button editButton = addview.findViewById(R.id.buttonEditTask);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditButtonCLick();
            }
        });

        Button deleteButton = addview.findViewById(R.id.buttonDeleteTask);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteButtonClick();
            }
        });

        return view;
    }

    /**
     * Handles the logic when the add button is clicked.
     */
    private void onAddButtonClick() {
        // Get the text from the EditTexts
        String dueDate = editTextDueDate.getText().toString();
        String assignment = editTextAssignment.getText().toString();
        String className = editTextClassName.getText().toString();

        // Create a new ConstraintLayout for the task
        View taskView = createTaskView(dueDate, assignment, className);

        // Add the taskView to the inputContainer
        inputContainer.addView(taskView);

        // Clear input fields
        editTextAssignment.getText().clear();
        editTextDueDate.getText().clear();
        editTextClassName.getText().clear();
    }

    /**
     * Handles the logic when the edit button is clicked.
     */
    private void onEditButtonCLick() {
        int position = 0;
        String dueDate = editTextDueDate.getText().toString();
        String assignment = editTextAssignment.getText().toString();
        String className = editTextClassName.getText().toString();

        // Update the task at the specified position
        updateTask(position, dueDate, assignment, className);

        // Clear input fields
        editTextDueDate.getText().clear();
        editTextAssignment.getText().clear();
        editTextClassName.getText().clear();
    }

    /**
     * Updates the task at the specified position with new details.
     *
     * @param position   The position of the task to be updated.
     * @param dueDate    The new due date.
     * @param title      The new assignment title.
     * @param taskDetail The new task detail.
     */
    private void updateTask(int position, String dueDate, String title, String taskDetail) {
        View taskView = inputContainer.getChildAt(position);
        updateTaskView(taskView, dueDate, title, taskDetail);
    }

    /**
     * Updates the view of the task with new details.
     *
     * @param taskView   The view representing the task.
     * @param dueDate    The new due date.
     * @param title      The new assignment title.
     * @param taskDetail The new task detail.
     */
    private void updateTaskView(View taskView, String dueDate, String title, String taskDetail) {
        createTaskView(dueDate, title, taskDetail);
    }

    /**
     * Handles the logic when the delete button is clicked.
     */
    private void onDeleteButtonClick() {
        // Clear input fields
        editTextDueDate.getText().clear();
        editTextAssignment.getText().clear();
        editTextClassName.getText().clear();
    }

    /**
     * Creates a view representing a task with the provided details.
     *
     * @param dueDate    The due date of the task.
     * @param title      The assignment title of the task.
     * @param taskDetail The task detail.
     * @return The inflated and configured taskView.
     */
    private View createTaskView(String dueDate, String title, String taskDetail) {
        // Inflate the taskView layout
        View taskView = getLayoutInflater().inflate(R.layout.assignment_add_on, null);

        // Find TextViews in the inflated layout
        TextView textViewDueDate = taskView.findViewById(R.id.textViewDueDate);
        TextView textViewTitle = taskView.findViewById(R.id.textViewAssignment);  // Corrected to use textViewTitle
        TextView textViewClassName = taskView.findViewById(R.id.textViewClassName);

        // Set text values with the provided data
        textViewDueDate.setText("Due Date: " + dueDate);
        textViewTitle.setText("Assignment: " + title);  // Corrected to use textViewTitle
        textViewClassName.setText("Class Name: " + taskDetail);

        // Return the inflated and configured taskView
        return taskView;
    }
}
