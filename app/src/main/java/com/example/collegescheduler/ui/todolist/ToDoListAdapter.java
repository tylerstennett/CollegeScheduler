package com.example.collegescheduler.ui.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.entities.TodoItem;

import java.util.ArrayList;
import java.util.List;


public class ToDoListAdapter extends ArrayAdapter<TodoItem> { // ??

    private List<TodoItem> list;

    public ToDoListAdapter() { //
        //super(context, resource, objects); ??
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todolist_item_layout, parent, false);
        }

        // Get the task item
        //String taskItem = getItem(position);

        // Extract task and details from the item (customize this part based on your item format)
        String[] parts = taskItem.split("\n");
        String task = parts[0].substring(parts[0].indexOf(":") + 2); // Extract text after "Task: "
        String details = parts[1].substring(parts[1].indexOf(":") + 2); // Extract text after "Details: "

        // Set task and details to TextViews
        TextView textViewTask = convertView.findViewById(R.id.textViewTask);
        TextView textViewDetails = convertView.findViewById(R.id.textViewDetails);
        textViewTask.setText("Task: " + task);
        textViewDetails.setText("Details: " + details);

        // Set click listeners for edit and delete buttons
        Button buttonEditTask = convertView.findViewById(R.id.buttonEditTask);
        Button buttonDeleteTask = convertView.findViewById(R.id.buttonDeleteTask);

        buttonEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit button click
                // You may open an edit dialog or perform any other action here
                showEditDialog(position);
            }
        });

        buttonDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click
                // You may remove the item from the list and update the adapter
                remove(getItem(position));
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private void showEditDialog(int position) {
        // You can create a custom dialog here
        // Example: Create an AlertDialog with an EditText for editing
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Task");

        // Create an EditText to allow users to input the new details
        final EditText editText = new EditText(getContext());
        editText.setText(getItem(position)); // Pre-fill the EditText with the current task details
        builder.setView(editText);

        // Set up the buttons for OK and Cancel
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the task in the list with the edited details
                String editedTask = editText.getText().toString();
                list.set(position, editedTask);
                notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User canceled the edit action
            }
        });

        builder.show();
    }

}


