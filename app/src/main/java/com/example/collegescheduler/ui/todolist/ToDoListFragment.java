package com.example.collegescheduler.ui.todolist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import com.example.collegescheduler.R;

/**
 * A simple {@link Fragment} subclass for managing a to-do list.
 * Use the {@link ToDoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoListFragment extends Fragment {

    // Fragment initialization parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // UI elements
    private EditText editTextTask;
    private EditText editTextTaskDetail;
    private Button buttonAddTask;
    private ListView listViewTasks;
    private ArrayList<String> taskList;
    private CustomAdapter adapter;
    private int selectedItemPosition = -1; // Initialize as -1 indicating no selected item

    // Fragment parameters
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor.
     */
    public ToDoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToDoListFragment.
     */
    public static ToDoListFragment newInstance(String param1, String param2) {
        ToDoListFragment fragment = new ToDoListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todolist, container, false);

        // Initialize UI elements
        editTextTask = view.findViewById(R.id.editTextClassName);
        editTextTaskDetail = view.findViewById(R.id.editTextClassName);
        buttonAddTask = view.findViewById(R.id.buttonAddTask);
        listViewTasks = view.findViewById(R.id.assignmentListSection);

        // Initialize taskList and adapter
        taskList = new ArrayList<>();
        adapter = new CustomAdapter(requireContext(), android.R.layout.simple_list_item_2, taskList, taskList);
        listViewTasks.setAdapter(adapter);

        // Set up button click listener for adding tasks
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskToList();
            }
        });

        return view;
    }

    /**
     * Edit the details of a selected task.
     */
    private void editTask() {
        // Get the edited task details from the EditText
        String editedTaskDetail = editTextTaskDetail.getText().toString();

        // Ensure that the taskList is not empty and there is a selected task to edit
        if (!taskList.isEmpty() && selectedItemPosition >= 0 && selectedItemPosition < taskList.size()) {
            // Update the selected task's details in the list
            String currentTask = taskList.get(selectedItemPosition);
            // Parse the currentTask string and update the details
            // For example, if your task details are separated by "\n", you can do something like this:
            String[] taskDetailsArray = currentTask.split("\n");
            if (taskDetailsArray.length >= 2) {
                taskDetailsArray[1] = "Details: " + editedTaskDetail;
                // Join the updated details back into a single string
                String updatedTask = taskDetailsArray[0] + "\n" + taskDetailsArray[1];
                // Update the task in the list
                taskList.set(selectedItemPosition, updatedTask);
                // Notify the adapter of the data change
                adapter.notifyDataSetChanged();
                // Clear the EditText
                editTextTaskDetail.getText().clear();
                // Reset the selected item position
                selectedItemPosition = -1;
            }
        }
    }

    /**
     * Add a task to the task list.
     */
    private void addTaskToList() {
        String task = editTextTask.getText().toString();
        String taskDetail = editTextTaskDetail.getText().toString();

        if (!task.isEmpty()) {
            // Add the task and task detail as a single string with Item1 and Sub Item1
            String listItem = "Task: " + task + "\nDetails: " + taskDetail;
            taskList.add(listItem);

            // Update the adapter and clear the EditText fields
            adapter.notifyDataSetChanged();
            editTextTask.getText().clear();
            editTextTaskDetail.getText().clear();

            hideKeyboard();
        }
    }

    /**
     * Hide the soft keyboard.
     */
    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}



















//THE CODE BELOW HAS THE FUNCTION WHERE THE DROPDOWN ARROW PART WORKS BUT WHEN I HIT ADD TASK IT CLOSES APP


//
//package com.example.collegescheduler.ui.todolist;
//
//import android.content.Context;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Spinner;
//
//import java.util.ArrayList;
//
//import com.example.collegescheduler.R;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ToDoListFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class ToDoListFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//    private EditText editTextTask;
//    private EditText editTextTaskDetail;
//    private Button buttonAddTask;
//    private ListView listViewTasks;
//    private ArrayList<String> taskList;
//    private CustomAdapter adapter;
//    private Spinner sortSpinner;
//
//    private int selectedItemPosition = -1; // Initialize as -1 indicating no selected item
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public ToDoListFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AssignmentsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ToDoListFragment newInstance(String param1, String param2) {
//        ToDoListFragment fragment = new ToDoListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_todolist, container, false);
//
//        editTextTask = view.findViewById(R.id.editDueDate);
//        View editTextAssignment = view.findViewById(R.id.editTextAssignment);
//        buttonAddTask = view.findViewById(R.id.buttonAddTask);
//        View assignmentListSection = view.findViewById(R.id.assignmentListSection);
//
//        buttonAddTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addTaskToList();
//            }
//        });
//
//
//
//
//
//
//
//
//
//
//
//    Spinner sortSpinner = view.findViewById(R.id.sortSpinner);
//
//    // Define an array of sorting options
//    String[] sortingOptions = {"Due Date", "Assignment", "Class Name", "Complete/Incomplete"};
//
//    // Create an ArrayAdapter using the string array and a default spinner layout
//    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sortingOptions);
//
//    // Specify the layout to use when the list of choices appears
//    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//    // Apply the adapter to the spinner
//    sortSpinner.setAdapter(adapter);
//
//    // Set a listener to handle spinner item selection
//    sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//            // Handle sorting or filtering based on the selected option
//            String selectedOption = (String) parentView.getItemAtPosition(position);
//            if (selectedOption.equals("Due Date")) {
//                // Sort by Due Date
//                // TODO: Implement sorting logic
//
//            } else if (selectedOption.equals("Complete/Incomplete")) {
//                // Filter by Complete/Incomplete
//                // TODO: Implement filtering logic
//
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parentView) {
//            // Do nothing here, but you can handle this case if needed
//        }
//    });
//
//
//
//
//
//
//
//
//
//
//
//
//        return view;
//
//    }
//    private void addTaskToList() {
//        taskList = new ArrayList<>();
//        String task = editTextTask.getText().toString();
//        String taskDetail = editTextTaskDetail.getText().toString();
//
//        if (!task.isEmpty()) {
//            // Add the task and task detail as a single string with Item1 and Sub Item1
//            String listItem = "Task: " + task + "\nDetails: " + taskDetail;
//            taskList.add(listItem);
//
//            // Update the adapter and clear the EditText fields
//            adapter.notifyDataSetChanged();
//            editTextTask.getText().clear();
//            editTextTaskDetail.getText().clear();
//
//            hideKeyboard();
//        }
//    }
//
//
//
//
//
//
//    private void sortTasks(String sortOption) {
//        adapter = new CustomAdapter(requireContext(), R.layout.fragment_todolist, taskList, this.taskList);
//        listViewTasks.setAdapter(adapter);
//        switch (sortOption) {
//            case "Due Date":
//                adapter.sortByDueDate();
//                break;
//            case "Complete/Incomplete":
//                adapter.sortByCompleteness();
//                break;
//            // Add more cases as needed
//        }
//    }
//
//
//    private void hideKeyboard() {
//        View view = requireActivity().getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }
//
//}
