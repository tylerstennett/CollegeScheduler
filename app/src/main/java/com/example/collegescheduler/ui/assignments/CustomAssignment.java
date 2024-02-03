package com.example.collegescheduler.ui.assignments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.collegescheduler.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAssignment extends ArrayAdapter<String> {

    private ArrayList<String> taskList;

    public CustomAssignment(Context context, int resource, List<String> objects, ArrayList<String> taskList) {
        super(context, resource, objects);
        this.taskList = taskList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }

        // Get the task item
        String taskItem = getItem(position);

        // Extract assignment, class, and due date from the item (customize this part based on your item format)
        String[] parts = taskItem.split("\n");
        String assignment = parts[0].substring(parts[0].indexOf(":") + 2); // Extract text after "Assignment: "
        String className = parts[1].substring(parts[1].indexOf(":") + 2); // Extract text after "Class: "
        String dueDate = parts[2].substring(parts[2].indexOf(":") + 2); // Extract text after "Due Date: "

        // Set assignment, class, and due date to TextViews
        TextView textViewAssignment = convertView.findViewById(R.id.textViewTitle);
        TextView textViewClass = convertView.findViewById(R.id.editTextTaskDetail);
        TextView textViewDueDate = convertView.findViewById(R.id.textViewDueDate);

        textViewAssignment.setText("Assignment: " + assignment);
        textViewClass.setText("Class: " + className);
        textViewDueDate.setText("Due Date: " + dueDate);

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

    private void showEditDialog(final int position) {
        // You can create a custom dialog here
        // Example: Create an AlertDialog with an EditText for editing
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Assignment");

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
                taskList.set(position, editedTask);
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
