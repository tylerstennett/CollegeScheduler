package com.example.collegescheduler.ui.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.collegescheduler.R;
import com.example.collegescheduler.db.entities.TodoItem;
import com.example.collegescheduler.interfaces.TodoListDatabase;

import java.util.List;
public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoListViewHolder> {
    private TodoListDatabase todoListDatabase;
    private List<TodoItem> list;
    private ToDoListFragment toDoListFragment;
    private TodoItem toDoItem; // Track the selected item
    private TextView infoToDo;
    private Context context;

    public ToDoListAdapter(List<TodoItem> list, TodoListDatabase todoListDatabase) {
        this.list = list;
        this.todoListDatabase = todoListDatabase;
    }
    @Override
    public ToDoListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todolist_item_layout, viewGroup, false);
        return new ToDoListViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ToDoListViewHolder viewHolder, int position) {

        viewHolder.toDoListTask.setText("Task: " + list.get(position).task);
        viewHolder.toDoListTaskDetails.setText("Task Details: " + list.get(position).taskDetails);

        // make sure not to prompt CheckedChangeListener
        viewHolder.taskCompleted.setOnCheckedChangeListener(null);
        viewHolder.taskCompleted.setChecked(list.get(position).completed);

        viewHolder.taskCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    TodoItem taskToEdit = list.get(adapterPosition);
                    todoListDatabase.updateTodoItemCompleted(isChecked, taskToEdit);
                }
            }
        });

        viewHolder.taskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    TodoItem taskToDelete = list.get(adapterPosition);
                    todoListDatabase.deleteTodoItemInDatabase(taskToDelete);
                    // list.remove(adapterPosition); automatically remakes list
                    // notifyItemRemoved(adapterPosition);
                }
            }
        });

        viewHolder.taskEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    TodoItem taskToEdit = list.get(adapterPosition);
                    todoListDatabase.updateTodoItemWithText(taskToEdit);
                }
            }
        });

    }

    public void editItem(TodoItem newTask, int position) {
        list.set(position, newTask);
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(TodoItem newTask) {
        list.add(newTask);
        notifyItemInserted(list.size());
    }

    public void addToDoList(List<TodoItem> todoItems) {
        this.list.clear();
        this.list.addAll(todoItems);
        notifyDataSetChanged();
    }

    public static class ToDoListViewHolder extends RecyclerView.ViewHolder {

        private final TextView toDoListTask;
        private final TextView toDoListTaskDetails;
        private final Button taskDelete;
        private final Button taskEdit;
        private final CheckBox taskCompleted;


        private final View view;


        public ToDoListViewHolder (View itemView) {
            super(itemView);
            toDoListTask = itemView.findViewById(R.id.textViewTask);
            toDoListTaskDetails = itemView.findViewById(R.id.textViewDetails);
            taskCompleted = itemView.findViewById(R.id.checkBoxTaskComplete);

            taskDelete = itemView.findViewById(R.id.buttonDeleteToDoTask);
            taskEdit = itemView.findViewById(R.id.buttonEditToDoTask);

            view = itemView;
        }

    }
}