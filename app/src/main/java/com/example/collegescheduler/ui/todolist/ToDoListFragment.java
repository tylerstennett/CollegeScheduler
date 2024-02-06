package com.example.collegescheduler.ui.todolist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.SharedViewModel;
import com.example.collegescheduler.db.entities.TodoItem;
import com.example.collegescheduler.interfaces.TodoListDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToDoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoListFragment extends Fragment implements TodoListDatabase {
    private SharedViewModel sharedViewModel;
    private String username;

    private EditText editTextTask;
    private EditText editTextTaskDetail;
    private Button buttonAddTask;
    private ListView listViewTasks;
    private List<TodoItem> taskList;
    private ToDoListAdapter adapter;
    private int selectedItemPosition = -1; // Initialize as -1 indicating no selected item


    public ToDoListFragment() {
        // Required empty public constructor
    }

    public static ToDoListFragment newInstance() {
        return new ToDoListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_todolist, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner todoSpinner = view.findViewById(R.id.toDoListSort);
        ArrayAdapter sortAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.toDoListSort, android.R.layout.simple_spinner_item);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        todoSpinner.setAdapter(sortAdapter);

        editTextTask = view.findViewById(R.id.editTextTask);
        editTextTaskDetail = view.findViewById(R.id.editTextTaskDetail);
        buttonAddTask = view.findViewById(R.id.buttonAddTask);
        listViewTasks = view.findViewById(R.id.listViewTasks);


        // Initialize taskList and adapter
        taskList = new ArrayList<TodoItem>();
        //adapter = new ToDoListAdapter(requireContext(), android.R.layout.simple_list_item_2, taskList, taskList);
        listViewTasks.setAdapter(adapter);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskToList();
            }
        });
    }

    public void insertTodoItemToDatabase(TodoItem todoItem) {

    }
    public void updateTodoItemInDatabase(TodoItem todoItem) {

    }
    public void updateTodoItemWithText(TodoItem todoItem) {

    }
    public void deleteTodoItemInDatabase(TodoItem todoItem) {

    }
    public void updateTodoItemCompleted(TodoItem todoItem) {

    }

    private void addTaskToList() {
        String task = editTextTask.getText().toString();
        String taskDetail = editTextTaskDetail.getText().toString();
        if (task.isEmpty()) {
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add the task and task detail as a single string with Item1 and Sub Item1
        //String listItem = "Task: " + task + "\nDetails: " + taskDetail;
        //taskList.add(listItem);

        // Update the adapter and clear the EditText fields
        adapter.notifyDataSetChanged();
        editTextTask.getText().clear();
        editTextTaskDetail.getText().clear();

        hideKeyboard();
    }

    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}