package com.example.collegescheduler.ui.todolist;

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

/**
 * CustomAdapter extends ArrayAdapter to customize the view of tasks in a ListView.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    private ArrayList<String> taskList;

    /**
     * Constructor for CustomAdapter.
     *
     * @param context   The context.
     * @param resource  The resource ID for a layout file containing a TextView to use when instantiating views.
     * @param objects   The objects to represent in the ListView.
     * @param taskList  The list of tasks associated with this adapter.
     */
    public CustomAdapter(Context context, int resource, List<String> objects, ArrayList<String> taskList) {
        super(context, resource, objects);
        this.taskList = taskList;
    }

    /**
     * Gets a View that displays the data at the specified position in the data set.
     *
     * @param position    The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent that this view will eventually be attached to.
     * @return             A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }

        // Get the task item
        String taskItem = getItem(position);

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

    /**
     * Shows an edit dialog for a selected task.
     *
     * @param position The position of the selected task.
     */
    private void showEditDialog(int position) {
        // You can create a custom dialog here
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

    /**
     * Sorts the tasks by due date.
     */
    public void sortByDueDate() {
        // TODO: Implement sorting by due date logic
    }

    /**
     * Sorts the tasks by completeness.
     */
    public void sortByCompleteness() {
        // TODO: Implement sorting by completeness logic
    }
}







//CODE BELOW IS MY TESTER


//
//
//package com.example.collegescheduler.ui.todolist;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import com.example.collegescheduler.R;
//import java.util.ArrayList;
//import java.util.List;
//
//public class CustomAdapter extends ArrayAdapter<String> {
//
//    private ArrayList<String> taskList;
//    public CustomAdapter(Context context, int resource, List<String> objects, ArrayList<String> taskList) {
//        super(context, resource, objects);
//        this.taskList = taskList;
//    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
//        }
//        String taskItem = getItem(position);
//
//        // Extract dueDate and courseInfo from the item (customize this part based on your item format)
//        String[] parts = taskItem.split("\n");
//        String dueDate = parts[0].substring(parts[0].indexOf(":") + 2);
//        String courseInfo = parts[1].substring(parts[1].indexOf(":") + 2);
//
//        // Set dueDate and courseInfo to TextViews
//        TextView textViewTask = convertView.findViewById(R.id.textViewTask);
//        TextView textViewDetails = convertView.findViewById(R.id.textViewDetails);
//        textViewTask.setText("Due Date: " + dueDate);
//        textViewDetails.setText("Task: " + courseInfo);
//
//        // Set click listeners for edit and delete buttons
//        Button buttonEditTask = convertView.findViewById(R.id.buttonEditTask);
//        Button buttonDeleteTask = convertView.findViewById(R.id.buttonDeleteTask);
//
//        buttonEditTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle edit button click
//                // You may open an edit dialog or perform any other action here
//                showEditDialog(position);
//            }
//        });
//
//        buttonDeleteTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Handle delete button click
//                // You may remove the item from the list and update the adapter
//                remove(getItem(position));
//                notifyDataSetChanged();
//            }
//        });
//
//        return convertView;
//    }
//
//    private void showEditDialog(int position) {
//        // You can create a custom dialog here
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Edit Task");
//
//        // Create an EditText to allow users to input the new details
//        final EditText editText = new EditText(getContext());
//        editText.setText(getItem(position)); // Pre-fill the EditText with the current task details
//        builder.setView(editText);
//
//        // Set up the buttons for OK and Cancel
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Update the task in the list with the edited details
//                String editedTask = editText.getText().toString();
//                taskList.set(position, editedTask);
//                notifyDataSetChanged();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // User canceled the edit action
//            }
//        });
//
//        builder.show();
//    }
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
//
//    public void sortByDueDate() {
//        SortingUtility.sortByDueDate(convertToTaskStatusList(taskList));
//        notifyDataSetChanged();
//    }
//
//    private List<TaskStatus> convertToTaskStatusList(List<String> taskList) {
//        // Implement the conversion logic based on your data structure
//        // Example: create a new TaskStatus object for each string and add it to a new list
//        List<TaskStatus> result = new ArrayList<>();
//        for (String task : taskList) {
//            TaskStatus taskStatus = convertToTaskStatus(task);
//            result.add(taskStatus);
//        }
//        return result;
//    }
//
//    private TaskStatus convertToTaskStatus(String task) {
//        // Implement the conversion logic based on your data structure
//        // Example: Parse the task string to extract details like due date, completeness, etc.
//        // You may use regular expressions or other methods based on your data format
//        // Here, I'll just provide a simple example assuming due date is in the format "yyyy-MM-dd"
//        String dueDate = extractDueDate(task);
//        boolean isComplete = extractCompleteness(task);
//        return new TaskStatus(this.toString(), isComplete, dueDate);  // Replace with your actual TaskStatus constructor
//    }
//
//    private String extractDueDate(String task) {
//        // Implement logic to extract due date information from the task string
//        // Example: Extract the substring between "Due Date: " and the end of the line
//        int startIndex = task.indexOf("Due Date: ") + 10; // Length of "Due Date: "
//        int endIndex = task.indexOf('\n', startIndex);
//        if (endIndex == -1) {
//            endIndex = task.length(); // If end of line not found, use the entire string
//        }
//        return task.substring(startIndex, endIndex).trim();
//    }
//
//    private boolean extractCompleteness(String task) {
//        // Implement logic to extract completeness information from the task string
//        // Example: Check if the task string contains a marker for completeness, e.g., "[Complete]"
//        return task.contains("[Complete]");
//    }
//
//
//    public void sortByCompleteness() {
//        SortingUtility.sortByCompleteness(convertToTaskStatusList(taskList));
//        notifyDataSetChanged();
//    }
//
//
//
//
//
//
//
//
//
//}