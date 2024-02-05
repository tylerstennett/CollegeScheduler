package com.example.collegescheduler.ui.todolist;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import com.example.collegescheduler.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToDoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText editTextTask;
    private EditText editTextTaskDetail;
    private Button buttonAddTask;
    private ListView listViewTasks;
    private ArrayList<String> taskList;
    private ToDoListAdapter adapter;
    private int selectedItemPosition = -1; // Initialize as -1 indicating no selected item

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ToDoListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssignmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
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




        Spinner toDoListSortSpinner = view.findViewById(R.id.toDoListSort);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter sortAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.toDoListSort, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        toDoListSortSpinner.setAdapter(sortAdapter);



        editTextTask = view.findViewById(R.id.editTextTask);
        editTextTaskDetail = view.findViewById(R.id.editTextTaskDetail);
        buttonAddTask = view.findViewById(R.id.buttonAddTask);
        listViewTasks = view.findViewById(R.id.listViewTasks);


        // Initialize taskList and adapter
        taskList = new ArrayList<>();
        adapter = new ToDoListAdapter(requireContext(), android.R.layout.simple_list_item_2, taskList, taskList);
        listViewTasks.setAdapter(adapter);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskToList();
            }
        });

        return view;

    }

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


    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}