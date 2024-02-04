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

/**
 * Custom ArrayAdapter for displaying assignment items in a ListView.
 */
public class CustomAssignment extends ArrayAdapter<String> {

    private ArrayList<String> taskList;

    /**
     * Constructor for the CustomAssignment adapter.
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param taskList The list of tasks to display.
     */
    public CustomAssignment(Context context, int resource, ArrayList<String> taskList) {
        super(context, resource);
        this.taskList = taskList;
    }

    /**
     * Overrides the getView method to customize the appearance of each item in the ListView.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return The view corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Inflate the layout if the view is null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.assignment_add_on, parent, false);
        }

        // Extract details from the taskItem string
        String taskItem = getItem(position);
        String[] parts = taskItem.split("\n");
        String assignment = parts[0].substring(parts[0].indexOf(":") + 2); // Extract text after "Assignment: "
        String className = parts[1].substring(parts[1].indexOf(":") + 2); // Extract text after "Class: "
        String dueDate = parts[2].substring(parts[2].indexOf(":") + 2); // Extract text after "Due Date: "

        // Find TextViews in the inflated layout
        TextView textViewAssignment = convertView.findViewById(R.id.textViewAssignment);
        TextView textViewClass = convertView.findViewById(R.id.editTextClassName);
        TextView textViewDueDate = convertView.findViewById(R.id.textViewDueDate);

        // Set text values with the provided data
        textViewDueDate.setText("Due Date: " + dueDate);
        textViewAssignment.setText("Assignment: " + assignment);
        textViewClass.setText("Class: " + className);

        // Find buttons in the inflated layout
        Button buttonEditTask = convertView.findViewById(R.id.buttonEditTask);
        Button buttonDeleteTask = convertView.findViewById(R.id.buttonDeleteTask);

        // Set onClickListeners for edit and delete buttons
        buttonEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(position);
            }
        });

        buttonDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(getItem(position));
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    /**
     * Displays a custom dialog for editing a task.
     *
     * @param position The position of the task in the list.
     */
    private void showEditDialog(int position) {
        // Create an AlertDialog with an EditText for editing
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
