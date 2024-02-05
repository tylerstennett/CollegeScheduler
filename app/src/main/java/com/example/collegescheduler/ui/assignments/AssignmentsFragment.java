package com.example.collegescheduler.ui.assignments;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.entities.Assignment;
import com.example.collegescheduler.ui.assignments.AssignmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsFragment extends Fragment implements AssignmentAdapter.EditListener {
    private EditText assignmentEntry;
    private EditText dueDateEntry;
    private EditText classEntry;
    private ConstraintLayout inputContainer;
    private AssignmentAdapter assignmentAdapter;
    private RecyclerView recyclerViewAssignments;
    List<Assignment> list;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assignments, container, false);

        Spinner assignmentSortSpinner = view.findViewById(R.id.assignmentSort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.assignmentSort, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignmentSortSpinner.setAdapter(adapter);

        assignmentEntry = view.findViewById(R.id.editTextAssignment);
        dueDateEntry = view.findViewById(R.id.editTextDueDate);
        classEntry = view.findViewById(R.id.editTextClass);
        inputContainer = view.findViewById(R.id.inputContainer);
        recyclerViewAssignments = view.findViewById(R.id.recyclerViewAssignments);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAssignments.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        assignmentAdapter = new AssignmentAdapter(list, this);
        recyclerViewAssignments.setAdapter(assignmentAdapter);

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClick();
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

        public void onEditButtonClick ( int position){
            // Get text from input fields
            String assignmentDueDate = dueDateEntry.getText().toString();
            String assignmentClass = classEntry.getText().toString();
            String assignmentAssignment = assignmentEntry.getText().toString();

            Assignment assignment = new Assignment(assignmentDueDate, assignmentClass, assignmentAssignment);

            assignmentAdapter.editItem(assignment, position);

            resetInputs();
        }

        private void onAddButtonClick () {
            String assignmentDueDate = dueDateEntry.getText().toString();
            String assignmentClass = classEntry.getText().toString();
            String assignmentAssignment = assignmentEntry.getText().toString();

            if (assignmentDueDate.isEmpty() || assignmentClass.isEmpty() || assignmentAssignment.isEmpty()) {
                Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
             }
            Assignment assignment = new Assignment(assignmentDueDate, assignmentClass, assignmentAssignment);
            assignmentAdapter.addItem(assignment);
            resetInputs();
        }

        private void resetInputs () {
            // clear input fields
            dueDateEntry.getText().clear();
            classEntry.getText().clear();
            assignmentEntry.getText().clear();
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