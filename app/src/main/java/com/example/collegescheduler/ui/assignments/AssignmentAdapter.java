package com.example.collegescheduler.ui.assignments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.example.collegescheduler.R;
import com.example.collegescheduler.db.entities.Assignment;
import com.example.collegescheduler.ui.assignments.AssignmentsFragment;

import java.util.List;
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private List<Assignment> list;
    private AssignmentsFragment assignmentsFragment;
    private Assignment selectedItem; // Track the selected item
    private TextView infoAssignment;
    private EditListener editListener;
    private Context context;

    public AssignmentAdapter(List<Assignment> list, EditListener editListener) {
        this.list = list;
        this.editListener = editListener;
    }

    // creates new exam views
    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new Exam view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assignment_add_on, viewGroup, false);
        // return ViewHolder with new assignmentView

        return new AssignmentViewHolder(view);
    }

    // replace contents of a view
    @Override
    public void onBindViewHolder(AssignmentViewHolder viewHolder, int position) {

        viewHolder.assignmentDueDate.setText("Due Date: " + list.get(position).dueDate);
        viewHolder.assignmentClass.setText("Class: " + list.get(position).courseName);
        viewHolder.assignmentAssignment.setText("Assignment: " + list.get(position).assignmentName);

        viewHolder.assignmentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Assignment removedItem = list.get(viewHolder.getAbsoluteAdapterPosition());
                list.remove(viewHolder.getAbsoluteAdapterPosition());
                notifyItemRemoved(viewHolder.getAbsoluteAdapterPosition());
            }

        });

        viewHolder.assignmentEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                editListener.onEditButtonClick(position);
            }

        });

    }

    public void editItem(Assignment newAssignment, int position) {
        list.set(position, newAssignment);
        notifyItemChanged(position);
        showToast("Assignment Edited");

    }

    void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public Assignment getItem(int position) {
        return list.get(position);
    }

    public interface EditListener {
        void onEditButtonClick(int position);
    }

        @Override
    public int getItemCount() {
        return list.size();
    }
    public void addItem(Assignment newAssignment) {
        list.add(newAssignment);
        notifyItemInserted(list.size());
    }
        //sorting attempt
//    public void setSortField(String selectedSortField) {
//    }



        public static class AssignmentViewHolder extends RecyclerView.ViewHolder {

            private final TextView assignmentDueDate;
            private final TextView assignmentClass;
            private final TextView assignmentAssignment;
            private final Button assignmentDelete;
            private final Button assignmentEdit;


            private final View view;


            public AssignmentViewHolder (View itemView) {
                super(itemView);
                assignmentDueDate = itemView.findViewById(R.id.textViewDueDate);
                assignmentClass = itemView.findViewById(R.id.textViewAssociatedClass);
                assignmentAssignment = itemView.findViewById(R.id.textViewAssignment);

                assignmentDelete = itemView.findViewById(R.id.buttonDeleteTask);
                assignmentEdit = itemView.findViewById(R.id.buttonEditTask);

                view = itemView;
            }

        }
    }