package com.example.collegescheduler.ui.todolist;
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
import com.example.collegescheduler.db.entities.TodoItem;
import com.example.collegescheduler.interfaces.TodoListDatabase;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ToDoListFragment extends Fragment implements TodoListDatabase {
    private SharedViewModel sharedViewModel;
    private String username;

    private EditText taskEntry;
    private EditText taskDetailEntry;
    private ConstraintLayout inputContainer;
    private ToDoListAdapter toDoListAdapter;
    private RecyclerView recyclerViewToDoList;
    private Spinner sortSpinner;
    private List<TodoItem> list;
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
        return inflater.inflate(R.layout.fragment_todolist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.username = sharedViewModel.getUsernameData().getValue();

        //Spinner assignmentSortSpinner = view.findViewById(R.id.assignmentSort);
        sortSpinner = view.findViewById(R.id.toDoListSort);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.toDoListSort, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        taskEntry = view.findViewById(R.id.editTextTask);
        taskDetailEntry = view.findViewById(R.id.editTextTaskDetails);
        inputContainer = view.findViewById(R.id.inputToDoContainer);
        recyclerViewToDoList = view.findViewById(R.id.recyclerViewToDo);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewToDoList.setLayoutManager(layoutManager);

        list = new ArrayList<TodoItem>();
        toDoListAdapter = new ToDoListAdapter(list, this);
        recyclerViewToDoList.setAdapter(toDoListAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isInitialization) {
                    isInitialization = false;
                    return;
                }
                Observer<List<TodoItem>> tempObserver = new Observer<List<TodoItem>>() {
                    @Override
                    public void onChanged(List<TodoItem> todoItems) {
                        if (todoItems != null) {
                            String currentSort = parent.getItemAtPosition(position).toString();
                            determineSortAndAdd(currentSort, todoItems);
                            sharedViewModel.getTodoItemsByUsername(username).removeObserver(this);
                        }
                    }
                };
                sharedViewModel.getTodoItemsByUsername(username).observe(getViewLifecycleOwner(), tempObserver);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        sharedViewModel.getTodoItemsByUsername(this.username).observe(getViewLifecycleOwner(), todoItems -> {
            String currentSort = sortSpinner.getSelectedItem().toString();
            this.determineSortAndAdd(currentSort, todoItems);
        });

        Button addButton = view.findViewById(R.id.addToDoButton);
        addButton.setOnClickListener(v -> onAddButtonClick());

    }

    private void onAddButtonClick () {
        String toDoTask = taskEntry.getText().toString();
        String toDoTaskDetails = taskDetailEntry.getText().toString();

        if (toDoTask.isEmpty() || toDoTaskDetails.isEmpty()) {
            Toast.makeText(requireContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        TodoItem task = new TodoItem(toDoTask, toDoTaskDetails, this.username, false);
        this.insertTodoItemToDatabase(task);

        resetInputs();
    }

    private void resetInputs () {
        // clear input fields
        taskEntry.getText().clear();
        taskDetailEntry.getText().clear();
    }

    private void determineSortAndAdd(String currentSort, List<TodoItem> todoItems) {
        if (todoItems != null) {
            list.clear();
            Log.i("CHECK LIST", todoItems.toString());
            switch (currentSort) {
                case "Due Date":
                    this.addTodoItemsSortedDueDate(todoItems);
                    break;
                case "Incomplete":
                    this.addTodoItemsSortedIncomplete(todoItems);
                    break;
                case "Complete":
                    this.addTodoItemsSortedComplete(todoItems);
                    break;
            }
        }
    }

    private void addTodoItemsSortedDueDate(List<TodoItem> todoItems) {
        todoItems.sort(Comparator.comparing(a -> a.taskDetails));
        toDoListAdapter.addToDoList(todoItems);
    }

    private void addTodoItemsSortedComplete(List<TodoItem> todoItems) {
        todoItems.sort((a1, a2) -> Boolean.compare(a2.completed, a1.completed));
        toDoListAdapter.addToDoList(todoItems);
    }

    private void addTodoItemsSortedIncomplete(List<TodoItem> todoItems) {
        todoItems.sort((a1, a2) -> Boolean.compare(a1.completed, a2.completed));
        toDoListAdapter.addToDoList(todoItems);
    }

    @Override
    public void insertTodoItemToDatabase(TodoItem todoItem) {
        sharedViewModel.insertTodoItem(todoItem);
    }

    @Override
    public void updateTodoItemInDatabase(TodoItem todoItem) {
        sharedViewModel.updateTodoItem(todoItem);
    }

    @Override
    public void updateTodoItemWithText(TodoItem todoItem) {
        String todoName = taskEntry.getText().toString();
        String todoDetails = taskDetailEntry.getText().toString();

        todoItem.task = todoName;
        todoItem.taskDetails = todoDetails;

        resetInputs();

        this.updateTodoItemInDatabase(todoItem);
    }

    @Override
    public void deleteTodoItemInDatabase(TodoItem todoItem) {
        sharedViewModel.deleteTodoItem(todoItem);
    }

    @Override
    public void updateTodoItemCompleted(boolean completed, TodoItem todoItem) {
        todoItem.completed = completed;
        this.updateTodoItemInDatabase(todoItem);
    }
}