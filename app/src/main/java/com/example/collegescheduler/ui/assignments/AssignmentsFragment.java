package com.example.collegescheduler.ui.assignments;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.SharedViewModel;
import com.example.collegescheduler.db.entities.Assignment;
import com.example.collegescheduler.interfaces.AssignmentDatabase;
import com.example.collegescheduler.ui.assignments.AssignmentAdapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AssignmentsFragment extends Fragment implements AssignmentDatabase {
    private SharedViewModel sharedViewModel;
    private String username;

    private EditText assignmentEntry;
    private EditText dueDateEntry;
    private EditText classEntry;
    private ConstraintLayout inputContainer;
    private AssignmentAdapter assignmentAdapter;
    private RecyclerView recyclerViewAssignments;
    private Spinner sortSpinner;
    private List<Assignment> list;
    private Context context;
    private boolean isInitialization = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_assignments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.username = sharedViewModel.getUsernameData().getValue();

        //Spinner assignmentSortSpinner = view.findViewById(R.id.assignmentSort);
        sortSpinner = view.findViewById(R.id.assignmentSort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.assignmentSort, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        assignmentEntry = view.findViewById(R.id.editTextAssignment);
        dueDateEntry = view.findViewById(R.id.editTextDueDate);
        classEntry = view.findViewById(R.id.editTextClass);
        inputContainer = view.findViewById(R.id.inputContainer);
        recyclerViewAssignments = view.findViewById(R.id.recyclerViewAssignments);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewAssignments.setLayoutManager(layoutManager);

        list = new ArrayList<Assignment>();
        assignmentAdapter = new AssignmentAdapter(list, this);
        recyclerViewAssignments.setAdapter(assignmentAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isInitialization) {
                    isInitialization = false;
                    return;
                }
                Observer<List<Assignment>> tempObserver = new Observer<List<Assignment>>() {
                    @Override
                    public void onChanged(List<Assignment> assignments) {
                        if (assignments != null) {
                            String currentSort = parent.getItemAtPosition(position).toString();
                            determineSortAndAdd(currentSort, assignments);
                            sharedViewModel.getAssignmentsByUsername(username).removeObserver(this);
                        }
                    }
                };
                sharedViewModel.getAssignmentsByUsername(username).observe(getViewLifecycleOwner(), tempObserver);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        sharedViewModel.getAssignmentsByUsername(this.username).observe(getViewLifecycleOwner(), assignments -> {
            String currentSort = sortSpinner.getSelectedItem().toString();
            determineSortAndAdd(currentSort, assignments);
        });

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> onAddButtonClick());

    }

    private void determineSortAndAdd(String currentSort, List<Assignment> assignments) {
        if (assignments != null) {
            list.clear();
            switch (currentSort) {
                case "Due Date":
                    this.addAssignmentsSortedDueDate(assignments);
                    break;
                case "Class":
                    this.addAssignmentSortedClassName(assignments);
                    break;
                case "Incomplete":
                    this.addAssignmentSortedIncomplete(assignments);
                    break;
                case "Complete":
                    this.addAssignmentSortedComplete(assignments);
                    break;
            }
        }
    }

    private void addAssignmentsSortedDueDate(List<Assignment> assignments) {
        assignments.sort(Comparator.comparing(a -> a.dueDate));
        assignmentAdapter.addAssignmentList(assignments);
    }

    private void addAssignmentSortedClassName(List<Assignment> assignments) {
        assignments.sort(Comparator.comparing(a -> a.courseName));
        assignmentAdapter.addAssignmentList(assignments);
    }

    private void addAssignmentSortedComplete(List<Assignment> assignments) {
        assignments.sort((a1, a2) -> Boolean.compare(a2.completed, a1.completed));
        assignmentAdapter.addAssignmentList(assignments);
    }

    private void addAssignmentSortedIncomplete(List<Assignment> assignments) {
        assignments.sort((a1, a2) -> Boolean.compare(a1.completed, a2.completed));
        assignmentAdapter.addAssignmentList(assignments);
    }

    @Override
    public void updateAssignmentCompleted(boolean completed, Assignment assignment) {
        assignment.completed = completed;
        this.updateAssignmentInDatabase(assignment);
    }

    @Override
    public void insertAssignmentToDatabase(Assignment assignment) {
        sharedViewModel.insertAssignment(assignment);
    }

    @Override
    public void updateAssignmentInDatabase(Assignment assignment) {
        sharedViewModel.updateAssignment(assignment);
    }

    @Override
    public void updateAssignmentWithText(Assignment assignment) {
        String assignmentDueDate = dueDateEntry.getText().toString();
        String assignmentClass = classEntry.getText().toString();
        String assignmentAssignment = assignmentEntry.getText().toString();

        assignment.dueDate = assignmentDueDate;
        assignment.courseName = assignmentClass;
        assignment.assignmentName = assignmentAssignment;

        resetInputs();

        this.updateAssignmentInDatabase(assignment);
    }

    @Override
    public void deleteAssignmentInDatabase(Assignment assignment) {
        sharedViewModel.deleteAssignment(assignment);
    }

    private void onAddButtonClick () {
        String assignmentDueDate = dueDateEntry.getText().toString();
        String assignmentClass = classEntry.getText().toString();
        String assignmentAssignment = assignmentEntry.getText().toString();

        if (assignmentDueDate.isEmpty() || assignmentClass.isEmpty() || assignmentAssignment.isEmpty()) {
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        Assignment assignment = new Assignment(assignmentAssignment, assignmentDueDate, assignmentClass, this.username, false);
        this.insertAssignmentToDatabase(assignment);

        resetInputs();
    }

    private void resetInputs () {
        // clear input fields
        dueDateEntry.getText().clear();
        classEntry.getText().clear();
        assignmentEntry.getText().clear();
    }

}