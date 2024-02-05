package com.example.collegescheduler.ui.assignments;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.entities.Assignment;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsFragment extends Fragment {

    private EditText editAssignment;
    private EditText editTextDueDate;
    private EditText editClassName;
    private ConstraintLayout inputContainer;
    private AssignmentAdapter assignmentAdapter;
    private RecyclerView recyclerViewAssignments;
    List<Assignment> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assignments, container, false);
        View assignmentAddOnView = inflater.inflate(R.layout.assignment_add_on, container, false);


        Spinner assignmentSortSpinner = view.findViewById(R.id.assignmentSort);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.assignmentSort, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        assignmentSortSpinner.setAdapter(adapter);


        // Initialize views
        editAssignment = view.findViewById(R.id.editTextAssignment);
        editTextDueDate = view.findViewById(R.id.editTextDueDate);
        editClassName = view.findViewById(R.id.editTextClass);

        inputContainer = view.findViewById(R.id.inputContainer);
        recyclerViewAssignments = view.findViewById(R.id.recyclerViewAssignments);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAssignments.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        assignmentAdapter = new AssignmentAdapter(list);
        recyclerViewAssignments.setAdapter(assignmentAdapter);

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClick();
            }
        });

        Button editButton = assignmentAddOnView.findViewById(R.id.buttonEditTask);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditButtonClick();
            }
        });

        Button deleteButton = assignmentAddOnView.findViewById(R.id.buttonDeleteTask);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteButtonClick();
            }
        });

        //apart of the sorting attempt
//        Button sortButton = view.findViewById(R.id.assignmentSort);
//        sortButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showSortOptionsDialog(); // You can implement a dialog to select the sort field
//            }
//        });

        return view;
    }

    private void onAddButtonClick() {
        String assignmentDueDate = editTextDueDate.getText().toString();
        String assignmentClass = editClassName.getText().toString();
        String assignmentAssignment = editAssignment.getText().toString();

        // create new exam card

        Assignment assignment = new Assignment(assignmentDueDate,assignmentClass,assignmentAssignment);

        // recyclerViewExams.addView(assignmentView);
        assignmentAdapter.addItem(assignment);

        // clear input fields
        editTextDueDate.getText().clear();
        editClassName.getText().clear();
        editAssignment.getText().clear();
    }

    private void onEditButtonClick() {
    }

    private void onDeleteButtonClick() {
    }



    //sorting attempt
//    private void showSortOptionsDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Sort By");
//
//        // Array of options for sorting
//        final String[] sortOptions = {"Due Date", "Class", "Incomplete/Complete"};
//
//        // Set up the list of options
//        builder.setItems(sortOptions, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Handle the selected sort option
//                String selectedSortField = sortOptions[which];
//                if (selectedSortField != null) {
//                    assignmentAdapter.setSortField(getSortField(selectedSortField)); //in assignment adapter
//                }
//            }
//        });
//        builder.create().show();
//    }
//
//    // Helper method to map user-friendly labels to actual sort fields
//    private String getSortField(String sortOption) {
//        switch (sortOption) {
//            case "Due Date":
//                return "dueDate";
//            case "Class":
//                return "courseName";
//            case "Incomplete/Complete":
//                return "completionStatus";
//            default:
//                return null;
//        }
}

